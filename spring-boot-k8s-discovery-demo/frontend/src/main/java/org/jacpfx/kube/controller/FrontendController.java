package org.jacpfx.kube.controller;

import io.fabric8.annotations.ServiceName;
import io.fabric8.kubernetes.api.model.EndpointSubset;
import io.fabric8.kubernetes.api.model.EndpointsList;
import io.fabric8.kubernetes.api.model.PodList;
import java.util.List;
import org.jacpfx.discovery.Endpoints;
import org.jacpfx.discovery.Label;
import org.jacpfx.discovery.Pods;
import org.jacpfx.kube.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Created by amo on 04.04.17.
 */
@RestController
@RequestMapping("/api")
public class FrontendController {

  @Value("${read-host}")
  public String readhost;

  @ServiceName("read")
  private String service;

  @Label(name = "visualize", labelValue = "true")
  private Endpoints endpoints;

  @Label(name = "hallo")
  private Pods pods;

  @Autowired
  private WebClient client;


  @GetMapping(path = "/users/{id}")
  public Mono<Person> get(@PathVariable("id") String uuid) {
    return this.client
        .get().uri("http://" + getHost() + "/api/users/{id}", uuid)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMap(resp -> resp.bodyToMono(Person.class));
  }

  @GetMapping(path = "/users")
  public Flux<Person> getUsers() {
    printEndpoints();
    printPodList();
    return this.client
        .get().uri("http://" + getHost() + "/api/users")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMapMany(response -> response.bodyToFlux(Person.class));
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

  private String getHost() {
    System.out.println("HOST: " + service);
    return service != null ? service : readhost;
  }
}
