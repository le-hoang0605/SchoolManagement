package org.schoolmanagement.schoolmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.schoolmanagement.schoolmanagement.entity.enumEntity.LessonPreparationStatus;

@Getter
@Setter
@NoArgsConstructor
public class LessonPreparationReviewRequestDTO {
    @NotNull(message = "Status is required")
    private LessonPreparationStatus status;

    private String adminNotes;
}
