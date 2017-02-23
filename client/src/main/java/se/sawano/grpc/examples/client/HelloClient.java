package se.sawano.grpc.examples.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.sawano.grpc.examples.helloworld.Answer;
import se.sawano.grpc.examples.helloworld.Guest;
import se.sawano.grpc.examples.helloworld.HelloServiceGrpc;

import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.Validate.notNull;

public class HelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient.class);

    private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

    public HelloClient(final HelloServiceGrpc.HelloServiceBlockingStub blockingStub) {
        this.blockingStub = notNull(blockingStub);
    }

    public void startTalking() {
        Executors.newSingleThreadScheduledExecutor()
                 .scheduleAtFixedRate(this::sayHello, 2, 5, TimeUnit.SECONDS);
    }

    private void sayHello() {
        try {
            LOGGER.info("Saying hello to server...");

            final Guest guest = Guest.newBuilder().setName(myName()).build();
            final Answer answer = blockingStub.sayHello(guest);

            LOGGER.info("Server says: {}", answer.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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
