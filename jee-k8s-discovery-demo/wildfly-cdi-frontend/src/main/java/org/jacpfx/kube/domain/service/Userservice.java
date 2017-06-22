package org.jacpfx.kube.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import io.fabric8.annotations.ServiceName;
import io.fabric8.kubernetes.api.model.EndpointSubset;
import io.fabric8.kubernetes.api.model.EndpointsList;
import io.fabric8.kubernetes.api.model.PodList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jacpfx.discovery.Endpoints;
import org.jacpfx.discovery.Label;
import org.jacpfx.discovery.Pods;
import org.jacpfx.discovery.annotation.K8SDiscovery;
import org.jacpfx.kube.domain.model.Person;
import org.jacpfx.kube.domain.model.PersonWrapper;

/**
 * Created by amo on 11.06.17.
 */
@K8SDiscovery
@ApplicationScoped
public class Userservice {

  @ServiceName("read")
  private String service;


  @ServiceName("frontend")
  private String service2;

  @Label(name = "visualize", labelValue = "true")
  private Endpoints endpoints;

  @Label(name = "hallo")
  private Pods pods;

  private final Client client = ClientBuilder.newClient();


  public Person get(String uuid) {

    WebTarget myResource = client.target("http://"+service).path("/api/users/"+uuid);
    Person user = myResource.request(MediaType.APPLICATION_JSON).get(Person.class);
    return user;
  }



  public List<Person> getUsers() {
    printEndpoints();
    printPodList();
    WebTarget myResource = client.target("http://"+service).path("/api/users");
    List<Person> users = myResource.request(MediaType.APPLICATION_JSON)
        .get(new GenericType<List<Person>>() {});
    return users;
  }


  private void printEndpoints() {
    final EndpointsList endpointsInNamespace = endpoints.getEndpoints();
    endpointsInNamespace.getItems().forEach(item -> {
      System.out.println("item: " + item.getMetadata().getName());
      List<EndpointSubset> subsets = item.getSubsets();
      subsets.forEach(sub -> {
        System.out.println("subset: " + sub);
        sub.getAddresses().forEach(add -> {
          System.out.println("- address: " + add.getIp());
        });
        sub.getPorts().forEach(port -> {
          System.out.println("- port: " + port.getPort());
        });
      });
    });
  }

  private void printPodList() {
    final PodList podlist = pods.getPods();
    podlist.getItems().forEach(pod -> {
      System.out.println("item: " + pod.getMetadata().getName());
      pod.getMetadata().getAdditionalProperties().entrySet().forEach(entry -> {
        System.out.println("key: "+entry.getKey()+" value:"+entry.getValue());
      });
    });
  }

}
