package org.example.feedbackstudio.homework.model.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeworkQueryDTO {
    private String title;
    private String description;
    private Long teacherId;
    private Long classroomId;
}
