package org.example.feedbackstudio.note.RestController;

import org.example.feedbackstudio.note.entity.HomeworkEntity;
import org.example.feedbackstudio.note.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homeworks")
@CrossOrigin(origins = "*")
public class HomeworkController {

    @Autowired
    private HomeworkRepository repository;

    @GetMapping
    public List<HomeworkEntity> getAllHomeworks() {
        return (List<HomeworkEntity>) repository.findAll();
    }

    @GetMapping("/{id}")
    public HomeworkEntity getHomeworkById(@PathVariable String id) {
        return repository.findById(Integer.valueOf(id)).orElse(null);
    }

    @PostMapping
    public HomeworkEntity createHomework(@RequestBody HomeworkEntity homework) {
        return repository.save(homework);
    }

    @PutMapping("/{id}")
    public HomeworkEntity updateHomework(@PathVariable String id, @RequestBody HomeworkEntity updatedHomework) {
        HomeworkEntity existingHomework = repository.findById(Integer.valueOf(id)).orElse(null);
        if (existingHomework != null) {
            existingHomework.setTitle(updatedHomework.getTitle());
            existingHomework.setDescription(updatedHomework.getDescription());
            return repository.save(existingHomework);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteHomework(@PathVariable String id) {
        repository.deleteById(Integer.valueOf(id));
    }
}
