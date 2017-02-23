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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@EnableConfigurationProperties(Conf.ClientConf.class)
public class Conf {

    @Bean
    HelloClient helloClient(final GRpcStub.ClientConfiguration clientConf) {
        return new HelloClient(createStub(clientConf));
    }

    @Bean
    GRpcStub createStub(final GRpcStub.ClientConfiguration clientConf) {
        return new GRpcStub(clientConf);
    }

    @ConfigurationProperties(prefix = "grpc")
    @Validated
    public static class ClientConf implements GRpcStub.ClientConfiguration {

        private String serverIp;
        private Integer serverPort;


        @Override
        public String serverIp() {
            return serverIp;
        }

        @Override
        public Integer serverPort() {
            return serverPort;
        }

        public void setServerIp(final String serverIp) {
            this.serverIp = serverIp;
        }

        public void setServerPort(final Integer serverPort) {
            this.serverPort = serverPort;
        }
    }

}
