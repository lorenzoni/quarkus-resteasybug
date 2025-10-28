package com.example.works;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

@RequestScoped
@Path("/works")
public class WorksResource {

  @Inject
  WorksSubResource worksSubResource;

  @PermitAll
  @Path("/sub")
  public WorksSubResource works() {
    return worksSubResource;
  }
}
