package test.interview.loopme.campaign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import test.interview.loopme.campaign.controller.dto.ad.AdvertisementDTO;
import test.interview.loopme.campaign.controller.dto.ad.NewAdvertisementDTO;
import test.interview.loopme.campaign.service.AdvertisementService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static test.interview.loopme.campaign.controller.mapper.AdvertisementMapper.mapToDomain;
import static test.interview.loopme.campaign.controller.mapper.AdvertisementMapper.mapToDto;

@RestController
@RequestMapping("ad")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDTO> getAdvertisement(@PathVariable("id") @Positive int id) {
        return advertisementService.find(id)
                .map(ad -> new ResponseEntity<>(mapToDto(ad), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertisementDTO addAdvertisement(@Valid @RequestBody NewAdvertisementDTO advertisementDTO) {
        return mapToDto(advertisementService.add(mapToDomain(advertisementDTO)));
    }

    @PutMapping
    public ResponseEntity<AdvertisementDTO> updateAdvertisement(@Valid @RequestBody AdvertisementDTO advertisementDTO) {
        return advertisementService.update(mapToDomain(advertisementDTO))
                .map(ad -> new ResponseEntity<>(mapToDto(ad), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdvertisement(@PathVariable("id") @Positive int id) {
        advertisementService.delete(id);
    }

}
