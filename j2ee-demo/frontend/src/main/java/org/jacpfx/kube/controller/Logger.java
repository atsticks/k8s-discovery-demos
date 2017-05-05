package org.jacpfx.kube.controller;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by atsticks on 03.05.17.
 */
@Provider
public class Logger implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("IN: " + requestContext.getRequest());
    }
}
