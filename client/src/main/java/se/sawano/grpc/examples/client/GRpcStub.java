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

package se.sawano.grpc.examples.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import se.sawano.grpc.examples.helloworld.GoodbyeServiceGrpc;
import se.sawano.grpc.examples.helloworld.HelloServiceGrpc;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class GRpcStub {

    private final String serverIp;
    private final int serverPort;
    private ManagedChannel channel;
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;
    private GoodbyeServiceGrpc.GoodbyeServiceBlockingStub goodbyeServiceBlockingStub;

    public GRpcStub(final String serverIp, final Integer serverPort) {
        this.serverIp = notEmpty(serverIp);
        this.serverPort = notNull(serverPort);
    }


    public void init() {
        channel = ManagedChannelBuilder.forAddress(serverIp, serverPort)
                                       .usePlaintext(true) // Disable SSL/TLS for the example
                                       .build();
        helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
        goodbyeServiceBlockingStub = GoodbyeServiceGrpc.newBlockingStub(channel);
    }

    public HelloServiceGrpc.HelloServiceBlockingStub helloStub() {
        return helloServiceBlockingStub;
    }

    public GoodbyeServiceGrpc.GoodbyeServiceBlockingStub goodbyeStub() {
        return goodbyeServiceBlockingStub;
    }


    public void stop() {
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error while shutting down gRPC channel", e);
        }
    }
}
