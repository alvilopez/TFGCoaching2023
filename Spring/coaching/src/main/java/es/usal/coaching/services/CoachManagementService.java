package es.usal.coaching.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface CoachManagementService {

    String uploadImage(MultipartFile imgFile);

    

}
