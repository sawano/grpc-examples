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

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.sawano.grpc.examples.helloworld.Answer;
import se.sawano.grpc.examples.helloworld.Guest;
import se.sawano.grpc.examples.helloworld.HelloServiceGrpc;

public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sayHello(Guest request, StreamObserver<Answer> responseObserver) {
        logger.info("Got request from: '{}'", request.getName());

        Answer answer = Answer.newBuilder()
                              .setMessage("Hello " + request.getName() + "!")
                              .build();

        responseObserver.onNext(answer);
        responseObserver.onCompleted();
    }

}
