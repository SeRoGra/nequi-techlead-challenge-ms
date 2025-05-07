package co.com.nequi.techlead.challenge.api.handler;

import co.com.nequi.techlead.challenge.api.dto.site.CreateSiteRequest;
import co.com.nequi.techlead.challenge.api.dto.site.UpdateSiteRequest;
import co.com.nequi.techlead.challenge.model.site.Site;
import co.com.nequi.techlead.challenge.usecase.sitemanagement.SiteManagementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.nequi.techlead.challenge.api.enums.PathParam.*;
import static co.com.nequi.techlead.challenge.api.utils.PathParamValidator.validateRequiredParam;

@Component
@RequiredArgsConstructor
public class SiteHandler {

    private final SiteManagementUseCase siteManagementUseCase;

    public Mono<ServerResponse> getSitesByBrandId(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        return ServerResponse.ok()
                .body(siteManagementUseCase.getSitesByBrandId(brandId), Site.class);
    }

    public Mono<ServerResponse> createSite(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        return serverRequest.bodyToMono(CreateSiteRequest.class)
                .map(CreateSiteRequest::getName)
                .flatMap(siteName -> siteManagementUseCase.createSite(siteName, brandId))
                .flatMap(site -> ServerResponse.ok().bodyValue(site));
    }

    public Mono<ServerResponse> updateSite(ServerRequest serverRequest) {
        Integer brandId = validateRequiredParam(BRAND_ID.getName(), serverRequest.pathVariable(BRAND_ID.getName()));
        Integer siteId = validateRequiredParam(SITE_ID.getName(), serverRequest.pathVariable(SITE_ID.getName()));
        return serverRequest.bodyToMono(UpdateSiteRequest.class)
                .map(UpdateSiteRequest::getName)
                .flatMap(siteName -> siteManagementUseCase.updateSite(brandId, siteId, siteName))
                .flatMap(site -> ServerResponse.ok().bodyValue(site));
    }

}
