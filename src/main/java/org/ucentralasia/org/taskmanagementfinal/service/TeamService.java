package org.ucentralasia.org.taskmanagementfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucentralasia.org.taskmanagementfinal.domain.Team;
import org.ucentralasia.org.taskmanagementfinal.domain.User;
import org.ucentralasia.org.taskmanagementfinal.dto.TeamRequest;
import org.ucentralasia.org.taskmanagementfinal.exception.ResourceNotFoundException;
import org.ucentralasia.org.taskmanagementfinal.repository.TeamRepository;
import org.ucentralasia.org.taskmanagementfinal.repository.UserRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Team createTeam(TeamRequest request, String username) {
        User owner = userService.findByUsername(username);
        Team team = new Team();
        team.setName(request.getName());
        team.getMembers().add(owner);
        return teamRepository.save(team);
    }

    public Team addUserToTeam(Long teamId, String username) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User user = userService.findByUsername(username);
        team.getMembers().add(user);
        return teamRepository.save(team);
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
    }
}
