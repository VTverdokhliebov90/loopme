package test.interview.loopme.campaign.controller.dto.ad;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import test.interview.loopme.campaign.controller.dto.AdPlatform;
import test.interview.loopme.campaign.controller.dto.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdvertisementDTO extends NewAdvertisementDTO {
    private static final long serialVersionUID = 4785544020900704497L;

    @NotNull(message = "Please provide target id")
    private Integer id;
    @NotNull(message = "Please provide status")
    private Status status;

    public AdvertisementDTO(Integer id, String name, Status status, String assetUrl, List<AdPlatform> platforms) {
        super(name, assetUrl, platforms);
        this.id = id;
        this.status = status;
    }
}
