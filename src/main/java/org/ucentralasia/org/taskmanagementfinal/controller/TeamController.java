package org.ucentralasia.org.taskmanagementfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ucentralasia.org.taskmanagementfinal.domain.Team;
import org.ucentralasia.org.taskmanagementfinal.dto.TeamRequest;
import org.ucentralasia.org.taskmanagementfinal.service.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public Team createTeam(@RequestParam String username, @RequestBody TeamRequest request) {
        return teamService.createTeam(request, username);
    }

    @PostMapping("/{teamId}/members")
    public Team addMemberToTeam(@PathVariable Long teamId, @RequestParam String username) {
        return teamService.addUserToTeam(teamId, username);
    }

    @GetMapping("/{teamId}")
    public Team getTeam(@PathVariable Long teamId) {
        return teamService.getTeamById(teamId);
    }
}
