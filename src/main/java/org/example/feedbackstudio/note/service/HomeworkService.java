package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.note.Model.HomeworkModel;
import org.example.feedbackstudio.note.Model.HomeworkQueryModel;
import org.example.feedbackstudio.note.entity.HomeworkEntity;

import java.util.List;

public interface HomeworkService {
    public List<HomeworkModel> getAllHomework();
    public HomeworkModel getHomework(String id);
    public HomeworkModel createHomework(HomeworkQueryModel homework);
    public HomeworkModel updateHomework(HomeworkQueryModel homework);
    public void deleteHomework(String id);
    public List<HomeworkModel> getHomeworkByQueryModel(HomeworkQueryModel homeworkQueryModel);

}
