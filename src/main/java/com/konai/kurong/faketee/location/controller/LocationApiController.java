package com.konai.kurong.faketee.location.controller;

import com.konai.kurong.faketee.location.dto.LocationSaveRequestDto;
import com.konai.kurong.faketee.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/corporation/{corId}/loc")
public class LocationApiController {

    private final LocationService locationService;

    /**
     * 출퇴근 장소 등록
     * 장소 등록하면서 직원도 넣어준다.
     *
     * @param corId
     * @param requestDto
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> registerLocation(@PathVariable(name = "corId") Long corId,
                                              @Valid @RequestBody LocationSaveRequestDto requestDto) {
        locationService.registerLocation(corId, requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 출퇴근 장소 목록 불러오기
     */
    @PostMapping("/list")
    public ResponseEntity<?> getPosList(@PathVariable(name = "corId") Long corId){

        return new ResponseEntity<>(locationService.getLocList(corId), HttpStatus.OK);

    }
    /**
     * 출퇴근 장소 삭제
     */
}
