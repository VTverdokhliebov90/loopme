package test.interview.loopme.campaign.controller.dto.campaign;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import test.interview.loopme.campaign.controller.dto.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CampaignDTO extends NewCampaignDTO {
    private static final long serialVersionUID = 1625422742307602999L;

    @NotNull(message = "Please provide target id")
    private Integer id;
    @NotNull(message = "Please provide status")
    private Status status;

    public CampaignDTO(Integer id, Status status, String name, long startDate, long endDate, List<Integer> advertisements) {
        super(name, startDate, endDate, advertisements);
        this.id = id;
        this.status = status;
    }
}
