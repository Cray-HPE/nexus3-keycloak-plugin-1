package org.github.flytreeleft.nexus3.keycloak.plugin.internal.http;

import java.net.URI;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.RequestBuilder;
import org.keycloak.representations.adapters.config.AdapterConfig;

public class Http {
    private final AdapterConfig config;
    private final HttpClient httpClient;
    private final ClientAuthenticator authenticator;

    public Http(AdapterConfig config, HttpClient httpClient, ClientAuthenticator authenticator) {
        this.config = config;
        this.httpClient = httpClient;
        this.authenticator = authenticator;
    }

    public <R> HttpMethod<R> get(String path, Object... placeholders) {
        return get(uri(path, placeholders));
    }

    public <R> HttpMethod<R> get(URI uri) {
        return method(RequestBuilder.get().setUri(uri));
    }

    public <R> HttpMethod<R> post(String path, Object... placeholders) {
        return post(uri(path, placeholders));
    }

    public <R> HttpMethod<R> post(URI uri) {
        return method(RequestBuilder.post().setUri(uri));
    }

    public <R> HttpMethod<R> put(String path, Object... placeholders) {
        return put(uri(path, placeholders));
    }

    public <R> HttpMethod<R> put(URI uri) {
        return method(RequestBuilder.put().setUri(uri));
    }

    public <R> HttpMethod<R> delete(String path, Object... placeholders) {
        return delete(uri(path, placeholders));
    }

    public <R> HttpMethod<R> delete(URI uri) {
        return method(RequestBuilder.delete().setUri(uri));
    }

    private URI uri(String path, Object... placeholders) {
        String authServerUrl = this.config.getAuthServerUrl();

        if (authServerUrl.endsWith("/") && path.startsWith("/")) {
            authServerUrl = authServerUrl.substring(0, authServerUrl.length() - 1);
        }

        return URI.create(authServerUrl + String.format(path, placeholders));
    }

    private <R> HttpMethod<R> method(RequestBuilder builder) {
        return new HttpMethod<>(this.httpClient, this.authenticator, builder);
    }
}
