package se.sawano.grpc.examples.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.sawano.grpc.examples.helloworld.Answer;
import se.sawano.grpc.examples.helloworld.Guest;
import se.sawano.grpc.examples.helloworld.HelloServiceGrpc;

public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloService.class);

    @Override
    public void sayHello(Guest request, StreamObserver<Answer> responseObserver) {
        LOGGER.info("Got request from: '{}'", request.getName());

        Answer answer = Answer.newBuilder()
                              .setMessage("Hello " + request.getName() + "!")
                              .build();

        responseObserver.onNext(answer);
        responseObserver.onCompleted();
    }

}
