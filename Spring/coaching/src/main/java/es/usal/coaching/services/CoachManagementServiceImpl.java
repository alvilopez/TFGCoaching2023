package es.usal.coaching.services;



import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.repositories.CoachRepository;
import es.usal.coaching.repositories.TeamRepository;
import es.usal.coaching.security.entity.Coach;


@Service
public class CoachManagementServiceImpl implements CoachManagementService  {

    

    @Autowired
    CoachRepository coachRepository;


    @Autowired
    TeamRepository teamRepository;

    @Autowired
    FilesStorageService storageService;


    @Override
    public String uploadImage(MultipartFile imgFile) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();
        String fileName = "IMG_" + username + "." + FilenameUtils.getExtension(imgFile.getOriginalFilename());
        try {
            Coach coach = coachRepository.findByNameUsuario(username);
            coach.setImgSrc(fileName);
            storageService.save(imgFile, username, fileName);
            coachRepository.save(coach);
        } catch (Exception e) {         
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'uploadImage'");
        }
        return fileName;
    }

    


    






}
