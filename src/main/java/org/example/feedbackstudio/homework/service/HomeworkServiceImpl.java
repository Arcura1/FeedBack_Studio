package org.example.feedbackstudio.homework.service;

import org.example.feedbackstudio.login.user.service.UserService;
import org.example.feedbackstudio.homework.model.HomeworkModel;
import org.example.feedbackstudio.homework.model.HomeworkQueryModel;
import org.example.feedbackstudio.homework.entitiy.HomeworkEntity;
import org.example.feedbackstudio.homework.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
    public List<HomeworkModel> getHomeworkByTeacher(Long teacher) {
        List<HomeworkEntity> entities = homeworkRepository.findByTeacherId(teacher); // findByTeacherId artık List döndürüyor
        List<HomeworkModel> models = new ArrayList<>();

        for (HomeworkEntity entity : entities) {
            models.add(HomeworkConverter.convertToModel(entity));
        }

        return models;
    }



    @Override
    public HomeworkModel getHomework(Long id) {
        HomeworkEntity homeworkEntityOptional = null;
        homeworkEntityOptional = homeworkRepository.findById(id).get(); // findById kullanımı
        if (homeworkEntityOptional!=null) {
            HomeworkEntity homeworkEntity = homeworkEntityOptional;
            return HomeworkConverter.convertToModel(homeworkEntity);
        } else {
            throw new RuntimeException("Homework not found with ID: " + id);
        }
    }

    @Override
    public HomeworkEntity getHomeworkEntitiy(Long id) {
        HomeworkEntity homeworkEntityOptional = null;
        homeworkEntityOptional = homeworkRepository.findById(id).get(); // findById kullanımı
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
        HomeworkEntity.setTeacherId(homework.getTeacherId());
        HomeworkEntity.setTitle(homework.getTitle());
        HomeworkEntity.setDescription(homework.getDescription());
        HomeworkEntity.setClassroomId(homework.getClassroomId());

        homeworkRepository.save(HomeworkEntity);
        File folder = new File("src/main/resources/static/"+HomeworkEntity.getId());

        // Klasör oluştur
        if (!folder.exists()) {
            boolean created = folder.mkdir(); // mkdir() tek bir klasör oluşturur
            if (created) {
                HomeworkModel result =HomeworkConverter.convertToModel(HomeworkEntity);
                return result;
            }
        }
        HomeworkModel result =HomeworkConverter.convertToModel(HomeworkEntity);
        return result;
    }

    @Override
    public HomeworkModel updateHomework(HomeworkQueryModel homework) {
        return null;
    }

    @Override
    public void deleteHomework(Long id) {
        HomeworkEntity homeworkEntityOptional=null;
        homeworkEntityOptional= homeworkRepository.findById(id).get();

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
