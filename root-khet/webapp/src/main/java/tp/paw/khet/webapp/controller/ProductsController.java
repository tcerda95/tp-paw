package tp.paw.khet.webapp.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import dto.ProductDTO;
import tp.paw.khet.model.Product;
import tp.paw.khet.service.ProductService;

@Path("products")
@Controller
public class ProductsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    private ProductService productService;
    
    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON}) 
    public Response getProductById(@PathParam("id") final int productID) {
        Product product = productService.getFullProductById(productID);
        
        if (product == null)
            return Response.status(Status.NOT_FOUND).build();
        else {
            LOGGER.debug("Accesed product with id: {}", productID);
            return Response.ok(new ProductDTO(product)).build();
        }
    }
}
