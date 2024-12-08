package org.example.feedbackstudio.note.service;


import org.example.feedbackstudio.login.service.UserService;
import org.example.feedbackstudio.note.Model.PdfUploadQueryModel;
import org.example.feedbackstudio.note.entity.HomeworkEntity;
import org.example.feedbackstudio.note.entity.PdfInfoEntity;
import org.example.feedbackstudio.note.repository.PdfInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfInfoServiceImpl implements PdfInfoService {
    @Autowired
    private PdfInfoRepository pdfInfoRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private HomeworkService homeworkService;


    private final String UPLOAD_DIR = "src/main/resources/static/";

    @Override
    public void add(PdfUploadQueryModel queryModel) {
        PdfInfoEntity save = new PdfInfoEntity();
        save.setTitle(queryModel.getTitle());
        save.setContent(queryModel.getContent());
        save.setXsize(queryModel.getXsize());
        save.setYsize(queryModel.getYsize());
        save.setPageSize(queryModel.getPageSize());
        save.setHomeworkEntity(homeworkService.getHomeworkEntitiy(queryModel.getHomeworkId()));
        save.setUser(userService.getUserById(queryModel.getUserId()).get());
        pdfInfoRepository.save(save);

        save.setUrl(UPLOAD_DIR +save.getId());
        pdfInfoRepository.save(save);

    }

    @Override
    public PdfInfoEntity findById(String Id) {
        PdfInfoEntity geted = new PdfInfoEntity();
        geted=null;

        geted = pdfInfoRepository.findById(Id);
        if (geted!=null) {
            return geted;
        } else {
            throw new RuntimeException("Pdf not found with ID: " + Id);
        }
    }
}
