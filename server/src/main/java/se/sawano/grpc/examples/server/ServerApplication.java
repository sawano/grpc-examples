package se.sawano.grpc.examples.server;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;


public class ServerApplication {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private io.grpc.Server grpcServer;

    public static void main(String[] args) throws Exception {
        final ServerApplication server = new ServerApplication();
        server.init();
        server.blockUntilShutdown();
    }


    public void init() {
        try {
            logger.info("Starting gRPC server...");

            grpcServer = ServerBuilder.forPort(8082)
                                      .addService(new HelloService())
                                      .build()
                                      .start();

            logger.info("Started gRPC server");

            Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


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
