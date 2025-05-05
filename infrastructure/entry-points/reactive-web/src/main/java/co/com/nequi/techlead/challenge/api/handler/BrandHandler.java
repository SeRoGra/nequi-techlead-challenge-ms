package co.com.nequi.techlead.challenge.api.handler;

import co.com.nequi.techlead.challenge.api.dto.CreateBrandRequest;
import co.com.nequi.techlead.challenge.api.dto.UpdateBrandRequest;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import co.com.nequi.techlead.challenge.usecase.brandsmanagement.BrandManagementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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
        String brandId = serverRequest.pathVariable("brandId");
        return serverRequest.bodyToMono(UpdateBrandRequest.class)
                .map(UpdateBrandRequest::getName)
                .flatMap(name -> brandManagementUseCase.updateBrand(Integer.valueOf(brandId), name))
                .flatMap(brand -> ServerResponse.ok().bodyValue(brand));
    }
}
