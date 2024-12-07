package org.example.feedbackstudio.note.Model;

import lombok.Getter;
import lombok.Setter;
import org.example.feedbackstudio.login.entity.User;

@Getter
@Setter
public class HomeworkQueryModel {
    private String title;
    private String description;
    private String teacherId;;
}
