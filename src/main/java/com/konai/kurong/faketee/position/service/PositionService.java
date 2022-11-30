package com.konai.kurong.faketee.position.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.position.dto.PositionResponseDto;
import com.konai.kurong.faketee.position.dto.PositionSaveRequestDto;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.position.repository.PositionQueryRepo;
import com.konai.kurong.faketee.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionQueryRepo positionQueryRepo;
    private final CorporationRepository corporationRepository;

    /**
     * 회사에 직무를 등록한다.
     * 회사 아이디를 받아 디비에서 회사를 찾아 Position의 corparation에
     * requestDto의 name을 Position의 name 프로퍼티에 넣어준다.
     *
     * @param corId 직무를 추가할 회사 아이디
     * @param requestDto 직무에 들어가는 프로퍼티 값들을 가지고 있다 (프로퍼티 키: name)
     */
    public void registerPosition(Long corId, PositionSaveRequestDto requestDto){

        //추가사항: 등록하려는 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        Position position = Position.builder()
                .name(requestDto.getName())
                .corporation(corporation)
                .build();

        positionRepository.save(position);
    }
    @Transactional
    public void updatePosition(Long posId, PositionSaveRequestDto requestDto){
        positionQueryRepo.updatePosName(posId, requestDto.getName());
    }

    /**
     * 직무를 지운다.
     * 지우려는 직무와 연관된 직원과 템플릿이 가지고 있는 직무를 null로 바꿔준다.
     *
     * @param corId 요청이 온 회사 아이디
     * @param posId 지우려는 직무 아이디
     */
    public void removePosition(Long corId, Long posId){

        //추가사항: 직무와 연관된 직원과 템플릿이 가지고 있는 직무를 null로 바꿔준다. 지우려는 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        positionRepository.deleteById(posId);
    }

    /**
     * 회사가 가지고 있는 직무 목록을 불러온다.
     *
     * @param corId 목록을 가져올 회사의 아이디
     * @return Postion에서 PositionResponseDto로 변환된 리스트를 반환
     */
    public List<PositionResponseDto> getPosList(Long corId){

        //추가사항: 지우려는 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        return PositionResponseDto.convertToDtoList(positionRepository.findAllByCorporation_Id(corId));
    }


}
