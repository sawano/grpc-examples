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

package se.sawano.grpc.exeamples.boot.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import se.sawano.grpc.examples.helloworld.HelloServiceGrpc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.Validate.notNull;

public class GRpcStub {

    private final ClientConfiguration clientConf;
    private ManagedChannel channel;
    private HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

    public GRpcStub(final ClientConfiguration clientConf) {
        this.clientConf = notNull(clientConf);
    }

    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder.forAddress(clientConf.serverIp(), clientConf.serverPort())
                                       .usePlaintext(true) // Disable SSL/TLS for the example
                                       .build();
        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }

    public HelloServiceGrpc.HelloServiceBlockingStub stub() {
        return blockingStub;
    }

    @PreDestroy
    public void stop() {
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error while shutting down gRPC channel", e);
        }
    }

    /**
     * Created by dansaw on 2017-02-23.
     */
    public interface ClientConfiguration {

        @NotNull
        String serverIp();

        @NotNull
        Integer serverPort();

    }
}
