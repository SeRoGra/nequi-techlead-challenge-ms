package co.com.nequi.techlead.challenge.api.router;

import co.com.nequi.techlead.challenge.api.handler.BrandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BrandRouter {

    @Bean
    public RouterFunction<ServerResponse> routerBrand(BrandHandler handler) {
        return nest(path("/api"),
                route(GET("/brands"), handler::getAllBrands)
                        .andRoute(POST("/brands"), handler::createBrand)
                        .andRoute(PUT("/brands/{brandId}"), handler::updateBrand));
    }

}
