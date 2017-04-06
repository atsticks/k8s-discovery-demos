package org.jacpfx.kube;

import org.jacpfx.postprocessor.Fabric8DiscoveryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class FrontendApplication {

  public static void main(String[] args) {
    SpringApplication.run(FrontendApplication.class, args);
  }

  @Bean
  WebClient webclient() {
    return WebClient.create();
  }

  @Bean
  Fabric8DiscoveryPostProcessor processor() {
    return new Fabric8DiscoveryPostProcessor();
  }




}
