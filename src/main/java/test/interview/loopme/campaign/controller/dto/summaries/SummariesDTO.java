package test.interview.loopme.campaign.controller.dto.summaries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import test.interview.loopme.campaign.controller.dto.AbstractObject;
import test.interview.loopme.campaign.controller.dto.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SummariesDTO extends AbstractObject {
    private static final long serialVersionUID = -4951069464041686451L;

    private Integer id;
    private String name;
    private Status status;
    private int adsCount;
}
