package tp.paw.khet.webapp.rest;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.model.User;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.dto.ProductDTO;
import tp.paw.khet.webapp.dto.ProductListDTO;
import tp.paw.khet.webapp.dto.UserListDTO;
import tp.paw.khet.webapp.utils.PaginationLinkFactory;

@Path("products")
@Controller
@Produces(value = {MediaType.APPLICATION_JSON}) 
public class ProductsController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private PaginationLinkFactory linkFactory;

	@Context
	private UriInfo uriContext;

	// TODO: poner en algún lado
	public static final int MAX_PAGE_SIZE = 100;
	public static final int DEFAULT_PAGE_SIZE = 20;

	@GET
	@Path("/{id}")
	public Response getProductById(@PathParam("id") final int id) {
		final Product product = productService.getFullProductById(id);

		if (product == null) {
			LOGGER.warn("Product with ID: {} not found", id);
			return Response.status(Status.NOT_FOUND).build();
		} else {
			LOGGER.debug("Accesed product with ID: {}", id);
			return Response.ok(new ProductDTO(product, uriContext.getBaseUri())).build();
		}
	}

	@GET
	@Path("/")
	public Response getProducts(@QueryParam("category") String categoryStr,
			@DefaultValue("1") @QueryParam("page") int page,
			@DefaultValue("" + DEFAULT_PAGE_SIZE) @QueryParam("per_page") int pageSize,
			@DefaultValue("ALPHABETICALLY") @QueryParam("sorted_by") final ProductSortCriteria sortCriteria,
			@DefaultValue("asc") @QueryParam("order") final String order) {

		final Optional<Category> category = Optional.empty();

		// Ignoro valores inválidos, queda en el default.
		page = (page < 1) ? 1 : page;
		pageSize = (pageSize < 1 || pageSize > MAX_PAGE_SIZE) ? DEFAULT_PAGE_SIZE : pageSize; 

		final int maxPage = productService.getMaxProductPageWithSize(category, pageSize);

		if (page > maxPage) {
			LOGGER.warn("Index page out of bounds: {}", page);
			return Response.status(Status.NOT_FOUND).build(); 
			// NOT_FOUND u otra cosa? Github devuelve body y de contenido un objecto vacío ( [ ] ó { } )
 		}

		// TODO: falta usar "order"
		final List<Product> products = productService.getPlainProductsPaged(category, sortCriteria, page, pageSize);

		LOGGER.debug("Accesing product list. Category: {}, page: {}, per_page: {}, sort: {}, order: {}", category, page,
				pageSize, sortCriteria, order);

		final Map<String, Link> links = linkFactory.createLinks(uriContext, page, maxPage);
		final Link[] linkArray = links.values().toArray(new Link[0]);

		LOGGER.debug("Links: {}", links);
		return Response.ok(new ProductListDTO(products, page, pageSize, category, uriContext.getBaseUri())).links(linkArray).build();
	}

	@GET
	@Path("{id}/voters")
	public Response getProductVoters(@PathParam("id") final int id) {
		final Product product = productService.getFullProductById(id);
		if (product == null) {
			LOGGER.warn("Product with ID: {} not found", id);
			return Response.status(Status.NOT_FOUND).build();
		} else {
			LOGGER.debug("Accesed voters with product ID: {}", id);
			final List<User> users = new LinkedList<>(product.getVotingUsers());
			return Response.ok(new UserListDTO(users, id, 1, users.size(), uriContext.getBaseUri())).build();
		}
	}
}