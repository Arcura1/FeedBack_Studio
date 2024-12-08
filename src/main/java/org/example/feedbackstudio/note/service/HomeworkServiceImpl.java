package org.example.feedbackstudio.note.service;

import org.example.feedbackstudio.login.entity.User;
import org.example.feedbackstudio.login.service.UserService;
import org.example.feedbackstudio.note.Model.HomeworkModel;
import org.example.feedbackstudio.note.Model.HomeworkQueryModel;
import org.example.feedbackstudio.note.entity.HomeworkEntity;
import org.example.feedbackstudio.note.entity.NoteEntity;
import org.example.feedbackstudio.note.repository.HomeworkRepository;
import org.example.feedbackstudio.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private UserService userService;


    @Autowired
    private HomeworkRepository homeworkRepository;

    @Override
    public List<HomeworkModel> getAllHomework() {
        Iterable<HomeworkEntity> entities = homeworkRepository.findAll();
        List<HomeworkModel> models = new ArrayList<>();
        for (HomeworkEntity entity : entities) {
            models.add(HomeworkConverter.convertToModel(entity));
        }
        return models;
    }

    @Override
    public HomeworkModel getHomework(String id) {
        HomeworkEntity homeworkEntityOptional = null;
        homeworkEntityOptional = homeworkRepository.findById(id); // findById kullanımı
        if (homeworkEntityOptional!=null) {
            HomeworkEntity homeworkEntity = homeworkEntityOptional;
            return HomeworkConverter.convertToModel(homeworkEntity);
        } else {
            throw new RuntimeException("Homework not found with ID: " + id);
        }
    }

    @Override
    public HomeworkEntity getHomeworkEntitiy(String id) {
        HomeworkEntity homeworkEntityOptional = null;
        homeworkEntityOptional = homeworkRepository.findById(id); // findById kullanımı
        if (homeworkEntityOptional!=null) {
            HomeworkEntity homeworkEntity = homeworkEntityOptional;
            return homeworkEntity;
        } else {
            throw new RuntimeException("Homework not found with ID: " + id);
        }
    }

    @Override
    public HomeworkModel createHomework(HomeworkQueryModel homework) {
        HomeworkEntity HomeworkEntity = new HomeworkEntity();
        User teacher = userService.getUserById(homework.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + homework.getTeacherId()));
        HomeworkEntity.setTeacher(teacher);
        HomeworkEntity.setTitle(homework.getTitle());
        HomeworkEntity.setDescription(homework.getDescription());


        homeworkRepository.save(HomeworkEntity);
        HomeworkModel result =HomeworkConverter.convertToModel(HomeworkEntity);
        return result;
    }

    @Override
    public HomeworkModel updateHomework(HomeworkQueryModel homework) {
        return null;
    }

    @Override
    public void deleteHomework(String id) {
        HomeworkEntity homeworkEntityOptional=null;
        homeworkEntityOptional= homeworkRepository.findById(id);

        if (homeworkEntityOptional != null) {
            // Öğeyi bulduysanız, silme işlemini yapın
            homeworkRepository.delete(homeworkEntityOptional);
        } else {
            // Eğer öğe bulunmazsa, uygun bir hata fırlatın
            throw new RuntimeException("Homework not found with ID: " + id);
        }
    }

    @Override
    public List<HomeworkModel> getHomeworkByQueryModel(HomeworkQueryModel homeworkQueryModel) {
        return List.of();
    }


    class HomeworkConverter {

        public static HomeworkModel convertToModel(HomeworkEntity entity) {
            HomeworkModel model = new HomeworkModel();
            model.setId(entity.getId());
            model.setTitle(entity.getTitle());
            model.setDescription(entity.getDescription());
            model.setTeacher(entity.getTeacher());
            return model;
        }

        public static HomeworkEntity convertToEntity(HomeworkModel model) {
            HomeworkEntity entity = new HomeworkEntity();
            entity.setId(model.getId());
            entity.setTitle(model.getTitle());
            entity.setDescription(model.getDescription());
            entity.setTeacher(model.getTeacher());
            return entity;
        }
    }

}
