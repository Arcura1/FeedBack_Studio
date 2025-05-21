package org.example.feedbackstudio.note.pdfInfo.service;


import org.example.feedbackstudio.login.user.service.UserService;
import org.example.feedbackstudio.note.Model.MixQueryModel;
import org.example.feedbackstudio.note.Model.PdfUploadQueryModel;
import org.example.feedbackstudio.note.pdfInfo.entitiy.PdfInfoEntity;
import org.example.feedbackstudio.note.pdfInfo.repository.PdfInfoRepository;
import org.example.feedbackstudio.homework.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Long add(PdfUploadQueryModel queryModel) {
        PdfInfoEntity save = new PdfInfoEntity();
        save.setTitle(queryModel.getTitle());
        save.setContent(queryModel.getContent());
        save.setXSize(queryModel.getXsize());
        save.setYSize(queryModel.getYsize());
        save.setPageSize(queryModel.getPageSize());
        save.setHomeworkEntityId(queryModel.getHomeworkId());
        save.setHomeworkEntity(homeworkService.getHomeworkEntitiy(queryModel.getHomeworkId()));
        save.setUserId((queryModel.getUserId()));
        pdfInfoRepository.save(save);

        return save.getId();
    }

    @Override
    public PdfInfoEntity findById(Long Id) {
        PdfInfoEntity geted = new PdfInfoEntity();
        geted=null;

        geted = pdfInfoRepository.findById(Id).get();
        if (geted!=null) {
            return geted;
        } else {
            throw new RuntimeException("Pdf not found with ID: " + Id);
        }
    }

    @Override
    public PdfInfoEntity findAllByHU(MixQueryModel queryModel) {
        PdfInfoEntity result = new PdfInfoEntity();
        pdfInfoRepository.findByhomeworkEntity_id(queryModel.getHomeworkId());
        result =pdfInfoRepository.findByHomeworkEntityIdAndUserId(queryModel.getHomeworkId(), queryModel.getUserId());

        return result;
    }

    @Override
    public List<PdfInfoEntity> findByHomevork(Long homevork) {
        return pdfInfoRepository.findByhomeworkEntity_id(homevork);
    }


    public MixQueryModel convertToMixQueryModel(PdfInfoEntity pdfInfoEntity) {
        MixQueryModel mixQueryModel = new MixQueryModel();

        if (pdfInfoEntity != null) {
            if (pdfInfoEntity.getHomeworkEntity() != null) {
                mixQueryModel.setHomeworkId(pdfInfoEntity.getHomeworkEntity().getId());
            }
            if (pdfInfoEntity.getUser() != null) {
                mixQueryModel.setUserId(pdfInfoEntity.getUser().getId());
            }
        }

        return mixQueryModel;
    }


}
