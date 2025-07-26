package com.grupo7.oo2spring.configuration;

import org.apache.catalina.Context;
import org.apache.catalina.session.StandardManager;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatSessionConfig {

    @Bean
    public TomcatContextCustomizer disableSessionPersistence() {
        return new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                StandardManager manager = new StandardManager();
                manager.setPathname(null); // Evita persistir sesión en disco
                context.setManager(manager);
            }
        };
    }
}
