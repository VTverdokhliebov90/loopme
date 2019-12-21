package test.interview.loopme.campaign.controller.mapper;

import test.interview.loopme.campaign.controller.dto.AdPlatform;
import test.interview.loopme.campaign.controller.dto.Status;
import test.interview.loopme.campaign.controller.dto.ad.AdvertisementDTO;
import test.interview.loopme.campaign.controller.dto.ad.NewAdvertisementDTO;
import test.interview.loopme.campaign.dao.model.Advertisement;

import java.util.Objects;
import java.util.stream.Collectors;

public final class AdvertisementMapper {

    public static Advertisement mapToDomain(NewAdvertisementDTO advertisement) {
        return new Advertisement(
                advertisement.getName(),
                advertisement.getPlatforms().stream().map(AdvertisementMapper::mapToDomain).collect(Collectors.toSet()),
                advertisement.getAssetUrl());
    }

    public static Advertisement mapToDomain(AdvertisementDTO advertisement) {
        return new Advertisement(
                advertisement.getId(),
                advertisement.getName(),
                advertisement.getStatus().getCode(),
                advertisement.getPlatforms().stream().map(AdvertisementMapper::mapToDomain).collect(Collectors.toSet()),
                advertisement.getAssetUrl());
    }

    public static AdvertisementDTO mapToDto(Advertisement advertisement) {
        return new AdvertisementDTO(
                advertisement.getId(),
                advertisement.getName(),
                Status.forCode(advertisement.getStatus()),
                advertisement.getAssetUrl(),
                advertisement.getPlatforms().stream()
                        .filter(Objects::nonNull)
                        .map(AdvertisementMapper::mapToDto)
                        .collect(Collectors.toList()));
    }

    private static AdPlatform mapToDto(int code) {
        return AdPlatform.forCode(code);
    }

    private static int mapToDomain(AdPlatform adPlatform) {
        return adPlatform.getCode();
    }

}
