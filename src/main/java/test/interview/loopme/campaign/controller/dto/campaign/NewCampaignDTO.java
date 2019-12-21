package test.interview.loopme.campaign.controller.dto.campaign;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import test.interview.loopme.campaign.controller.dto.AbstractObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewCampaignDTO extends AbstractObject {
    private static final long serialVersionUID = -6461720355580517770L;

    @NotBlank(message = "Please provide a name")
    private String name;
    private long startDate;
    private long endDate;
    @NotEmpty(message = "Should be specified at least one ad id")
    @UniqueElements(message = "Platforms list must contain only uniques")
    private List<Integer> advertisements;

    public NewCampaignDTO(String name, long startDate, long endDate, List<Integer> advertisements) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.advertisements = advertisements;
    }
}
