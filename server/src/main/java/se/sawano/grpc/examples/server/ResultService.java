/*
 * Copyright 2018 Daniel Sawano
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

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.sawano.grpc.examples.helloworld.Guest;
import se.sawano.grpc.examples.helloworld.Result;
import se.sawano.grpc.examples.helloworld.ResultServiceGrpc;

public final class ResultService extends ResultServiceGrpc.ResultServiceImplBase {

    private static Logger LOGGER = LoggerFactory.getLogger(ResultService.class);

    @Override
    public void doProcess(final Guest request, final StreamObserver<Result> responseObserver) {
        LOGGER.info("Got request from: '{}'", request.getName());

        final Result result = resultWithNoFailure();

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    private static Result resultWithNoFailure() {
        return Result.newBuilder()
                     // .setFailure() deliberately don't set the failure
                     .build();
    }

    private static Result resultWithFailureNotAvailableOnClient() {
        return Result.newBuilder()
                     .setFailureValue(42)
                     .build();
    }

}
