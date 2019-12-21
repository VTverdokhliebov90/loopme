package test.interview.loopme.campaign.dao;

import test.interview.loopme.campaign.dao.model.Advertisement;

/**
 * Provides CRUD operations for {@link Advertisement}
 */
// TODO: dao package could be moved to separate module
public interface AdvertisementRepository {

    /**
     * Search on {@link Advertisement} by its id
     *
     * @param id - {@link Advertisement}'s id
     * @return {@link Advertisement} and null if there is no any
     */
    Advertisement find(int id);

    /**
     * Saves new {@link Advertisement} representation
     *
     * @param advertisement - {@link Advertisement} to be saved id is not required and will be ignored
     * @return - saved {@link Advertisement} contains id represented in the database
     */
    Advertisement saveAndGet(Advertisement advertisement);

    /**
     * Saves new {@link Advertisement} representation
     *
     * @param advertisement - {@link Advertisement} to be updated id is required and defines the row to be updated
     * @return - updated {@link Advertisement} contains id represented in the database is not changed
     */
    Advertisement updateAndGet(Advertisement advertisement);

    /**
     * Deletes {@link Advertisement} by its id
     *
     * @param id - {@link Advertisement}'s id
     */
    void delete(int id);

}
