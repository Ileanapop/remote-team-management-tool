package com.example.teamuptool;


import com.example.teamuptool.model.*;
import com.example.teamuptool.repository.*;
import com.example.teamuptool.service.AdministratorService;
import com.example.teamuptool.service.ManagerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private ProjectManagerRepository projectManagerRepository;

    @Mock
    private RoleTypesRepository roleTypesRepository;


    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getAllRoleTypesShouldReturnListOfRoleTypes(){

        List<RoleType> roleTypes = new ArrayList<>();

        RoleType roleType1 = new RoleType();
        roleType1.setName("type1");

        RoleType roleType2 = new RoleType();
        roleType2.setName("type2");

        roleTypes.add(roleType1);
        roleTypes.add(roleType2);

        Mockito.when(roleTypesRepository.getAllByNameIsNotIn(Mockito.anyList())).thenReturn(roleTypes);

        List<String> roleTypesReturned = managerService.getAllRoleTypes();

        Assertions.assertEquals(roleTypes.size(),roleTypesReturned.size());
        Assertions.assertEquals("type1",roleTypesReturned.get(0));
        Assertions.assertEquals("type2",roleTypesReturned.get(1));


    }

    @Test
    public void getProjectOfManagerShouldReturnProject(){

        ProjectManager projectManager = new ProjectManager();

        ProjectRole projectRole = new ProjectRole();
        Project project = new Project();
        project.setName("project");

        projectRole.setCompanyProject(project);
        projectManager.setProjectRole(projectRole);

        Mockito.when(projectManagerRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.of(projectManager));

        String returnedProjectName = managerService.getProject("email");

        Assertions.assertEquals("project",returnedProjectName);

    }

    @Test
    public void getProjectOfManagerNotHavingAProjectShouldReturnStringNoProjectAssigned(){

        ProjectManager projectManager = new ProjectManager();


        Mockito.when(projectManagerRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.of(projectManager));

        String returnedProjectName = managerService.getProject("email");

        Assertions.assertEquals("No project assigned",returnedProjectName);

    }

    @Test
    public void getProjectOfNonExistingManagerShouldReturnStringNoUserFound(){


        Mockito.when(projectManagerRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.empty());

        String returnedProjectName = managerService.getProject("email");

        Assertions.assertEquals("User not found",returnedProjectName);

    }
}
