package tp.paw.khet.webapp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.User;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.dto.CollectionListDTO;
import tp.paw.khet.webapp.dto.UserDTO;

import java.util.List;
import java.util.LinkedList;

@Path("users")
@Controller
@Produces(value = {MediaType.APPLICATION_JSON}) 
public class UsersController {
    
    @Autowired
    private UserService userService;
    
	@Context
	private UriInfo uriContext;
    
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") final int id) {
        final User user = userService.getUserById(id);
        
        if (user != null) {
            return Response.ok(new UserDTO(user, uriContext.getBaseUri())).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}/collections")
    public Response getUserCollections(@PathParam("id") final int id) {
    	final User user = userService.getUserById(id);
    	
        if (user != null) {
        	List<FavList> favLists = new LinkedList<>(user.getFavLists());
            return Response.ok(new CollectionListDTO(favLists, uriContext.getBaseUri(), user.getUserId())).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
