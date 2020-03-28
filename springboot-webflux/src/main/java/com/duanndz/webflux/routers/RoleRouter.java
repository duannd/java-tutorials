package com.duanndz.webflux.routers;

import com.duanndz.webflux.handlers.RoleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class RoleRouter {

    @Bean
    public RouterFunction<ServerResponse> roleRoutes(RoleHandler roleHandler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/api/roles")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), roleHandler::addRole);
    }

}
