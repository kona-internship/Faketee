package com.konai.kurong.faketee.location.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.location.dto.LocationResponseDto;
import com.konai.kurong.faketee.location.dto.LocationSaveRequestDto;
import com.konai.kurong.faketee.location.entity.Location;
import com.konai.kurong.faketee.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    private final CorporationRepository corporationRepository;
    private final LocationRepository locationRepository;

    public void registerLocation(Long corId, LocationSaveRequestDto requestDto) {
        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        Location newLoc = requestDto.toEntity(corporation);
        locationRepository.save(newLoc);
    }

    public List<LocationResponseDto> getLocList(Long corId){
        return LocationResponseDto.converToDtoList(locationRepository.findLocationByCorporationId(corId));
    }
}
