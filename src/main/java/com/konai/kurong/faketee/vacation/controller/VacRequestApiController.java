package com.konai.kurong.faketee.vacation.controller;


import com.konai.kurong.faketee.vacation.service.VacRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/vac/req")
@RestController
public class VacRequestApiController {

    private final VacRequestService vacRequestService;


}
