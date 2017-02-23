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

package se.sawano.grpc.examples.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Collections.singletonList;


public class ServerApplication {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GRpcServer gRpcServer;


    public static void main(String[] args) throws Exception {
        final ServerApplication server = new ServerApplication();
        server.init();
        server.blockUntilShutdown();
    }


    public void init() {
        logger.info("Starting gRPC server...");

        gRpcServer = new GRpcServer(8082, singletonList(new HelloService()));
        gRpcServer.init();

        logger.info("Started gRPC server");
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (gRpcServer != null) {
            gRpcServer.blockUntilShutdown();
        }
    }
}
