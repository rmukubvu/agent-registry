package com.agentdna.registry.api.rest;

import com.agentdna.registry.application.commands.*;
import com.agentdna.registry.application.handlers.*;
import com.agentdna.registry.domain.statemachine.AgentStateTransitionException;
import com.agentdna.registry.infrastructure.persistence.AgentRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.NoSuchElementException;
import java.util.UUID;

@Path("/v1/agents")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgentDnaResource {

    private final RegisterAgentHandler  registerHandler;
    private final ActivateAgentHandler  activateHandler;
    private final SuspendAgentHandler   suspendHandler;
    private final ReinstateAgentHandler reinstateHandler;
    private final RevokeAgentHandler    revokeHandler;
    private final VerifyAgentHandler    verifyHandler;
    private final AgentRepository       repository;

    @Inject
    public AgentDnaResource(RegisterAgentHandler  registerHandler,
                            ActivateAgentHandler  activateHandler,
                            SuspendAgentHandler   suspendHandler,
                            ReinstateAgentHandler reinstateHandler,
                            RevokeAgentHandler    revokeHandler,
                            VerifyAgentHandler    verifyHandler,
                            AgentRepository       repository) {
        this.registerHandler  = registerHandler;
        this.activateHandler  = activateHandler;
        this.suspendHandler   = suspendHandler;
        this.reinstateHandler = reinstateHandler;
        this.revokeHandler    = revokeHandler;
        this.verifyHandler    = verifyHandler;
        this.repository       = repository;
    }

    @POST
    public Response register(RegisterAgentPayload payload) {
        var cmd = new RegisterAgentCommand(
                payload.agentName(), payload.publicKeyHex(),
                payload.ownerId(),   payload.ownerName(),
                payload.jurisdiction(), payload.capabilities(),
                payload.parentDnaId(), payload.expiresAt(),
                payload.idemKey());
        var result = registerHandler.handle(cmd);
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @GET
    public Response listAll() {
        return Response.ok(repository.findAll()).build();
    }

    @PUT
    @Path("/{dnaId}/activate")
    public Response activate(@PathParam("dnaId") UUID dnaId,
                             @QueryParam("idemKey") String idemKey) {
        try {
            var result = activateHandler.handle(new ActivateAgentCommand(dnaId, idemKey));
            return Response.ok(result).build();
        } catch (NoSuchElementException e) {
            return notFound(e);
        } catch (AgentStateTransitionException e) {
            return unprocessable(e);
        }
    }

    @PUT
    @Path("/{dnaId}/suspend")
    public Response suspend(@PathParam("dnaId") UUID dnaId, SuspendAgentPayload payload) {
        try {
            var cmd    = new SuspendAgentCommand(dnaId, payload.reason(), payload.idemKey());
            var result = suspendHandler.handle(cmd);
            return Response.ok(result).build();
        } catch (NoSuchElementException e) {
            return notFound(e);
        } catch (AgentStateTransitionException e) {
            return unprocessable(e);
        }
    }

    @PUT
    @Path("/{dnaId}/reinstate")
    public Response reinstate(@PathParam("dnaId") UUID dnaId,
                              @QueryParam("idemKey") String idemKey) {
        try {
            var result = reinstateHandler.handle(new ReinstateAgentCommand(dnaId, idemKey));
            return Response.ok(result).build();
        } catch (NoSuchElementException e) {
            return notFound(e);
        } catch (AgentStateTransitionException e) {
            return unprocessable(e);
        }
    }

    @PUT
    @Path("/{dnaId}/revoke")
    public Response revoke(@PathParam("dnaId") UUID dnaId, RevokeAgentPayload payload) {
        try {
            var cmd    = new RevokeAgentCommand(dnaId, payload.reason(), payload.idemKey());
            var result = revokeHandler.handle(cmd);
            return Response.ok(result).build();
        } catch (NoSuchElementException e) {
            return notFound(e);
        } catch (AgentStateTransitionException e) {
            return unprocessable(e);
        }
    }

    @GET
    @Path("/{dnaId}/verify")
    public Response verify(@PathParam("dnaId") UUID dnaId) {
        try {
            return Response.ok(verifyHandler.handle(dnaId)).build();
        } catch (NoSuchElementException e) {
            return notFound(e);
        }
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private Response notFound(NoSuchElementException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorBody(e.getMessage())).build();
    }

    private Response unprocessable(AgentStateTransitionException e) {
        return Response.status(422).entity(new ErrorBody(e.getMessage())).build();
    }

    private record ErrorBody(String error) {}
}
