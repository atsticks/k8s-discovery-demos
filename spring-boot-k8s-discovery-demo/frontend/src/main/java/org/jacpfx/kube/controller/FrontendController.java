package org.jacpfx.kube.controller;

import io.fabric8.annotations.ServiceName;
import java.time.Duration;
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
  public   String readhost;

  @ServiceName("read")
  private String service;

  @Autowired
  private WebClient client;


  @GetMapping(path = "/users/{id}")
  public Mono<Person> get(@PathVariable("id") String uuid) {
    System.out.println("FrontendController  call get");
    return this.client
        .get().uri("http://"+ getHost() + "/api/users/{id}", uuid)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .then(response -> response.bodyToMono(Person.class));
  }

  @GetMapping(path = "/users")
  public Flux<Person> getUsers() {
    return this.client
        .get().uri("http://"+ getHost() + "/api/users")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .flatMap(response -> response.bodyToFlux(Person.class));
  }

  private String getHost() {
    System.out.println("HOST: "+service);
    return service!=null?service:readhost;
  }
}
