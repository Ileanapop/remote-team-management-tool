package com.example.teamuptool;

import com.example.teamuptool.dto.AdministratorDTO;
import com.example.teamuptool.model.Administrator;
import com.example.teamuptool.model.Project;
import com.example.teamuptool.model.ProjectStatus;
import com.example.teamuptool.model.Technology;
import com.example.teamuptool.repository.AdministratorRepository;
import com.example.teamuptool.repository.ProjectRepository;
import com.example.teamuptool.repository.ProjectStatusRepository;
import com.example.teamuptool.repository.TechnologyRepository;
import com.example.teamuptool.service.AdministratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AdministratorServiceTest {

    @InjectMocks
    private AdministratorService administratorService;

    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private ProjectStatusRepository projectStatusRepository;

    @Mock
    private ProjectRepository projectRepository;


    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loginAdministratorCorrectCredentialsShouldReturnAdministrator() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");

        Mockito.when(administratorRepository.findByEmail(Mockito.anyString())).thenReturn(java.util.Optional.of(new Administrator(encodedPassword,"email" )));

        AdministratorDTO administratorDTO = administratorService.loginAdministrator("email","password");

        Assertions.assertEquals("email", administratorDTO.getEmail());
        Assertions.assertEquals(encodedPassword, administratorDTO.getPassword());


    }

    @Test
    public void loginAdministratorIncorrectCredentialsShouldReturnNull() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");
        Mockito.when(administratorRepository.findByEmail(Mockito.anyString())).thenReturn(java.util.Optional.of(new Administrator(encodedPassword, "email")));

        String email = "email";
        String password = "wrong";

        AdministratorDTO administratorDTO = administratorService.loginAdministrator(email,password);
        Assertions.assertNull(administratorDTO);


    }

    @Test
    public void loginAdministratorWrongEmailShouldReturnNull() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");
        Mockito.when(administratorRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        String email = "wrong";
        String password = "wrong";

        AdministratorDTO administratorDTO = administratorService.loginAdministrator(email,password);
        Assertions.assertNull(administratorDTO);


    }

    @Test
    public void getTechnologiesShouldReturnListTechnologies(){

        List<Technology> technologies = new ArrayList<>();
        Technology technology1 = new Technology();
        technology1.setName("Technology1");

        Technology technology2 = new Technology();
        technology2.setName("Technology2");

        technologies.add(technology1);
        technologies.add(technology2);

        Mockito.when(technologyRepository.findAll()).thenReturn(technologies);

        List<String> allTechnologies = administratorService.getTechnologies();

        Assertions.assertEquals(technologies.size(),allTechnologies.size());
        Assertions.assertEquals("Technology1",allTechnologies.get(0));
        Assertions.assertEquals("Technology2",allTechnologies.get(1));
    }

    @Test
    public void getActiveProjectsShouldReturnListActiveProjects(){

        List<Project> projects = new ArrayList<>();
        Project project1 = new Project();
        project1.setName("Project1");

        Project project2 = new Project();
        project2.setName("Project2");

        projects.add(project1);
        projects.add(project2);

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setName("ACTIVE");
        Mockito.when(projectStatusRepository.getByName(Mockito.anyString())).thenReturn(Optional.of(projectStatus));
        Mockito.when(projectRepository.getAllByProjectStatus(Mockito.any())).thenReturn(projects);

        List<String> activeProjects = administratorService.getActiveProjects();

        Assertions.assertEquals(projects.size(),activeProjects.size());
        Assertions.assertEquals("Project1",activeProjects.get(0));
        Assertions.assertEquals("Project2",activeProjects.get(1));
    }



}
