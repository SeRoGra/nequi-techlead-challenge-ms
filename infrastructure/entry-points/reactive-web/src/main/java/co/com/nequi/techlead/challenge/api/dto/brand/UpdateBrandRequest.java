package co.com.nequi.techlead.challenge.api.dto.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBrandRequest {
    @NotBlank(message = "Brand name is required")
    private String name;
}
