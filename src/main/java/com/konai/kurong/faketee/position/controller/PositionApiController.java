package com.konai.kurong.faketee.position.controller;


import com.konai.kurong.faketee.position.dto.PositionSaveRequestDto;
import com.konai.kurong.faketee.position.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/pos")
public class PositionApiController {

    private final PositionService positionService;

    /**
     * 직무 등록
     *
     * @param corId
     * @param requestDto
     * @return
     */
    @PostMapping()
    public ResponseEntity<?> registerPosition(@PathVariable(name = "corId") Long corId,
                                              @Valid @RequestBody PositionSaveRequestDto requestDto){

        positionService.registerPosition(corId, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 직무 수정
     *
     * @param posId
     * @param requestDto
     * @return
     */
    @PostMapping("/update/{posId}")
    public ResponseEntity<?> updatePosition(@PathVariable(name = "posId") Long posId,
                                              @Valid @RequestBody PositionSaveRequestDto requestDto){

        positionService.updatePosition(posId, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 직무 목록 불러오기
     *
     * @param corId
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<?> getPosList(@PathVariable(name = "corId") Long corId){

        return new ResponseEntity<>(positionService.getPosList(corId), HttpStatus.OK);
    }

    /**
     * 직무 삭제
     *
     * @param corId
     * @param posId
     * @return
     */
    @PostMapping("/delete/{posId}")
    public ResponseEntity<?> removePosition(@PathVariable(name = "corId") Long corId,
                                        @PathVariable(name = "posId") Long posId){

        positionService.removePosition(corId, posId);
        return new ResponseEntity<>(positionService.getPosList(corId), HttpStatus.OK);
    }

}
