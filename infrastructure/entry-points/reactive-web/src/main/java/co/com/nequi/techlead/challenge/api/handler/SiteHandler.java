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

@Component
@RequiredArgsConstructor
public class SiteHandler {

    private final SiteManagementUseCase siteManagementUseCase;

    public Mono<ServerResponse> getAllSitesByBrandId(ServerRequest serverRequest) {
        String brandId = serverRequest.pathVariable("brandId");
        return ServerResponse.ok()
                .body(siteManagementUseCase.getAllSitesByBrandId(Integer.valueOf(brandId)), Site.class);
    }

    public Mono<ServerResponse> createSite(ServerRequest serverRequest) {
        String brandId = serverRequest.pathVariable("brandId");
        return serverRequest.bodyToMono(CreateSiteRequest.class)
                .flatMap(createSiteRequest -> siteManagementUseCase
                        .createSite(createSiteRequest.getName(), Integer.valueOf(brandId)))
                .flatMap(site -> ServerResponse.ok().bodyValue(site));
    }

    public Mono<ServerResponse> updateSite(ServerRequest serverRequest) {
        String brandId = serverRequest.pathVariable("brandId");
        String siteId = serverRequest.pathVariable("siteId");
        return serverRequest.bodyToMono(UpdateSiteRequest.class)
                .map(UpdateSiteRequest::getName)
                .flatMap(name -> siteManagementUseCase.updateSite(
                        Integer.valueOf(brandId), Integer.valueOf(siteId), name))
                .flatMap(site -> ServerResponse.ok().bodyValue(site));
    }

}
