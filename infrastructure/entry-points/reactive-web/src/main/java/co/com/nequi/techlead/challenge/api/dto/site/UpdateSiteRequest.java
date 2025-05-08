package co.com.nequi.techlead.challenge.api.dto.site;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSiteRequest {
    @NotBlank(message = "Site name is required")
    private String name;
}
