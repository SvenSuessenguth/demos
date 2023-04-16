package org.cc.testing.testcontainers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;

@Path("/resources")
public class TestcontainersResource {
  @GET
  @Path("/ping")
  public Response ping() {
    return Response.ok().build();
  }

  @GET
  @Path("/localdatetime")
  public Response localdatetime() {
    return Response.ok().entity(LocalDateTime.now().toString()).build();
  }
}
