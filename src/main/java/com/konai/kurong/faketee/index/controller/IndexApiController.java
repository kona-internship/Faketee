package com.konai.kurong.faketee.index.controller;


import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.corporation.service.CorporationService;
import com.konai.kurong.faketee.index.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/index")
@RequiredArgsConstructor
@RestController
public class IndexApiController {

    private final IndexService indexService;

    @GetMapping("/cor-list")
    public ResponseEntity<?> findCorList(@LoginUser SessionUser sessionUser){

        return ResponseEntity.ok(indexService.loadCorLists(sessionUser.getEmployeeList()));
    }
}
