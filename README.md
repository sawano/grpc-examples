# gRPC Java examples

Build the project with
    
    .> ./mvnw clean package

You can then play around with different types of client/server implementations. See below.

## Plain Java gRPC server

Example server using plain Java.

Run with:

    :> java -jar server/target/server-0.0.1-SNAPSHOT.jar

## Plain java gRPC client

Example server using plain Java. Talks to the plain java server.

Run with:

    :> java -jar client/target/client-0.0.1-SNAPSHOT.jar

## Spring Boot server

Example server using Spring Boot.

Run with:

    :> java -jar bootserver/target/boot-server-0.0.1-SNAPSHOT.jar
    
## Spring Boot client

Example client using Spring Boot.

Run with:

    :> java -jar bootclient/target/bootclient-0.0.1-SNAPSHOT.jar

