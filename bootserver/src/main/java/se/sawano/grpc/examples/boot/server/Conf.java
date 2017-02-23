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

import io.grpc.BindableService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration
@EnableConfigurationProperties(Conf.ServerConf.class)
public class Conf {

    @Bean
    HelloService helloService() {
        return new HelloService();
    }

    @Bean
    GRpcServer gRpcServer(final GRpcServer.ServerConfiguration configuration, final List<BindableService> bindableServices) {
        return new GRpcServer(configuration, bindableServices);
    }

    @ConfigurationProperties(prefix = "grpc")
    @Validated
    public static class ServerConf implements GRpcServer.ServerConfiguration {

        @NotNull
        private Integer port;

        @Override
        public int port() {
            return port;
        }

        public void setPort(final Integer port) {
            this.port = port;
        }
    }

}
