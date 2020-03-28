package com.duanndz.webflux.handlers;

import com.duanndz.webflux.models.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RoleHandler {

    public Mono<ServerResponse> addRole(ServerRequest request) {
        Mono<Role> role = request.bodyToMono(Role.class);
        return ServerResponse.ok().body(role, Role.class);
    }

}
