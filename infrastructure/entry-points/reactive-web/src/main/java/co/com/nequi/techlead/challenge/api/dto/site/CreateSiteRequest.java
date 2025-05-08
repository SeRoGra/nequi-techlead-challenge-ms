package co.com.nequi.techlead.challenge.api.dto.site;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSiteRequest {
    @NotBlank(message = "Site name is required")
    private String name;
}
