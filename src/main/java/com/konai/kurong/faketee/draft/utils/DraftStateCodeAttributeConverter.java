package com.konai.kurong.faketee.draft.utils;

import javax.persistence.AttributeConverter;

public class DraftStateCodeAttributeConverter implements AttributeConverter<DraftStateCode, String> {
    @Override
    public String convertToDatabaseColumn(DraftStateCode attribute) {
        if(DraftStateCode.WAIT.equals(attribute)){
            return "10";
        } else if (DraftStateCode.APVL_1.equals(attribute)) {
            return "20";
        } else if (DraftStateCode.APVL_FIN.equals(attribute)) {
            return "21";
        } else if (DraftStateCode.REJ_1.equals(attribute)) {
            return "30";
        } else if (DraftStateCode.REJ_FIN.equals(attribute)) {
            return "31";
        }
        return "00";
    }

    @Override
    public DraftStateCode convertToEntityAttribute(String dbData) {
        if(dbData.equals("10")){
            return DraftStateCode.WAIT;
        } else if (dbData.equals("20")) {
            return DraftStateCode.APVL_1;
        } else if (dbData.equals("21")) {
            return DraftStateCode.APVL_FIN;
        } else if (dbData.equals("30")) {
            return DraftStateCode.REJ_1;
        } else if (dbData.equals("31")) {
            return DraftStateCode.REJ_FIN;
        }
        return null;
    }
}
