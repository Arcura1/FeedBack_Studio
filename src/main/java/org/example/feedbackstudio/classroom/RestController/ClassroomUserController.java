package org.example.feedbackstudio.classroom.RestController;

import org.example.feedbackstudio.classroom.entitiy.ClassroomUserEntity;
import org.example.feedbackstudio.classroom.model.ClassroomUserQueyModel;
import org.example.feedbackstudio.classroom.service.ClassroomUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classroom-users")
@CrossOrigin(origins = "*")
public class ClassroomUserController {

    private final ClassroomUserService classroomUserService;

    public ClassroomUserController(ClassroomUserService classroomUserService) {
        this.classroomUserService = classroomUserService;
    }

    // 📌 Tüm sınıf kullanıcılarını getir
    @GetMapping
    public ResponseEntity<List<ClassroomUserEntity>> getAllClassroomUsers() {
        return ResponseEntity.ok(classroomUserService.getAllClassroomUsers());
    }

    // 📌 Belirtilen ID'ye sahip sınıf kullanıcısını getir
    @GetMapping("/{id}")
    public ResponseEntity<ClassroomUserEntity> getClassroomUserById(@PathVariable Long id) {
        Optional<ClassroomUserEntity> classroomUser = classroomUserService.getClassroomUserById(id);
        return classroomUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // 📌 Belirtilen ID'ye sahip sınıf kullanıcısını getir
    @GetMapping("/byUser/{id}")
    public ResponseEntity<List<ClassroomUserEntity>>getClassroomUserByUserId(@PathVariable Long id) {
        Optional<List<ClassroomUserEntity>> classroomUser = classroomUserService.getClassroomUsersByUserId(id);
        return classroomUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 📌 Yeni bir sınıf kullanıcısı ekle
    @PostMapping
    public ResponseEntity<ClassroomUserEntity> createClassroomUser(@RequestBody ClassroomUserQueyModel model) {
        ClassroomUserEntity savedUser = classroomUserService.saveClassroomUser(model);
        return ResponseEntity.ok(savedUser);
    }

    // 📌 Belirtilen ID'ye sahip sınıf kullanıcısını sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroomUser(@PathVariable Long id) {
        classroomUserService.deleteClassroomUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomUserEntity> updateClassroomUser(
            @PathVariable Long id,
            @RequestBody ClassroomUserQueyModel model) {
        ClassroomUserEntity updatedEntity = classroomUserService.updateClassroomUser(id, model);
        return ResponseEntity.ok(updatedEntity);
    }
}
