package co.com.nequi.techlead.challenge.api.handler;

import co.com.nequi.techlead.challenge.api.dto.product.CreateProductRequest;
import co.com.nequi.techlead.challenge.api.dto.product.UpdateProductRequest;
import co.com.nequi.techlead.challenge.model.product.Product;
import co.com.nequi.techlead.challenge.model.product.dto.UpdateProductCommand;
import co.com.nequi.techlead.challenge.usecase.productmanagement.ProductManagementUseCase;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.nequi.techlead.challenge.api.enums.PathParam.*;
import static co.com.nequi.techlead.challenge.api.utils.PathParamValidator.validateRequiredParam;
import static co.com.nequi.techlead.challenge.api.utils.RequestBodyValidator.validateBody;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final Validator validator;
    private final ProductManagementUseCase productManagementUseCase;

    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        Integer siteId = validateRequiredParam(SITE_ID.getName(), serverRequest.pathVariable(SITE_ID.getName()));
        return validateBody(serverRequest.bodyToMono(CreateProductRequest.class), validator)
                .flatMap(createProductRequest -> productManagementUseCase.createProduct(
                        brandId, siteId, createProductRequest.getName(), createProductRequest.getStock()))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        Integer siteId = validateRequiredParam(SITE_ID.getName(), serverRequest.pathVariable(SITE_ID.getName()));
        Integer productId = validateRequiredParam(PRODUCT_ID.getName(), serverRequest.pathVariable(PRODUCT_ID.getName()));
        return validateBody(serverRequest.bodyToMono(UpdateProductRequest.class), validator)
                .flatMap(updateProductRequest -> productManagementUseCase.updateProduct(
                        UpdateProductCommand.builder()
                                .brandId(brandId)
                                .siteId(siteId)
                                .productId(productId)
                                .name(updateProductRequest.getName())
                                .stock(updateProductRequest.getStock())
                                .build()))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        Integer siteId = validateRequiredParam(SITE_ID.getName(), serverRequest.pathVariable(SITE_ID.getName()));
        Integer productId = validateRequiredParam(PRODUCT_ID.getName(), serverRequest.pathVariable(PRODUCT_ID.getName()));
        return productManagementUseCase.deleteProduct(brandId, siteId, productId)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getTopProductsByBrandId(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        return ServerResponse.ok().body(productManagementUseCase
                .getTopProductsByBrandId(brandId), Product.class);
    }

    public Mono<ServerResponse> getProductsBySiteId(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        Integer siteId = validateRequiredParam(SITE_ID.getName(), serverRequest.pathVariable(SITE_ID.getName()));
        return ServerResponse.ok()
                .body(productManagementUseCase.getProductsByBrandIdAndSiteId(brandId, siteId), Product.class);
    }
}
