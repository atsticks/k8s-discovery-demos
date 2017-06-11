package org.jacpfx.kube.service;

import io.fabric8.annotations.ServiceName;
import io.fabric8.kubernetes.api.model.EndpointSubset;
import io.fabric8.kubernetes.api.model.EndpointsList;
import io.fabric8.kubernetes.api.model.PodList;
import jacpfx.discovery.Endpoints;
import jacpfx.discovery.Label;
import jacpfx.discovery.Pods;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.jacpfx.discovery.annotation.K8SDiscovery;
import org.jacpfx.kube.domain.Person;

/**
 * Created by amo on 11.06.17.
 */
@ApplicationScoped
@K8SDiscovery
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


    return null;
  }



  public List<Person> getUsers() {
    System.out.println("TEST");
    return null;
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
