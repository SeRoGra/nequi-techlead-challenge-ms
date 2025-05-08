package co.com.nequi.techlead.challenge.model.site;
import co.com.nequi.techlead.challenge.model.brand.Brand;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Site {

    private Integer id;
    private String name;
    private Brand brand;

}
