package org.example.feedbackstudio.homework.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeworkQueryModel {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;;
    private Long classroomId;;
}
