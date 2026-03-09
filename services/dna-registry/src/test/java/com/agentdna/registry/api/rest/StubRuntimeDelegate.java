package com.agentdna.registry.api.rest;

import jakarta.ws.rs.SeBootstrap;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.RuntimeDelegate;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletionStage;

/**
 * Minimal JAX-RS RuntimeDelegate stub for unit tests (no Quarkus/Servlet container required).
 */
public class StubRuntimeDelegate extends RuntimeDelegate {

    @Override
    public UriBuilder createUriBuilder() {
        throw new UnsupportedOperationException("createUriBuilder");
    }

    @Override
    public Response.ResponseBuilder createResponseBuilder() {
        return new StubResponseBuilder();
    }

    @Override
    public Variant.VariantListBuilder createVariantListBuilder() {
        throw new UnsupportedOperationException("createVariantListBuilder");
    }

    @Override
    public <T> T createEndpoint(Application application, Class<T> endpointType) {
        throw new UnsupportedOperationException("createEndpoint");
    }

    @Override
    public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> type) {
        return null;
    }

    @Override
    public Link.Builder createLinkBuilder() {
        throw new UnsupportedOperationException("createLinkBuilder");
    }

    @Override
    public SeBootstrap.Configuration.Builder createConfigurationBuilder() {
        throw new UnsupportedOperationException("createConfigurationBuilder");
    }

    @Override
    public CompletionStage<SeBootstrap.Instance> bootstrap(
            Application application, SeBootstrap.Configuration configuration) {
        throw new UnsupportedOperationException("bootstrap(Application)");
    }

    @Override
    public CompletionStage<SeBootstrap.Instance> bootstrap(
            Class<? extends Application> applicationClass, SeBootstrap.Configuration configuration) {
        throw new UnsupportedOperationException("bootstrap(Class)");
    }

    @Override
    public EntityPart.Builder createEntityPartBuilder(String fieldName) {
        throw new UnsupportedOperationException("createEntityPartBuilder");
    }

    // ── Stub Response.ResponseBuilder ────────────────────────────────────────

    private static class StubResponseBuilder extends Response.ResponseBuilder {

        private int    status = 200;
        private Object entity;
        private final MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

        @Override public Response.ResponseBuilder status(int status) {
            this.status = status; return this;
        }

        @Override public Response.ResponseBuilder status(int status, String reasonPhrase) {
            this.status = status; return this;
        }

        @Override public Response.ResponseBuilder entity(Object entity) {
            this.entity = entity; return this;
        }

        @Override public Response.ResponseBuilder entity(Object entity, Annotation[] annotations) {
            this.entity = entity; return this;
        }

        @Override public Response.ResponseBuilder type(MediaType type)  { return this; }
        @Override public Response.ResponseBuilder type(String type)     { return this; }
        @Override public Response.ResponseBuilder allow(String... methods)   { return this; }
        @Override public Response.ResponseBuilder allow(Set<String> methods) { return this; }
        @Override public Response.ResponseBuilder cacheControl(CacheControl cc) { return this; }
        @Override public Response.ResponseBuilder encoding(String encoding)     { return this; }

        @Override public Response.ResponseBuilder header(String name, Object value) {
            headers.add(name, value); return this;
        }

        @Override public Response.ResponseBuilder replaceAll(MultivaluedMap<String, Object> h) {
            headers.clear(); headers.putAll(h); return this;
        }

        @Override public Response.ResponseBuilder language(String language) { return this; }
        @Override public Response.ResponseBuilder language(Locale language) { return this; }
        @Override public Response.ResponseBuilder variant(Variant variant)  { return this; }
        @Override public Response.ResponseBuilder variants(Variant... v)    { return this; }
        @Override public Response.ResponseBuilder variants(List<Variant> v) { return this; }
        @Override public Response.ResponseBuilder tag(EntityTag tag)    { return this; }
        @Override public Response.ResponseBuilder tag(String tag)       { return this; }
        @Override public Response.ResponseBuilder lastModified(Date d)  { return this; }
        @Override public Response.ResponseBuilder location(URI uri)     { return this; }
        @Override public Response.ResponseBuilder contentLocation(URI u){ return this; }
        @Override public Response.ResponseBuilder cookie(NewCookie... c){ return this; }
        @Override public Response.ResponseBuilder expires(Date expires) { return this; }
        @Override public Response.ResponseBuilder links(Link... links)  { return this; }
        @Override public Response.ResponseBuilder link(URI uri, String rel)    { return this; }
        @Override public Response.ResponseBuilder link(String uri, String rel) { return this; }

        @Override
        public Response.ResponseBuilder clone() {
            var copy = new StubResponseBuilder();
            copy.status = this.status;
            copy.entity = this.entity;
            copy.headers.putAll(this.headers);
            return copy;
        }

        @Override
        public Response build() {
            return new StubResponse(status, entity, headers);
        }
    }

    // ── Stub Response ─────────────────────────────────────────────────────────

    private static class StubResponse extends Response {

        private final int    status;
        private final Object entity;
        private final MultivaluedMap<String, Object> headers;

        StubResponse(int status, Object entity, MultivaluedMap<String, Object> headers) {
            this.status  = status;
            this.entity  = entity;
            this.headers = headers;
        }

        @Override public int        getStatus()       { return status; }
        @Override public Object     getEntity()       { return entity; }
        @Override public StatusType getStatusInfo()   { return Status.fromStatusCode(status); }
        @Override public MultivaluedMap<String, Object> getMetadata() { return headers; }
        @Override public MultivaluedMap<String, String> getStringHeaders() { return new MultivaluedHashMap<>(); }
        @Override public String     getHeaderString(String name)      { return null; }
        @Override public Set<String> getAllowedMethods()              { return Set.of(); }
        @Override public Map<String, NewCookie> getCookies()         { return Map.of(); }
        @Override public EntityTag  getEntityTag()                    { return null; }
        @Override public Date       getDate()                         { return null; }
        @Override public Date       getLastModified()                 { return null; }
        @Override public URI        getLocation()                     { return null; }
        @Override public Set<Link>  getLinks()                        { return Set.of(); }
        @Override public boolean    hasLink(String relation)          { return false; }
        @Override public Link       getLink(String relation)          { return null; }
        @Override public Link.Builder getLinkBuilder(String relation) { return null; }
        @Override public boolean    hasEntity()                       { return entity != null; }
        @Override public <T> T readEntity(Class<T> type)             { return type.cast(entity); }
        @Override public <T> T readEntity(GenericType<T> type)       { return null; }
        @Override public <T> T readEntity(Class<T> type, Annotation[] ann) { return type.cast(entity); }
        @Override public <T> T readEntity(GenericType<T> type, Annotation[] ann) { return null; }
        @Override public boolean    bufferEntity()                    { return false; }
        @Override public void       close()                           {}
        @Override public MediaType  getMediaType()                    { return MediaType.APPLICATION_JSON_TYPE; }
        @Override public Locale     getLanguage()                     { return null; }
        @Override public int        getLength()                       { return -1; }
    }
}
