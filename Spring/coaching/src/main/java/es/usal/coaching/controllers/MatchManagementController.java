package es.usal.coaching.controllers;



import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.mime.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.core.joran.action.Action;
import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.MatchDTO;
import es.usal.coaching.services.MatchManagementService;
import reactor.core.publisher.Mono;

@RestController
public class MatchManagementController {

    @Autowired
    MatchManagementService matchManagementService;

    @Autowired
    StreamingService streamingService;

    
    @GetMapping(value="/match")
    public ResponseEntity<Collection<MatchDTO>> getMatchs() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername();    
        Collection<MatchDTO> response = matchManagementService.getMatchs(username);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value="/match")
    public ResponseEntity<MatchDTO> addMatch(@RequestBody MatchDTO request){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername(); 

        MatchDTO response = matchManagementService.addMatch(request, username); 

        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value="/match/video/{idMatch}")
    public ResponseEntity<String> addMatchVideo( @RequestParam("file") MultipartFile file,@PathVariable Long idMatch){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername(); 
        String response = matchManagementService.addMatchVideo(file, username, idMatch);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }


    
    @GetMapping(value = "/video/{userName}/{title}", produces = "video/mp4")
    public Mono<Resource> getVideos(@PathVariable String title, @PathVariable String userName, @RequestHeader("Range") String range) {        
        System.out.println("range in bytes() : " + range);
        title = "MATCH" + title;
        Mono<Resource> response = streamingService.getVideo(title, userName);
        return response;
    }

    @PutMapping(value="/match")
    public ResponseEntity<MatchDTO> updateMatch(@RequestBody MatchDTO request){
        MatchDTO response = matchManagementService.updateMatch(request);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping(value="/match/{id}")
    public ResponseEntity<MatchDTO> deleteMatch(@PathVariable Long id){
        MatchDTO response = matchManagementService.deleteMatch(id);

        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(value="/match/action/{id}")
    public ResponseEntity<ActionDTO> addAction(@RequestBody ActionDTO request, @PathVariable Long id){
        ActionDTO response = matchManagementService.addAction(request, id); 
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping(value="/match/action/{id}")
    public ResponseEntity<ActionDTO> deleteAction(@PathVariable Long id){
        ActionDTO response = matchManagementService.deleteAction(id); 
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }


    @GetMapping(value = "/match/video/split/{match}")
    public byte[] splitVideo(HttpServletRequest request,
    HttpServletResponse response,@PathVariable String match) throws IOException{
        

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername(); 

        matchManagementService.splitVideo(match, username);
        response.setContentType("application/zip");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        // Creating byteArray stream, make it bufferable and passing this buffer to ZipOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        // Simple file Collection, just for tests
        ArrayList<File> files = new ArrayList<>(2);
        File file1 = new File("resources/videos/" + username + "/split/" + match+ "/");
        files.add(new File(file1.getAbsolutePath()));

        // Packing files
        for (File file : files) {
            // New zip entry and copying InputStream with file to ZipOutputStream, after all closing streams
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file1);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();

    }
    
    @GetMapping(value="/match/video/photo/{id}")
    public ResponseEntity<ActionDTO> postMethodName(@PathVariable Long id) throws IOException {
       
        
        ActionDTO response = matchManagementService.obtenerFotoDeVideo(id);

        return new ResponseEntity<ActionDTO>(response, HttpStatus.OK);
        
    }
    
}