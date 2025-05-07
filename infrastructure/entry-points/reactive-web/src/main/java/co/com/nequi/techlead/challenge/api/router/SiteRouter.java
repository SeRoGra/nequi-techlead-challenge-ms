package co.com.nequi.techlead.challenge.api.router;

import co.com.nequi.techlead.challenge.api.handler.SiteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SiteRouter {

    @Bean
    public RouterFunction<ServerResponse> routerSite(SiteHandler handler) {
        return nest(path("/api"),
                route(GET("/brands/{brandId}/sites"), handler::getAllSitesByBrandId)
                .andRoute(POST("/brands/{brandId}/sites"), handler::createSite)
                .andRoute(PUT("/brands/{brandId}/sites/{siteId}"), handler::updateSite));
    }


}
