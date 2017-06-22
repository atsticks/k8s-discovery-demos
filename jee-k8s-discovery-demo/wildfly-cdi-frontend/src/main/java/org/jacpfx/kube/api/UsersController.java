package org.jacpfx.kube.api;


import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jacpfx.kube.domain.model.Person;
import org.jacpfx.kube.domain.service.Userservice;


/**
 * Created by amo on 04.04.17.
 */
@ApplicationScoped
@Path("/users")
public class UsersController {

  @Inject
  private Userservice userservice;


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Person> getUsers() {
    return userservice.getUsers();
  }


}
