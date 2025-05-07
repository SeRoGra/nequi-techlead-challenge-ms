package co.com.nequi.techlead.challenge.api.handler;

import co.com.nequi.techlead.challenge.api.dto.brand.CreateBrandRequest;
import co.com.nequi.techlead.challenge.api.dto.brand.UpdateBrandRequest;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.usecase.brandsmanagement.BrandManagementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.nequi.techlead.challenge.api.enums.PathParam.BRAND_ID;
import static co.com.nequi.techlead.challenge.api.utils.PathParamValidator.validateRequiredParam;

@Component
@RequiredArgsConstructor
public class BrandHandler {

    private final BrandManagementUseCase brandManagementUseCase;

    public Mono<ServerResponse> getAllBrands(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(brandManagementUseCase.getAllBrands(), Brand.class);
    }

    public Mono<ServerResponse> createBrand(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CreateBrandRequest.class)
                .map(CreateBrandRequest::getName)
                .flatMap(brandManagementUseCase::createBrand)
                .flatMap(brand -> ServerResponse.ok().bodyValue(brand));
    }

    public Mono<ServerResponse> updateBrand(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        return serverRequest.bodyToMono(UpdateBrandRequest.class)
                .map(UpdateBrandRequest::getName)
                .flatMap(name -> brandManagementUseCase.updateBrand(brandId, name))
                .flatMap(brand -> ServerResponse.ok().bodyValue(brand));
    }
}
