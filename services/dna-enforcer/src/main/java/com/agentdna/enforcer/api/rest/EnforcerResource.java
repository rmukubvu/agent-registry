package com.agentdna.enforcer.api.rest;

import com.agentdna.enforcer.application.EnforcerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/enforce")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnforcerResource {

    private final EnforcerService service;

    @Inject
    public EnforcerResource(EnforcerService service) {
        this.service = service;
    }

    @POST
    public Response enforce(EnforceRequest request) {
        var result = service.enforce(request);
        return Response.ok(result).build();
    }
}
