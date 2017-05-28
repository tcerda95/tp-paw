package tp.paw.khet.persistence;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.Product.ProductBuilder;
import tp.paw.khet.model.interfaces.PlainProduct;
import tp.paw.khet.persistence.rowmapper.PlainProductRowMapper;
import tp.paw.khet.persistence.rowmapper.ProductBuilderRowMapper;

@Repository
public class ProductJdbcDao implements ProductDao {
	    
	@Autowired
	private ProductBuilderRowMapper productBuilderRowMapper;
	
	@Autowired
	private PlainProductRowMapper plainProductRowMapper;
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public ProductJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("products")
					.usingGeneratedKeyColumns("productid");
	}
	
	@Override
	public List<PlainProduct> getPlainProducts() {
		return jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products ORDER BY uploadDate DESC", 
				plainProductRowMapper);
	}

	@Override
	public List<PlainProduct> getPlainProductsByCategory(String category) {
		return jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products WHERE category = ? ORDER BY uploadDate DESC", 
				plainProductRowMapper, category.toUpperCase(Locale.ENGLISH));
	}
	
	@Override
	public Product.ProductBuilder getFullProductById(int productId) {
		List<Product.ProductBuilder> productBuilder = jdbcTemplate.query("SELECT * FROM products NATURAL JOIN users WHERE productId = ?", productBuilderRowMapper, productId);
		
		if (productBuilder.isEmpty())
			return null;
		
		return productBuilder.get(0);
	}
	
	@Override
	public PlainProduct getPlainProductById(int productId) {
		List<PlainProduct> product = jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products WHERE productId = ?", 
				plainProductRowMapper, productId);
		
		if (product.isEmpty())
			return null;
		
		return product.get(0);
	}
	
	@Override
	public List<PlainProduct> getPlainProductsByUserId(int userId) {
		return jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products WHERE userId = ? ORDER BY uploadDate DESC", 
				plainProductRowMapper, userId);
	}
	
	@Override
	public ProductBuilder createProduct(String name, String description, String shortDescription, String website, String category,
			LocalDateTime uploadDate, byte[] logo, int creatorId) {

		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("productName", name);
		args.put("description", description);
		args.put("shortDescription", shortDescription);
		args.put("website", website);
		args.put("category", category.toUpperCase(Locale.ENGLISH));
		args.put("uploadDate", Timestamp.valueOf(uploadDate));
		args.put("logo", logo);
		args.put("userId", creatorId);
		
		final Number productId = jdbcInsert.executeAndReturnKey(args);
		
		return Product.getBuilder(productId.intValue(), name, shortDescription)
				.description(description)
				.website(website)
				.category(category)
				.uploadDate(uploadDate);		
	}

	@Override
	public byte[] getLogoByProductId(int productId) {
		byte[] logo = jdbcTemplate.queryForObject("SELECT logo FROM products WHERE productId = ?", byte[].class, productId);
		
		if (logo == null)
			return new byte[0];
		
		return logo;
	}

    @Override
    public List<PlainProduct> getPlainProductsRange(int offset, int length) {
        return jdbcTemplate.query("SELECT productId, productName, shortDescription, category "
                + "FROM products ORDER BY uploadDate DESC LIMIT ? OFFSET ?", plainProductRowMapper, length, offset);
    }

    @Override
    public List<PlainProduct> getPlainProductsRangeByCategory(String category, int offset, int length) {
        return jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products WHERE category = ? "
                + "ORDER BY uploadDate DESC LIMIT ? OFFSET ?", plainProductRowMapper, category.toUpperCase(Locale.ENGLISH), length, offset);
    }

    @Override
    public int getTotalProducts() {
        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products", Integer.class);
        return total != null ? total : 0;
    }

    @Override
    public int getTotalProductsInCategory(Category category) {
        Integer total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products WHERE category = ?", Integer.class, category.name());
        return total != null ? total : 0;
    }

	@Override
	public boolean deleteProductById(int productId) {
		return jdbcTemplate.update("DELETE FROM products WHERE productId = ?", productId) == 1;
	}

    @Override
    public List<PlainProduct> getPlainProductsRangeAlphabetically(int offset, int length) {
        return jdbcTemplate.query("SELECT productId, productName, shortDescription, category "
                + "FROM products ORDER BY lower(productName) LIMIT ? OFFSET ?", plainProductRowMapper, length, offset);
    }

    @Override
    public List<PlainProduct> getPlainProductsRangeAlphabeticallyByCategory(String category, int offset,
            int length) {
        return jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products WHERE category = ? "
                + "ORDER BY lower(productName) LIMIT ? OFFSET ?", plainProductRowMapper, category.toUpperCase(Locale.ENGLISH), length, offset);
    }

	@Override
	public List<PlainProduct> getPlainProductsByKeyword(String keyword, int maxLength) {
		String firstWordKeyword = keyword+"%";
		String otherWordsKeyword = "% "+keyword+"%";
		
		String sql = "SELECT productId, productName, shortDescription, category FROM products WHERE "
				+ "lower(productName) LIKE lower(?) OR lower(productName) LIKE lower(?) OR "
				+ "lower(shortDescription) LIKE lower(?) OR lower(shortDescription) LIKE lower(?) "
				+ "ORDER BY lower(productName) LIMIT ?";
		
		return jdbcTemplate.query(sql, plainProductRowMapper, firstWordKeyword, otherWordsKeyword, firstWordKeyword, otherWordsKeyword, maxLength);
	}    
}
