package test.interview.loopme.campaign.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {

    private Integer id;
    private String name;
    private int status;
    private Set<Integer> platforms;
    private String assetUrl;

    public Advertisement(String name, Set<Integer> platforms, String assetUrl) {
        this.name = name;
        this.platforms = platforms;
        this.assetUrl = assetUrl;
    }
}
