package com.konai.kurong.faketee.position.controller;


import com.konai.kurong.faketee.position.dto.PositionSaveRequestDto;
import com.konai.kurong.faketee.position.service.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pos")
public class PositionApiController {

    private final PositionService positionService;

    @PostMapping("/{corId}")
    public ResponseEntity<?> registerPosition(@PathVariable(name = "corId") Long corId,
                                              @Valid @RequestBody PositionSaveRequestDto requestDto){

        positionService.registerPosition(corId, requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/list/{corId}")
    public ResponseEntity<?> getPosList(@PathVariable(name = "corId") Long corId){

        return new ResponseEntity<>(positionService.getPosList(corId), HttpStatus.OK);
    }

    @GetMapping("/{corId}/delete/{posId}")
    public ResponseEntity<?> getPosList(@PathVariable(name = "corId") Long corId,
                                        @PathVariable(name = "posId") Long posId){

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
