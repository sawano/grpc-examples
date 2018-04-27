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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.sawano.grpc.examples.helloworld.Answer;
import se.sawano.grpc.examples.helloworld.Guest;
import se.sawano.grpc.examples.helloworld.Result;

import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.Validate.notNull;

public class HelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient.class);

    private final GRpcStub stub;

    public HelloClient(final GRpcStub stub) {
        this.stub = notNull(stub);
    }

    public void startTalking() {
        Executors.newSingleThreadScheduledExecutor()
                 .scheduleAtFixedRate(this::sayHello, 2, 5, TimeUnit.SECONDS);
    }

    private void sayHello() {
        try {
            LOGGER.info("Saying hello to server...");

            final Guest guest = Guest.newBuilder().setName(myName()).build();
            final Answer answer = stub.helloStub().sayHello(guest);

            LOGGER.info("Server says: {}", answer.getMessage());

            LOGGER.info("Saying goodbye to server...");

            final Answer goodbyeAnswer = stub.goodbyeStub().sayGoodbye(guest);

            LOGGER.info("Server says: {}", goodbyeAnswer.getMessage());

            LOGGER.info("Asking server to process...");

            final Result result = stub.resultStub().doProcess(guest);

            LOGGER.info("Result failure was {}", result.getFailure());

        } catch (Exception e) {
            LOGGER.error("Error while talking to server", e);
        }
    }

    private String myName() {
        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            throw new UncheckedIOException(e);
        }
    }

}
