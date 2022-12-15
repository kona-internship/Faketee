package com.konai.kurong.faketee.draft.utils;

import javax.persistence.AttributeConverter;

public class DraftCrudTypeAttributeConverter implements AttributeConverter<DraftCrudType, String> {
    @Override
    public String convertToDatabaseColumn(DraftCrudType attribute) {
        if(DraftCrudType.CREATE.equals(attribute)){
            return "C";
        } else if (DraftCrudType.UPDATE.equals(attribute)) {
            return "U";
        } else if (DraftCrudType.DELETE.equals(attribute)) {
            return "D";
        }
        return null;
    }

    @Override
    public DraftCrudType convertToEntityAttribute(String dbData) {
        if(dbData.equals("C")){
            return DraftCrudType.CREATE;
        } else if (dbData.equals("U")) {
            return DraftCrudType.UPDATE;
        } else if (dbData.equals("D")) {
            return DraftCrudType.DELETE;
        }
        return null;
    }
}
