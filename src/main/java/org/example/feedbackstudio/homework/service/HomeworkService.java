package org.example.feedbackstudio.homework.service;

import org.example.feedbackstudio.homework.model.HomeworkModel;
import org.example.feedbackstudio.homework.model.HomeworkQueryModel;
import org.example.feedbackstudio.homework.entitiy.HomeworkEntity;
import org.example.feedbackstudio.homework.model.query.HomeworkQueryDTO;

import java.util.List;

public interface HomeworkService {
    public List<HomeworkModel> getAllHomework();
    public List<HomeworkModel> getHomeworkByTeacher(Long teacher);
    public List<HomeworkModel> getHomeworksByUser(Long userId);
    public HomeworkModel getHomework(Long id);
    public HomeworkEntity getHomeworkEntitiy(Long id);
    public HomeworkModel createHomework(HomeworkQueryModel homework);
    public HomeworkModel updateHomework(HomeworkQueryModel homework);
    public void deleteHomework(Long id);
    public List<HomeworkModel> getHomeworkByQueryModel(HomeworkQueryModel homeworkQueryModel);
    public List<HomeworkEntity> searchHomeworks(HomeworkQueryDTO query);

}
