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

public class ClientApplication {

    private GRpcStub stub;

    public static void main(String[] args) throws Exception {
        final ClientApplication client = new ClientApplication();
        client.init();
        Thread.currentThread().join();
    }


    public void init() {
        final String serverIp = "127.0.0.1";
        final int serverPort = 8082;

        stub = new GRpcStub(serverIp, serverPort);
        stub.init();

        new HelloClient(stub).startTalking();

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void stop() {
        stub.stop();
    }


}
