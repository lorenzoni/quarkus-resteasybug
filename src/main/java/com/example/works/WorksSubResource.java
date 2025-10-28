package com.example.works;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.GET;

@Dependent
public class WorksSubResource {

  @GET
  public String hello() {
    return "Hello from Quarkus REST";
  }

}
