package com.cybertek.controller;

import com.cybertek.annotation.DefaultExceptionMessage;
import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.ResponseWrapper;
import com.cybertek.enums.Status;
import com.cybertek.exception.TicketingProjectException;
import com.cybertek.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Projects Controller",description = "User API")
public class ProjectController {

    ProjectService projectService;
    UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @Operation(summary = "Read all projects")
    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    public ResponseEntity<ResponseWrapper> readAll(){
        List<ProjectDTO> projectDTOS = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are retrieved",projectDTOS));
    }

    @GetMapping("/{projectcode}")
    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @Operation(summary = "Read by project code")
    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    public ResponseEntity<ResponseWrapper> readByProjectCode(@PathVariable("projectcode") String projectcode){

        ProjectDTO projectDTOS = projectService.getByProjectCode(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Projects are retrieved",projectDTOS));
    }

    @PostMapping
    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @Operation(summary = "Create project")
    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    public ResponseEntity<ResponseWrapper> create(@RequestBody ProjectDTO projectDTO) throws TicketingProjectException {

        ProjectDTO createdProject = projectService.save(projectDTO);

        return ResponseEntity.ok(new ResponseWrapper("Projects are retrieved",projectDTO));
    }

    @PutMapping
    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @Operation(summary = "Update project")
    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO) throws TicketingProjectException {

        ProjectDTO updatedProject = projectService.update(projectDTO);

        return ResponseEntity.ok(new ResponseWrapper("Project is updated",updatedProject));
    }

    @DeleteMapping("/{projectcode}")
    @DefaultExceptionMessage(defaultMessage = "Failed to delete project!")
    @Operation(summary = "Delete project")
    @PreAuthorize("hasAnyAuthority('Admin','Manager')")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectcode") String projectcode) throws TicketingProjectException {

        projectService.delete(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Projects is deleted",projectcode));
    }

    @DeleteMapping("/complete/{projectcode}")
    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @Operation(summary = "Complete project")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<ResponseWrapper> completeProject(@PathVariable("projectcode") String projectcode) throws TicketingProjectException {

        ProjectDTO projectDTO = projectService.complete(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Projects is complete",projectcode));
    }

    @GetMapping("/details")
    @DefaultExceptionMessage(defaultMessage = "Something went wrong, try again!")
    @Operation(summary = "Read all project details")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<ResponseWrapper> readAllProjectDetails(@PathVariable("projectcode") String projectcode) throws TicketingProjectException {

        List<ProjectDTO> projectDTOs = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("Projects are retrieved with details",projectDTOs));
    }



}
