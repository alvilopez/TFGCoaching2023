package es.usal.coaching.services;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.MatchDTO;
import es.usal.coaching.email.EmailService;
import es.usal.coaching.entities.Action;
import es.usal.coaching.entities.Match;
import es.usal.coaching.entities.Player;
import es.usal.coaching.entities.Team;
import es.usal.coaching.mappers.ActionDTOToEntityMapper;
import es.usal.coaching.mappers.ActionEntityToDTOMapper;
import es.usal.coaching.mappers.MatchDTOToEntityMapper;
import es.usal.coaching.mappers.MatchEntityToDTOMapper;
import es.usal.coaching.mappers.TeamDTOToEntityMapper;
import es.usal.coaching.repositories.ActionRepository;
import es.usal.coaching.repositories.CoachRepository;
import es.usal.coaching.repositories.MatchRepository;
import es.usal.coaching.repositories.PlayerRepository;
import es.usal.coaching.repositories.TeamRepository;
import es.usal.coaching.security.entity.Coach;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;



@Service
public class MatchManagementServiceImpl implements MatchManagementService {

    private final Path root = Paths.get("resources/videos");

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    CoachRepository coachRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    FilesStorageService storageService;

    @Autowired
    TeamManagementService teamManagementService;

    @Autowired
    EmailService emailService;
    
    @Autowired
    TeamRepository teamRepository;
    

    @Override
    public Collection<MatchDTO> getMatchs(String userCod) {

        Collection<MatchDTO> response = new ArrayList<MatchDTO>();
        Collection<Match> usersMatches = new ArrayList<Match>();

        try {
            Coach coach = coachRepository.findByNameUsuario(userCod);
            usersMatches = matchRepository.findByLocalTeam(coach.getTeam());
        } catch (Exception e) {

        }

        for (Match m : usersMatches) {
            response.add(MatchEntityToDTOMapper.parser(m));
        }

        return response;
    }

    @Override
    public MatchDTO addMatch(MatchDTO request, String userCod) {

        MatchDTO response = new MatchDTO();
        Match matchToSave = new Match();

        try {
            matchToSave.setCod("MATCH_" + StringUtils.right( UUID.randomUUID().toString(), 10));
            matchToSave.setMatchNum(request.getMatchNum());
            matchToSave.setVideo(request.getVideo());
            matchToSave.setDate(request.getDate());
            Coach coach = coachRepository.findByNameUsuario(userCod);
            matchToSave.setLocalTeam(coach.getTeam());
            request.getVisitantTeam().setCod("VT_" + StringUtils.right( UUID.randomUUID().toString(), 10));
            Team visitantTeam = TeamDTOToEntityMapper.parser(teamManagementService.addTeam(request.getVisitantTeam(), userCod));
            matchToSave.setVisitantTeam(visitantTeam);
            matchToSave.setActions(new ArrayList<Action>());

            response = MatchEntityToDTOMapper.parser(matchRepository.save(matchToSave));

        } catch (Exception e) {
            return null;
        }
        return response;
    }

    @Override
    public MatchDTO updateMatch(MatchDTO request) {
        MatchDTO response = new MatchDTO();
        try {
            Match toUpdate = MatchDTOToEntityMapper.parser(request);
            toUpdate.setId(request.getId());
            response = MatchEntityToDTOMapper.parser(matchRepository.save(toUpdate));
        } catch (Exception e) {
            return null;
        }
        return response;
    }

    @Override
    public MatchDTO deleteMatch(Long request) {

        try {
            Optional<Match> toDelete = matchRepository.findById(request);
            matchRepository.delete(toDelete.get());
            return new MatchDTO();

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public ActionDTO addAction(ActionDTO request, Long id) {
        ActionDTO response = new ActionDTO();
        Action toSave = ActionDTOToEntityMapper.parser(request);
        try {
            Optional<Match> matchToSave = matchRepository.findById(id);
            toSave.setPlayer(playerRepository.findByDni(toSave.getPlayer().getDni()));
            toSave = actionRepository.save(toSave);
            
            matchToSave.get().getActions().add(toSave);
            matchRepository.save(matchToSave.get());
            response = ActionEntityToDTOMapper.parser(toSave);
        } catch (Exception e) {
            return null;
        }

        return response;
    }

    public Resource loadVideo(String video) {        
        return storageService.load(video);
    }

    @Override
    public String addMatchVideo(MultipartFile file, String username, Long id) {
        Path path = Path.of("/resources/videos/" + username);

        try {
            storageService.save(file, username,
                    "MATCH" + id.toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        } catch (Exception e) {
            return null;
        }

        return id.toString();
    }
        


    @Override
    public void splitVideo(String cod, String userName) {

        
        try {
            Match matchEntity = matchRepository.findByCod(cod);
                
            for(Action a : matchEntity.getActions()){
                File pathMpeg = new File("resources/ffmpeg.exe");
                File pathMprobe = new File("resources/ffmpeg.exe");
                FFmpeg ffmpeg = new FFmpeg(pathMpeg.getAbsolutePath());
                FFprobe ffprobe = new FFprobe(pathMprobe.getAbsolutePath());
                File path = new File("resources");
                

                Long t = a.getMin();
                Integer i = 1;
                String videos ="/videos";
                String username = "/" + userName;
                String match = "/MATCH_" + cod;

                File splitDirectory = new File(path.getAbsolutePath() + videos + username + "/split" + match + "/");

                if (!splitDirectory.exists()) {
                    splitDirectory.mkdirs();
                    System.out.println("Directory Created -> "+ splitDirectory.getAbsolutePath());
                

                FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(path.getAbsolutePath() + videos + username + match + ".mp4")
                .addExtraArgs("-ss", Long.toString(t-10))
                .addExtraArgs("-t", Integer.toString(20))
                .addOutput(splitDirectory + "/" + a.getType() + i.toString() + ".mp4")
                .done();

                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

                executor.createJob(builder).run();
                }
        }
            
        } catch (Exception e) {
            e.printStackTrace();
        }    
        
       
        
    
    }

    @Override
    public ActionDTO deleteAction(Long id) {
        Optional<Action> actionToDelete = actionRepository.findById(id);

        Match match = matchRepository.findByActions(actionToDelete);
        match.getActions().remove(actionToDelete.get());
        actionToDelete.get().setMatch(null);
        matchRepository.save(match);
        actionRepository.delete(actionToDelete.get());

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        StringBuilder img = new StringBuilder()
            .append(root)
            .append(File.separator)
            .append(username)
            .append(File.separator)
            .append(actionToDelete.get().getImgSrc());

        File archivo = new File(img.toString());
        
        // Verificar si el archivo existe
        if (archivo.exists()) {
            // Intentar eliminar el archivo
            if (archivo.delete()) {
                System.out.println("Archivo eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el archivo.");
            }
        } else {
            System.out.println("El archivo no existe en la ruta especificada.");
        }



        actionRepository.delete(actionToDelete.get());


        return ActionEntityToDTOMapper.parser(actionToDelete.get());
    }


    @Override
    public ActionDTO obtenerFotoDeVideo(Long id) {
        Optional<Action> photoAction = actionRepository.findById(id);
        Match match = matchRepository.findByActions(photoAction);
        
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();

        String userPath = "/videos/" + username;
        String videoFilePath = "/MATCH" + match.getId().toString() + ".mp4";
        String photoName = "IMG_"+ photoAction.get().getId() +"_MATCH_"+ match.getId().toString() + ".jpg";

        try{
                File pathMpeg = new File("resources/ffmpeg.exe");
                File pathMprobe = new File("resources/ffmpeg.exe");
                FFmpeg ffmpeg = new FFmpeg(pathMpeg.getAbsolutePath());
                FFprobe ffprobe = new FFprobe(pathMprobe.getAbsolutePath());
                File path = new File("resources/" + userPath);
                

                FFmpegBuilder builder = new FFmpegBuilder().setInput(path.getAbsolutePath() + videoFilePath)
                .addOutput(path.getAbsolutePath() + "/" + photoName)
                .setFrames(1)
                .setStartOffset(photoAction.get().getMin(), TimeUnit.SECONDS)
                .done();

                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

                executor.createJob(builder).run();

                photoAction.get().setImgSrc(photoName);
                actionRepository.save(photoAction.get());
                return ActionEntityToDTOMapper.parser(photoAction.get());    
                

        }catch(Exception e){
            e.printStackTrace();
        }

            return null;

    }

    @Override
    public MatchDTO notificarJugadores(MatchDTO match) {
        
        Optional<Match> matchEntity = matchRepository.findById(match.getId());

        emailService.sendMatchInfoToPlayer(matchEntity.get());

        return match;
    }

    @Override
    public Collection<MatchDTO> getMatchsForPlayer(String hash) {
        Collection<MatchDTO> response = new ArrayList<MatchDTO>();
        
        try {
            Player player = playerRepository.findByHashString(hash);
            Team team = teamRepository.findByPlayers(player);
            Collection<Match> partidos = matchRepository.findByLocalTeam(team);
            
            for(Match match : partidos){
                MatchDTO matchDTO = new MatchDTO();
                matchDTO.setDate(match.getDate());
                matchDTO.setMatchNum(match.getMatchNum());
                matchDTO.setVideo(match.getVideo());
                matchDTO.setActions(new ArrayList<>());
                for(Action a : match.getActions()){
                    if(a.getPlayer().getId() == player.getId()){
                        matchDTO.getActions().add(ActionEntityToDTOMapper.parser(a));
                    }
                }
                response.add(matchDTO);
            }
            
            
        } catch (Exception e) {
            return null;
        }

        return response;
    }

    
}
