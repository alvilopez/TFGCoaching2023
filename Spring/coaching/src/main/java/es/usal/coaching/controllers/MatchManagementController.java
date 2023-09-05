package es.usal.coaching.controllers;



import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<byte[]> splitVideo(@PathVariable String match) throws IOException{
        

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
        String username = userDetails.getUsername(); 

        match = matchManagementService.splitVideo(match, username);

        // Creating byteArray stream, make it bufferable and passing this buffer to ZipOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        File path = new File("resources");
        File splitDirectoryFolder = new File(path.getAbsolutePath() + "/videos/" + username + "/split" + match + "/");
        
        comprimirCarpeta(splitDirectoryFolder, "", zipOutputStream);

        zipOutputStream.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "carpeta.zip");

        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), headers, HttpStatus.OK);

    }
    
    private void comprimirCarpeta(File carpeta, String rutaRelativa, ZipOutputStream zipOutputStream) throws IOException {
        File[] archivos = carpeta.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    comprimirCarpeta(archivo, rutaRelativa + archivo.getName() + "/", zipOutputStream);
                } else {
                    FileInputStream fileInputStream = new FileInputStream(archivo);
                    ZipEntry zipEntry = new ZipEntry(rutaRelativa + archivo.getName());
                    zipOutputStream.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fileInputStream.read(bytes)) >= 0) {
                        zipOutputStream.write(bytes, 0, length);
                    }

                    fileInputStream.close();
                }
            }
        }
    }

    @GetMapping(value="/match/video/photo/{id}")
    public ResponseEntity<ActionDTO> postMethodName(@PathVariable Long id) throws IOException {
       
        
        ActionDTO response = matchManagementService.obtenerFotoDeVideo(id);

        return new ResponseEntity<ActionDTO>(response, HttpStatus.OK);
        
    }

    @PostMapping(value = "/match/email")
    public ResponseEntity<MatchDTO> enviarEmailConDatosJugadores(@RequestBody MatchDTO match){

        MatchDTO response = matchManagementService.notificarJugadores(match);

        return new ResponseEntity<MatchDTO>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/match/player/{hash}")
    public ResponseEntity<Collection<MatchDTO>> getMatchsForPlayer(@PathVariable String hash) {

        Collection<MatchDTO> response = matchManagementService.getMatchsForPlayer(hash);
        if(response !=null){
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }

    
}