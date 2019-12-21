package test.interview.loopme.campaign.dao;

import test.interview.loopme.campaign.dao.model.Campaign;

/**
 * Provides CRUD operations for {@link Campaign}
 */
public interface CampaignRepository {

    /**
     * Search on {@link Campaign} by its id
     *
     * @param id - {@link Campaign}'s id
     * @return {@link Campaign} and null if there is no any
     */
    Campaign find(int id);

    /**
     * Saves new {@link Campaign} representation
     *
     * @param campaign - {@link Campaign} to be saved id is not required and will be ignored
     * @return - saved {@link Campaign} contains id represented in the database
     */
    Campaign saveAndGet(Campaign campaign);

    /**
     * Saves new {@link Campaign} representation
     *
     * @param campaign - {@link Campaign} to be updated id is required and defines the row to be updated
     * @return - updated {@link Campaign} contains id represented in the database is not changed
     */
    Campaign updateAndGet(Campaign campaign);

    /**
     * Deletes {@link Campaign} by its id
     *
     * @param id - {@link Campaign}'s id
     */
    void delete(int id);
}
