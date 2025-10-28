package com.example.bug;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Dependent
public class BugsSubResource {

  /**
   * This injection of a rest client causes a failure.
   * http://localhost:8080/bugs/sub results in: 405 Method Not Allowed
   */
  @Inject
  @RestClient
  MyRemoteService myRemoteService;

  @GET
  public String hello() {
    return "Hello from Quarkus REST";
  }

}
