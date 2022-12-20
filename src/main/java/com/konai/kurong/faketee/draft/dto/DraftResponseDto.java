package com.konai.kurong.faketee.draft.dto;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.utils.DraftRequestType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
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

    private List<ReqResponseDto> reqList;

    public static DraftResponseDto convertToDto(Draft draft){
        DraftResponseDto.DraftResponseDtoBuilder builder =  DraftResponseDto.builder()
                .id(draft.getId())
                .requestEmployee(draft.getRequestEmployee().getName())
                .approvalDate(draft.getApprovalDate())
                .requestDate(draft.getRequestDate())
                .approvalMessage(draft.getApprovalMessage())
                .stateCode(draft.getStateCode().getStateMessage())
                .requestMessage(draft.getRequestMessage())
                .requestType(draft.getRequestType().getType())
                .crudType(draft.getCrudType().getType());

        if(draft.getRequestType().equals(DraftRequestType.ATTENDANCE)){
//            return builder.dateList(draft.getAtd()).build();
        } else if (draft.getRequestType().equals(DraftRequestType.VACATION)) {
            return builder.reqList(ReqResponseDto.convertToDtoList(draft.getVacDateRequestList())).build();
        }
        return null;
    }

    public static List<DraftResponseDto> convertToDtoList(List<Draft> draftList) {
        Stream<Draft> stream = draftList.stream();
        return stream.map((draft) -> convertToDto(draft)).collect(Collectors.toList());
    }
}
