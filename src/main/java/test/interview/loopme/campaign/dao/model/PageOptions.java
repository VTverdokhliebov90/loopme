package test.interview.loopme.campaign.dao.model;

import lombok.Value;

@Value
public class PageOptions {

    private final Integer limit;
    private final Integer offset;
}
