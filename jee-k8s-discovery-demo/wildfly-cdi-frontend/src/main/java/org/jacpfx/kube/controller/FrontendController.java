package org.jacpfx.kube.controller;


import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jacpfx.kube.domain.Person;
import org.jacpfx.kube.service.Userservice;


/**
 * Created by amo on 04.04.17.
 */
@ApplicationScoped
@Path("/api")
public class FrontendController {

  @Inject
  private Userservice userservice;


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/users/{id}")
  public Person get(@PathParam("id") String uuid) {

    return userservice.get(uuid);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/users")
  public List<Person> getUsers() {
    return userservice.getUsers();
  }


}
