package es.usal.coaching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import es.usal.coaching.services.MatchManagementService;
import reactor.core.publisher.Mono;

@Service
public class StreamingService {

    private static final String FORMAT="/%s.mp4";

    @Autowired 
    MatchManagementService matchManagementService;

    public Mono<Resource> getVideo(String title, String userName ){
     return Mono.fromSupplier(()->matchManagementService.loadVideo(String.format(userName + FORMAT, title)))   ;
    }
}