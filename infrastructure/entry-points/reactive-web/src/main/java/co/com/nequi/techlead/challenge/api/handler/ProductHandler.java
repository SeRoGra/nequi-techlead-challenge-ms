package co.com.nequi.techlead.challenge.api.handler;

import co.com.nequi.techlead.challenge.api.dto.product.CreateProductRequest;
import co.com.nequi.techlead.challenge.api.dto.product.UpdateProductRequest;
import co.com.nequi.techlead.challenge.model.product.Product;
import co.com.nequi.techlead.challenge.model.product.dto.UpdateProductCommand;
import co.com.nequi.techlead.challenge.usecase.productmanagement.ProductManagementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.nequi.techlead.challenge.api.enums.PathParam.*;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductManagementUseCase productManagementUseCase;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        String brandId = serverRequest.pathVariable(BRAND_ID.getName());
        String siteId = serverRequest.pathVariable(SITE_ID.getName());
        return serverRequest.bodyToMono(CreateProductRequest.class)
                .flatMap(createProductRequest -> productManagementUseCase.createProduct(
                        Integer.valueOf(brandId), Integer.valueOf(siteId),
                        createProductRequest.getName(), createProductRequest.getStock()))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
        String brandId = serverRequest.pathVariable(BRAND_ID.getName());
        String siteId = serverRequest.pathVariable(SITE_ID.getName());
        String productId = serverRequest.pathVariable(PRODUCT_ID.getName());
        return serverRequest.bodyToMono(UpdateProductRequest.class)
                .flatMap(updateProductRequest -> productManagementUseCase.updateProduct(
                        UpdateProductCommand.builder()
                                .brandId(Integer.valueOf(brandId))
                                .siteId(Integer.valueOf(siteId))
                                .productId(Integer.valueOf(productId))
                                .name(updateProductRequest.getName())
                                .stock(updateProductRequest.getStock())
                                .build()))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
        String brandId = serverRequest.pathVariable(BRAND_ID.getName());
        String siteId = serverRequest.pathVariable(SITE_ID.getName());
        String productId = serverRequest.pathVariable(PRODUCT_ID.getName());
        return productManagementUseCase.deleteProduct(Integer.valueOf(brandId),
                        Integer.valueOf(siteId), Integer.valueOf(productId))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getTopProductsByBrandId(ServerRequest serverRequest) {
        String brandId = serverRequest.pathVariable(BRAND_ID.getName());
        return ServerResponse.ok().body(productManagementUseCase
                .getTopProductsByBrandId(Integer.valueOf(brandId)), Product.class);
    }

    public Mono<ServerResponse> getProductsBySiteId(ServerRequest serverRequest) {
        String brandId = serverRequest.pathVariable(BRAND_ID.getName());
        String siteId = serverRequest.pathVariable(SITE_ID.getName());
        return ServerResponse.ok()
                .body(productManagementUseCase.getProductsByBrandIdAndSiteId(
                        Integer.valueOf(brandId), Integer.valueOf(siteId)), Product.class);
    }
}
