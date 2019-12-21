package test.interview.loopme.campaign.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
public class Campaign {

    private Integer id;
    private final String name;
    private int status;
    private final Date startDate;
    private final Date endDate;
    private final List<Integer> advertisements;

    public Campaign(String name, Date startDate, Date endDate, List<Integer> advertisements) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.advertisements = advertisements;
    }
}
