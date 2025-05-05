package co.com.nequi.techlead.challenge.model.site;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Site {

    private Integer id;
    private String name;
    private Integer brandId;

}
