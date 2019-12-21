package test.interview.loopme.campaign.controller.dto.ad;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import test.interview.loopme.campaign.controller.dto.AbstractObject;
import test.interview.loopme.campaign.controller.dto.AdPlatform;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewAdvertisementDTO extends AbstractObject {
    private static final long serialVersionUID = 4785544020900704497L;

    @NotBlank(message = "Please provide a name")
    private String name;
    @NotBlank(message = "Please provide a assetUrl")
    private String assetUrl;
    @NotEmpty(message = "Should be specified at least one platform")
    @UniqueElements(message = "Platforms list must contain only uniques")
    private List<AdPlatform> platforms;

    public NewAdvertisementDTO(String name, String assetUrl, List<AdPlatform> platforms) {
        this.name = name;
        this.assetUrl = assetUrl;
        this.platforms = platforms;
    }
}
