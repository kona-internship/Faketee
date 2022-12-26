package com.konai.kurong.faketee.draft.dto;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.utils.DraftRequestType;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
@Slf4j
public class DraftResponseDto {
    private Long id;

    private String requestEmployee;

    private LocalDateTime approvalDate;

    private LocalDateTime requestDate;

    private String approvalMessage;

    private String stateCode;

    private String requestMessage;

    private String requestType;

    private String crudType;

    private List<ApvlResponseDto> apvlList;

    private List<ReqResponseDto> reqList;

    public static DraftResponseDto convertToDto(Draft draft){
        List<ApvlResponseDto> apvlResponseDtos = new ArrayList<>();
        DraftStateCode draftState = draft.getStateCode();
        switch (draftState){
            case WAIT:
                if(draft.getApprovalEmp1()!=null) {
                    apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmp1().getName()).apvlState("대기").build());
                }
                apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmpFin().getName()).apvlState("대기").build());
                break;
            case APVL_1:
                apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmp1().getName()).apvlState("승인").build());
                apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmpFin().getName()).apvlState("대기").build());
                break;
            case APVL_FIN:
                if(draft.getApprovalEmp1()!=null) {
                    apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmp1().getName()).apvlState("승인").build());
                }
                apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmpFin().getName()).apvlState("승인").build());
                break;
            case REJ_1:
                apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmp1().getName()).apvlState("거절").build());
                apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmpFin().getName()).apvlState("").build());
                break;
            case REJ_FIN:
                if(draft.getApprovalEmp1()!=null) {
                    apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmp1().getName()).apvlState("승인").build());
                }
                apvlResponseDtos.add(ApvlResponseDto.builder().apvlEmpName(draft.getApprovalEmpFin().getName()).apvlState("거절").build());
                break;
            default:
                break;
        }
        DraftResponseDto.DraftResponseDtoBuilder builder =  DraftResponseDto.builder()
                .id(draft.getId())
                .requestEmployee(draft.getRequestEmployee().getName())
                .approvalDate(draft.getApprovalDate())
                .requestDate(draft.getRequestDate())
                .approvalMessage(draft.getApprovalMessage())
                .stateCode(draft.getStateCode().getStateMessage())
                .requestMessage(draft.getRequestMessage())
                .requestType(draft.getRequestType().getType())
                .crudType(draft.getCrudType().getType())
                .apvlList(apvlResponseDtos);
        if(draft.getRequestType().equals(DraftRequestType.ATTENDANCE)){log.info(">>>>>>flag 3");
            return builder.reqList(ReqResponseDto.convertToAtdDtoList(draft.getAtdRequestList())).build();
        } else if (draft.getRequestType().equals(DraftRequestType.VACATION)) {log.info(">>>>>>flag 4");
            return builder.reqList(ReqResponseDto.convertToVacDtoList(draft.getVacRequestList())).build();
        }
        return null;
    }

    public static List<DraftResponseDto> convertToDtoList(List<Draft> draftList) {
        Stream<Draft> stream = draftList.stream();
        return stream.map((draft) -> convertToDto(draft)).collect(Collectors.toList());
    }
}
