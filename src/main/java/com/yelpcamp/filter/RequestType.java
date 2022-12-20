package com.yelpcamp.filter;

public class RequestType {

    private final String uriPattern;
    private final String method;

    public RequestType(String uriPattern, String method) {
        this.uriPattern = uriPattern;
        this.method = method;
    }

    public boolean matches(String uri, String method){
        return uri.matches(uriPattern) && method.equals(this.method);
    }
}
