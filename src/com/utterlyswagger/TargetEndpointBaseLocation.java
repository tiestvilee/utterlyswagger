package com.utterlyswagger;

import com.googlecode.totallylazy.Option;

public class TargetEndpointBaseLocation {
    public final Option<String> basePath;
    public final Option<String> host;

    public TargetEndpointBaseLocation(Option<String> basePath, Option<String> host) {
        this.basePath = basePath;
        this.host = host;
    }
}
