package com.konai.kurong.faketee.corporation.controller;

import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.corporation.dto.CorporationSaveRequestDto;
import com.konai.kurong.faketee.corporation.service.CorporationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CorporationApiController {
    private final CorporationService corporationService;

    /**
     * 회사 등록
     *
     * @param requestDto
     * @return
     */

    @PostMapping("/api/corporation")
    public int registerCorporation(@Valid @RequestBody CorporationSaveRequestDto requestDto,
                                   @LoginUser SessionUser user) {

        Long id = corporationService.registerCorporation(requestDto, user.getId(), user.getName(), user.getEmail());

        return Math.toIntExact(id);
    }

}
