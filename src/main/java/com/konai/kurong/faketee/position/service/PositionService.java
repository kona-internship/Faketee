package com.konai.kurong.faketee.position.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.position.dto.PositionResponseDto;
import com.konai.kurong.faketee.position.dto.PositionSaveRequestDto;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    private final CorporationRepository corporationRepository;

    /**
     *
     *
     *
     * @param corId
     * @param requestDto
     */
    public void registerPosition(Long corId, PositionSaveRequestDto requestDto){

        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        Position position = Position.builder()
                .name(requestDto.getName())
                .corporation(corporation)
                .build();

        positionRepository.save(position);
    }

    public void removePosition(Long corId, Long posId){
        positionRepository.deleteById(posId);
    }

    public List<PositionResponseDto> getPosList(Long corId){
        return PositionResponseDto.convertToDtoList(positionRepository.findAllByCorporation_Id(corId));
    }


}
