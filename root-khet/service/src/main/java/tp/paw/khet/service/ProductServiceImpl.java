package tp.paw.khet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.persistence.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Product getFullProductById(int productId) {
		Product.ProductBuilder productBuilder = productDao.getFullProductById(productId);
		
		productBuilder.commentFamilies(commentService.getCommentsByProductId(productId))
					  .videos(videoService.getVideosByProductId(productId));
		
		return productBuilder.build();
	}

	@Override
	public Product getPlainProductById(int productId) {
		return productDao.getPlainProductById(productId);
	}
	
	@Override
	public List<Product> getPlainProducts() {
		return productDao.getPlainProducts();
	}

	@Override
	public List<Product> getPlainProductsByCategory(Category category) {
		return productDao.getPlainProductsByCategory(category.name());
	}
	
	@Override
	public Product createProduct(String name, String description, String shortDescription, String website,
			Category category, byte[] logo, int creatorId, List<byte[]> imageBytes, List<String> videoIds) {
		
		Product.ProductBuilder productBuilder = productDao.createProduct(name, description, shortDescription, 
				website, category.name(), LocalDateTime.now(), logo, creatorId);
		
		int productId = productBuilder.build().getId();
		
		productBuilder.creator(userService.getUserById(creatorId));
		productBuilder.videos(videoService.createVideos(videoIds, productId));
		productImageService.createProductImages(imageBytes, productId);
		
		return productBuilder.build();
	}

	@Override
	public byte[] getLogoByProductId(int productId) {
		return productDao.getLogoByProductId(productId);
	}

}
