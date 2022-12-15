package com.konai.kurong.faketee.draft.utils;

import javax.persistence.AttributeConverter;

public class DraftRequestTypeAttributeConverter implements AttributeConverter<DraftRequestType, String> {
    @Override
    public String convertToDatabaseColumn(DraftRequestType attribute) {
        if(DraftRequestType.ATTENDANCE.equals(attribute)){
            return "A";
        } else if (DraftRequestType.VACATION.equals(attribute)) {
            return "V";
        }
        return null;
    }

    @Override
    public DraftRequestType convertToEntityAttribute(String dbData) {
        if(dbData.equals("A")){
            return DraftRequestType.ATTENDANCE;
        } else if (dbData.equals("V")) {
            return DraftRequestType.VACATION;
        }
        return null;
    }
}
