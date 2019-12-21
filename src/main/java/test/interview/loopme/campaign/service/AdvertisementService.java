package test.interview.loopme.campaign.service;

import test.interview.loopme.campaign.dao.model.Advertisement;

import java.util.Optional;

/**
 * The AdvertisementService. Business logic level
 */
public interface AdvertisementService {

    Optional<Advertisement> find(int id);

    Advertisement add(Advertisement advertisement);

    Optional<Advertisement> update(Advertisement advertisement);

    void delete(int id);
}
