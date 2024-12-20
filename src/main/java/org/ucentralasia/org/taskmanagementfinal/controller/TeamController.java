package org.ucentralasia.org.taskmanagementfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.ucentralasia.org.taskmanagementfinal.domain.Team;
import org.ucentralasia.org.taskmanagementfinal.dto.TeamRequest;
import org.ucentralasia.org.taskmanagementfinal.service.TeamService;
import org.ucentralasia.org.taskmanagementfinal.service.UserPrincipal;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> createTeam(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                           @RequestBody TeamRequest request) {
        Team team = teamService.createTeam(request, userPrincipal.getUsername());
        return ResponseEntity.ok(team);
    }

    @PostMapping("/{teamId}/members")
    public ResponseEntity<Team> addMemberToTeam(@PathVariable Long teamId,
                                                @RequestParam String username,
                                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        // Optionally, check if the authenticated user has permissions to add members
        Team team = teamService.addUserToTeam(teamId, username);
        return ResponseEntity.ok(team);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeam(@PathVariable Long teamId,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        // Optionally, check if the authenticated user is a member of the team
        Team team = teamService.getTeamById(teamId);
        return ResponseEntity.ok(team);
    }
}
