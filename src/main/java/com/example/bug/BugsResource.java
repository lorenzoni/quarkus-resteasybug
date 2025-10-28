package com.example.bug;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

@RequestScoped
@Path("/bugs")
public class BugsResource {

  @Inject
  BugsSubResource bugsSubResource;

  @PermitAll
  @Path("/sub")
  public BugsSubResource works() {
    return bugsSubResource;
  }
}
