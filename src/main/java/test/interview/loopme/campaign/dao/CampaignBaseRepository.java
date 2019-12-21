package test.interview.loopme.campaign.dao;

import test.interview.loopme.campaign.dao.model.Campaign;

/**
 * Provides base database operations for {@link Campaign}
 */
public interface CampaignBaseRepository {

    /**
     * Saves {@link Campaign}
     *
     * @param campaign is an object to be saved
     * @return generated id of the saved entity
     */
    int save(Campaign campaign);

    /**
     * Saves {@link Campaign}
     *
     * @param campaign is an object to be updated
     * @return number of effected rows
     */
    int update(Campaign campaign);

    /**
     * Saves {@link Campaign}
     *
     * @param id is an of object to be removed
     */
    void delete(int id);
}
