package co.com.nequi.techlead.challenge.api.router;

import co.com.nequi.techlead.challenge.api.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> routerProduct(ProductHandler handler) {
        return nest(path("/api"),
                route(POST("/brands/{brandId}/sites/{siteId}/products"), handler::createProduct)
                .andRoute(PUT("/brands/{brandId}/sites/{siteId}/products/{productId}"), handler::updateProductName)
                .andRoute(PUT("/brands/{brandId}/sites/{siteId}/products/{productId}/stock"), handler::updateProductStock)
                .andRoute(DELETE("/brands/{brandId}/sites/{siteId}/products/{productId}"), handler::deleteProduct)
                .andRoute(GET("/brands/{brandId}/top-products-by-site"), handler::getTopProductsBySite));
    }

}
