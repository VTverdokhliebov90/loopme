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
import test.interview.loopme.campaign.controller.dto.campaign.CampaignDTO;
import test.interview.loopme.campaign.controller.dto.campaign.NewCampaignDTO;
import test.interview.loopme.campaign.service.CampaignService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static test.interview.loopme.campaign.controller.mapper.CampaignMapper.mapToDomain;
import static test.interview.loopme.campaign.controller.mapper.CampaignMapper.mapToDto;

@RestController
@RequestMapping("campaign")
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDTO> getCampaign(@PathVariable("id") @Positive int id) {
        return campaignService.find(id)
                .map(campaign -> new ResponseEntity<>(mapToDto(campaign), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CampaignDTO> addCampaign(@Valid @RequestBody NewCampaignDTO campaignDTO) {
        return campaignService.add(mapToDomain(campaignDTO))
                .map(campaign -> new ResponseEntity<>(mapToDto(campaign), HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PutMapping
    public ResponseEntity<CampaignDTO> updateCampaign(@Valid @RequestBody CampaignDTO campaignDTO) {
        return campaignService.update(mapToDomain(campaignDTO))
                .map(campaign -> new ResponseEntity<>(mapToDto(campaign), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable("id") @Positive int id) {
        campaignService.delete(id);
    }

}
