package tp.paw.khet.webapp.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import dto.UserDTO;
import tp.paw.khet.model.User;
import tp.paw.khet.service.UserService;


@Path("users")
@Controller
public class UserController {
    
    @Autowired
    UserService userService;
    
    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON}) 
    public Response getUserById(@PathParam("id") final int id) {
        final User user = userService.getUserById(id);
        if (user != null) {
            return Response.ok(new UserDTO(user)).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
