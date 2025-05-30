package org.example.feedbackstudio.homework.service;

import org.example.feedbackstudio.classroom.entitiy.ClassroomUserEntity;
import org.example.feedbackstudio.homework.model.query.HomeworkQueryDTO;
import org.example.feedbackstudio.homework.service.Specification.HomeworkSpecification;
import org.example.feedbackstudio.login.authority.authorityenum.AuthorityType;
import org.example.feedbackstudio.login.authority.authorityenum.EffectTypeEnum;
import org.example.feedbackstudio.login.authority.entity.Authority;
import org.example.feedbackstudio.login.authority.repository.AuthorityRepository;
import org.example.feedbackstudio.login.authority.service.AuthorityService;
import org.example.feedbackstudio.login.role.repository.RoleAuthorityRepository;
import org.example.feedbackstudio.login.user.service.UserService;
import org.example.feedbackstudio.homework.model.HomeworkModel;
import org.example.feedbackstudio.homework.model.HomeworkQueryModel;
import org.example.feedbackstudio.homework.entitiy.HomeworkEntity;
import org.example.feedbackstudio.homework.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.example.feedbackstudio.classroom.service.ClassroomUserService;
import org.example.feedbackstudio.classroom.entitiy.ClassroomUserEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private ClassroomUserService classroomUserService;

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
    public List<HomeworkModel> getHomeworksByUser(Long userId) {
        Optional<List<ClassroomUserEntity>> classroomUsersOpt = classroomUserService.getClassroomUsersByUserId(userId);

        if (classroomUsersOpt.isEmpty()) return List.of();

        List<Long> classroomIds = classroomUsersOpt.get().stream()
                .map(ClassroomUserEntity::getClassroomId)
                .distinct()
                .toList();

        List<HomeworkEntity> homeworkEntities = homeworkRepository.findByClassroomIdIn(classroomIds);

        return homeworkEntities.stream()
                .map(HomeworkConverter::convertToModel)
                .toList();
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

        for (EffectTypeEnum roleType : EffectTypeEnum.values()) {
            Authority temp=new Authority();
            temp.setHomework(HomeworkEntity);
            temp.setAuthorityType(AuthorityType.HOMEWORK);
            temp.setHomeworkId(HomeworkEntity.getId());
            temp.setDescription("description");
            temp.setName(HomeworkEntity.getTitle().toLowerCase()+" "+roleType.toString());
            temp.setEffectTypeEnum(roleType);
            authorityService.saveAuthority(temp);
        }
        HomeworkModel result =HomeworkConverter.convertToModel(HomeworkEntity);
        return result;
    }

    @Override
    public HomeworkModel updateHomework(HomeworkQueryModel homework) {
        if (homework.getId() == null) {
            throw new IllegalArgumentException("Güncellenecek ödevin ID’si boş olamaz.");
        }

        HomeworkEntity existingHomework = homeworkRepository.findById(homework.getId())
                .orElseThrow(() -> new RuntimeException("Ödev bulunamadı: " + homework.getId()));

        // Sadece güncellenebilir alanları değiştir
        existingHomework.setTitle(homework.getTitle());
        existingHomework.setDescription(homework.getDescription());

        // Kaydet
        HomeworkEntity updatedEntity = homeworkRepository.save(existingHomework);

        // Modele dönüştür ve döndür
        return HomeworkConverter.convertToModel(updatedEntity);
    }


    @Override
    public void deleteHomework(Long id) {


        List<Authority> authorities = authorityRepository.findAllByHomeworkId(id);
        if (!authorities.isEmpty()) {
            List<Long> authorityIds = authorities.stream()
                    .map(Authority::getId)
                    .collect(Collectors.toList());

            // Tüm role-authority ilişkilerini sil
            roleAuthorityRepository.deleteAllByAuthorityIdIn(authorityIds);

            // Authority’leri sil
            authorityRepository.deleteAll(authorities);
        }
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

    @Override
    public List<HomeworkEntity> searchHomeworks(HomeworkQueryDTO query) {
            // Specification ile sorguyu oluşturuyoruz
            return homeworkRepository.findAll(HomeworkSpecification.filter(query));
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
