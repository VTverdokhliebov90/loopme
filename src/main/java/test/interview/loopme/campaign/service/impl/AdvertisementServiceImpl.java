package test.interview.loopme.campaign.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.interview.loopme.campaign.dao.AdvertisementRepository;
import test.interview.loopme.campaign.dao.model.Advertisement;
import test.interview.loopme.campaign.service.AdvertisementService;

import java.util.Optional;

@Component
@Transactional
class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public Optional<Advertisement> find(int id) {
        return Optional.ofNullable(advertisementRepository.find(id));
    }

    @Override
    public Advertisement add(Advertisement advertisement) {
        return advertisementRepository.saveAndGet(advertisement);
    }

    @Override
    public Optional<Advertisement> update(Advertisement advertisement) {
        return Optional.ofNullable(advertisementRepository.updateAndGet(advertisement));
    }

    @Override
    public void delete(int id) {
        advertisementRepository.delete(id);
    }
}
