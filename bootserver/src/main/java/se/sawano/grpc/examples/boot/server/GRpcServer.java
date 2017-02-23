/*
 * Copyright 2017 Daniel Sawano
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.sawano.grpc.examples.boot.server;

import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import static org.apache.commons.lang3.Validate.noNullElements;
import static org.apache.commons.lang3.Validate.notNull;

public class GRpcServer {

    public interface ServerConfiguration {

        int port();

    }

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ServerConfiguration configuration;
    private final List<BindableService> bindableServices;

    private io.grpc.Server grpcServer;

    public GRpcServer(final ServerConfiguration configuration, final List<BindableService> bindableServices) {
        this.configuration = notNull(configuration);
        this.bindableServices = noNullElements(bindableServices);
    }

    @PostConstruct
    public void init() {
        try {
            logger.info("Starting gRPC server...");

            final ServerBuilder<?> builder = ServerBuilder.forPort(configuration.port());
            bindableServices.forEach(builder::addService);
            grpcServer = builder.build().start();

            logger.info("Started gRPC server");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @PreDestroy
    public void stop() {
        logger.info("Shutting down gRPC server...");
        if (grpcServer != null) {
            grpcServer.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (grpcServer != null) {
            grpcServer.awaitTermination();
        }
    }
}
