package tp.paw.khet.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tp.paw.khet.model.ProductImage;

@Repository
public class ProductImageJdbcDao implements ProductImageDao {
	
	private final static RowMapper<ProductImage> ROW_MAPPER = new RowMapper<ProductImage> () {

		public ProductImage mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ProductImage(rs.getInt("productImageId"), rs.getInt("productId"), rs.getBytes("data"));
		}
	};
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public ProductImageJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("productImages");
	}

	@Override
	public ProductImage createProductImage(int imageId, int productId, byte[] data) {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("productImageId", imageId);
		args.put("productId", productId);
		args.put("data", data);
		
		jdbcInsert.execute(args);
		return new ProductImage(imageId, productId, data);
	}

	@Override
	public List<Integer> getImagesIdByProductId(int productId) {
		return jdbcTemplate.queryForList("SELECT productImageId FROM productImages WHERE productId = ? ORDER BY productImageId", Integer.class, productId);
	}

	@Override
	public ProductImage getImageByIds(int imageId, int productId) {
		List<ProductImage> list = jdbcTemplate.query("SELECT * FROM productImages WHERE productId = ? AND productImageId = ?", 
				ROW_MAPPER, productId, imageId);
		
		if (list.isEmpty())
			return null;
		
		return list.get(0);
	}

}
