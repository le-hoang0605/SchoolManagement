package org.schoolmanagement.schoolmanagement.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.AssessmentType;

@Converter(autoApply = true)
public class AssessmentTypeConverter implements AttributeConverter<AssessmentType, String> {

    @Override
    public String convertToDatabaseColumn(AssessmentType attribute) {
        if (attribute == null) return null;
        if (attribute == AssessmentType.final_exam) return "final";
        return attribute.name();
    }

    @Override
    public AssessmentType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        if ("final".equalsIgnoreCase(dbData)) return AssessmentType.final_exam;
        return AssessmentType.valueOf(dbData);
    }
}