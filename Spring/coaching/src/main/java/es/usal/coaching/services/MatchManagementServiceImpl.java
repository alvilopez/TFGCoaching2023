package es.usal.coaching.services;


import java.io.File;


import java.nio.file.Path;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.usal.coaching.dtos.ActionDTO;
import es.usal.coaching.dtos.MatchDTO;
import es.usal.coaching.entities.Action;

import es.usal.coaching.entities.Match;
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
    TeamRepository teamRepository;

    @Override
    public List<MatchDTO> getMatchs(String userCod) {

        List<MatchDTO> response = new ArrayList<MatchDTO>();
        List<Match> usersMatches = new ArrayList<Match>();

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
            matchToSave.setCod("MATCH" + request.getVideo());
            matchToSave.setMatchNum(request.getMatchNum());
            matchToSave.setVideo(request.getVideo());
            matchToSave.setDate(request.getDate());
            Coach coach = coachRepository.findByNameUsuario(userCod);
            matchToSave.setLocalTeam(coach.getTeam());
            request.getVisitantTeam().setCod(request.getVideo().toString() + "visit");
            Team visitantTeam = teamRepository.save(TeamDTOToEntityMapper.parser(request.getVisitantTeam()));
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
            Long id = matchRepository.findIdByCod(request.getCod());
            Match toUpdate = MatchDTOToEntityMapper.parser(request);
            toUpdate.setId(id);
            response = MatchEntityToDTOMapper.parser(matchRepository.save(toUpdate));
        } catch (Exception e) {
            return null;
        }
        return response;
    }

    @Override
    public MatchDTO deleteMatch(String request) {

        try {
            Match toDelete = matchRepository.findByCod(request);

            List<Long> ids = new ArrayList<Long>();

            for (Action a : toDelete.getActions()) {
                ids.add(a.getId());
            }

            if (!ids.isEmpty()) {
                actionRepository.deleteAllById(ids);
            }

            matchRepository.delete(toDelete);
            return new MatchDTO();

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public ActionDTO addAction(ActionDTO request, String matchCod) {
        ActionDTO response = new ActionDTO();
        Action toSave = ActionDTOToEntityMapper.parser(request);
        try {
            toSave.setPlayer(playerRepository.findByDni(toSave.getPlayer().getDni()));
            toSave = actionRepository.save(toSave);
            Match matchToSave = matchRepository.findByCod(matchCod);
            matchToSave.getActions().add(toSave);
            matchRepository.save(matchToSave);
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
    public String addMatchVideo(MultipartFile file, String username) {
        Path path = Path.of("/resources/videos/" + username);

        Integer i = new Random().nextInt(10000000);
        while (new File(path + i.toString()).exists())
            i = new Random().nextInt(10000000);

        try {
            storageService.save(file, username,
                    "MATCH" + i.toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        } catch (Exception e) {
            return null;
        }

        return i.toString();

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
                

                Integer t = a.getMin();
                Integer i = 1;
                String videos ="/videos";
                String username = "/" + userName;
                String match = "/" + cod;

                File splitDirectory = new File(path.getAbsolutePath() + videos + username + "/split" + match + "/");

                if (!splitDirectory.exists()) {
                    splitDirectory.mkdirs();
                    System.out.println("Directory Created -> "+ splitDirectory.getAbsolutePath());
                

                FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(path.getAbsolutePath() + videos + username + match + ".mp4")
                .addExtraArgs("-ss", Integer.toString(t-10))
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
}
