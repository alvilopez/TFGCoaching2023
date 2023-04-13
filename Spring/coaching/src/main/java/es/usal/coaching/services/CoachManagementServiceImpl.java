package es.usal.coaching.services;



import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;


import es.usal.coaching.repositories.CoachRepository;
import es.usal.coaching.repositories.TeamRepository;


@Service
public class CoachManagementServiceImpl implements CoachManagementService  {

    

    @Autowired
    CoachRepository coachRepository;


    @Autowired
    TeamRepository teamRepository;

    


    






}
