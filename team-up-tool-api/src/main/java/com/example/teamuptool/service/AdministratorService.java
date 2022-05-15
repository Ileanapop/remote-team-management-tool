package com.example.teamuptool.service;

import com.example.teamuptool.dto.AdministratorDTO;
import com.example.teamuptool.dto.EmployeeDTO;
import com.example.teamuptool.dto.ProjectDTO;
import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.model.*;
import com.example.teamuptool.repository.*;
import com.example.teamuptool.utils.AdministratorMapper;
import com.example.teamuptool.utils.ResponseService;
import com.example.teamuptool.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private ProjectStatusRepository projectStatusRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleTypesRepository roleTypesRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final static Logger LOGGER = Logger.getLogger(AdministratorService.class.getName());

    public AdministratorService() {
    }

    /**
     * Method for authenticating the administrator
     *
     * @param email email of the admin who wants to login
     * @param password password of the admin
     * @return the administrator if the credentials sent were correct else returns null
     */
    public AdministratorDTO loginAdministrator(String email, String password) {

        LOGGER.info("Verifying username");
        Optional<Administrator> administrator = administratorRepository.findByEmail(email);

        if (administrator.isEmpty()) {
            return null;
        }

        LOGGER.info("Verifying password");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(password, administrator.get().getPassword())) {
            LOGGER.info("Password correct");
            AdministratorMapper administratorMapper = new AdministratorMapper();
            return administratorMapper.convertToDTO(administrator.get());
        }
        LOGGER.warning("Incorrect credentials");
        return null;
    }

    /**
     * Method for saving a new user to the database
     * @param userDTO data for new user
     * @return the new user if the provided data was successful otherwise return null
     */
    public UserDTO saveUser(UserDTO userDTO){

        LOGGER.info("Verifying duplicated email");
        Optional<RegularUser> existingUser = userRepository.findByAccount_Email(userDTO.getEmail());
        if(existingUser.isPresent())
            return null;

        LOGGER.info("Check user type");
        RegularUser regularUser;
        Integer type = userDTO.getType();
        if(type == 1){
            regularUser = new Employee();
        }
        else {
            if (type == 2) {
                regularUser = new ProjectManager();
            }
            else
                regularUser = new Customer();
        }
        LOGGER.info("Verifying credentials");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Account newAccount = new Account(bCryptPasswordEncoder.encode(userDTO.getPassword()),userDTO.getEmail());
        regularUser.setAccount(newAccount);
        regularUser.setName(userDTO.getName());
        regularUser.setPhone(userDTO.getPhone());

        UserMapper userMapper = new UserMapper();

        LOGGER.info("Save new user");
        accountRepository.save(newAccount);
        LOGGER.info("Map response");
        return userMapper.convertToDto(userRepository.save(regularUser));
    }


    /**
     * Method for getting the list of technologies
     * @return list of names of available technologies
     */
    public List<String> getTechnologies(){
        LOGGER.info("Get list of available technologies");
        List<Technology> existingTechnologies = technologyRepository.findAll();

        List<String> technologies = new ArrayList<>();

        for(Technology technology: existingTechnologies){
            technologies.add(technology.getName());
        }
        return technologies;
    }

    /**
     * Method for getting the active projects
     * @return list of names of active projects
     */
    public List<String> getActiveProjects(){

        LOGGER.info("Get active status from repository");
        Optional<ProjectStatus> activeStatus = projectStatusRepository.getByName("ACTIVE");

        LOGGER.info("Construct list of active projects");
        if(activeStatus.isPresent()){
            List<Project> activeProjects = projectRepository.getAllByProjectStatus(activeStatus.get());

            List<String> projectNames = new ArrayList<>();

            for(Project project: activeProjects){
                projectNames.add(project.getName());
            }
            return projectNames;
        }
        LOGGER.info("Active status not found");
        return null;
    }

    /**
     * Method for getting the managers in the company
     * @return list of emails of managers
     */
    public List<String> getManagers(){

        LOGGER.info("Get list of managers");
        List<ProjectManager> managers = projectManagerRepository.findAll();

        List<String> projectManagersEmails = new ArrayList<>();

        for(ProjectManager projectManager: managers)
            projectManagersEmails.add(projectManager.getAccount().getEmail());
        return  projectManagersEmails;
    }

    /**
     * Method for getting the employees in the company
     * @return list of emails of employees
     */
    public List<String> getEmployees(){

        LOGGER.info("Get list of employees");
        List<Employee> employees = employeeRepository.findAll();

        List<String> employeesEmail = new ArrayList<>();

        LOGGER.info("Construct list of employees");
        for(Employee employee: employees)
            employeesEmail.add(employee.getAccount().getEmail());

        return  employeesEmail;
    }


    /**
     * Method for updating the employee
     * @param employeeDTO updated data for the employee
     * @return true is the update was successful, otherwise return false
     */
    public boolean updateEmployee(EmployeeDTO employeeDTO){

        LOGGER.info("Check employee exists");
        Optional<Employee> employee = employeeRepository.findByAccount_Email(employeeDTO.getEmail());
        LOGGER.info("Verify manager employee exists");
        Optional<ProjectManager> manager = projectManagerRepository.findByAccount_Email(employeeDTO.getManager());
        LOGGER.info("Verify technology");
        Optional<Technology> technology = technologyRepository.findByName(employeeDTO.getTechnology());


        LOGGER.info("Update employee fields");
        if(employee.isPresent() && manager.isPresent() && technology.isPresent()){
            employee.get().setProjectManager(manager.get());
            employee.get().setTechnology(technology.get());
            employeeRepository.save(employee.get());
            return true;
        }

        return false;
    }


    public ProjectDTO getManagerProjectData(String email){

        Optional<ProjectManager> projectManager = projectManagerRepository.findByAccount_Email(email);

        if(projectManager.isEmpty()){
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setManager(ResponseService.MANAGER_NOT_FOUND.name());
            projectDTO.setStatus("");
            return projectDTO;
        }

        if(projectManager.get().getProjectRole() == null){
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setManager(email);
            projectDTO.setName(ResponseService.PROJECT_NOT_ASSIGNED.name());
            projectDTO.setStatus("");
            return projectDTO;
        }

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setManager(email);
        projectDTO.setName(projectManager.get().getProjectRole().getCompanyProject().getName());
        projectDTO.setStatus(projectManager.get().getProjectRole().getCompanyProject().getProjectStatus().getName());

        return projectDTO;

    }

    public boolean updateManagerData(ProjectDTO projectDTO){
        Optional<ProjectManager> projectManager = projectManagerRepository.findByAccount_Email(projectDTO.getManager());

        if(projectManager.isEmpty()){
            return false;
        }

        if(projectManager.get().getProjectRole() == null) {

            ProjectRole projectRole = new ProjectRole();
            Optional<RoleType> roleType = roleTypesRepository.findByName("Team Lead");

            if(roleType.isEmpty()){
                return false;
            }

            Optional<ProjectStatus> projectStatus = projectStatusRepository.getByName(projectDTO.getStatus());
            if(projectStatus.isEmpty())
                return false;

            //create project
            Project project = new Project();
            project.setName(projectDTO.getName());
            project.setProjectStatus(projectStatus.get());

            projectRepository.save(project);

            //create project role
            projectRole.setRoleType(roleType.get());
            projectRole.setCompanyProject(project);
            projectRole.setDescription("Coordinates the team");
            roleRepository.save(projectRole);

            projectManager.get().setProjectRole(projectRole);
            projectManagerRepository.save(projectManager.get());

            return true;
        }
        else{

            ProjectRole projectRole = projectManager.get().getProjectRole();
            projectRole.getCompanyProject().setName(projectDTO.getName());

            Optional<ProjectStatus> projectStatus = projectStatusRepository.getByName(projectDTO.getStatus());
            if(projectStatus.isEmpty())
                return false;

            projectRole.getCompanyProject().setProjectStatus(projectStatus.get());
            roleRepository.save(projectRole);
            return true;
        }
    }

}
