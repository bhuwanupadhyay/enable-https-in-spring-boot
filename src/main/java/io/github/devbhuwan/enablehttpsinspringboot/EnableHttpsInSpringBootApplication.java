package io.github.devbhuwan.enablehttpsinspringboot;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.websocket.TomcatWebSocketContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class EnableHttpsInSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnableHttpsInSpringBootApplication.class, args);
    }

    @RestController
    public static class Test {

        @GetMapping("test")
        public String test() {
            return "Test";
        }
    }

    @Configuration
    protected static class HttpsTomcatWebSocketContainerCustomizer extends TomcatWebSocketContainerCustomizer {

        @Value("${server.port:8443}")
        private int serverPort;

        @Override
        public void doCustomize(TomcatEmbeddedServletContainerFactory tomcatContainer) {
            tomcatContainer.addAdditionalTomcatConnectors(getHttpConnector());
            super.doCustomize(tomcatContainer);
        }

        private Connector getHttpConnector() {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setScheme("http");
            connector.setPort(8080);
            connector.setSecure(false);
            connector.setRedirectPort(serverPort);
            return connector;
        }

    }

}
