package org.jacpfx.kube.controller;/*
 * (C) Copyright 2015-2017 Trivadis AG. All rights reserved.
 */

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by atsticks on 03.05.17.
 */
@Provider
public class SimpleExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException exception) {
        return Response.serverError()
                .header("x-reason", exception.getMessage())
                .build();
    }
}
