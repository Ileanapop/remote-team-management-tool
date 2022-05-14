package com.example.teamuptool.service;

import com.example.teamuptool.dto.AdministratorDTO;
import com.example.teamuptool.dto.EmployeeDTO;
import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.model.*;
import com.example.teamuptool.repository.*;
import com.example.teamuptool.utils.AdministratorMapper;
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

    public UserDTO saveUser(UserDTO userDTO){

        LOGGER.info("Verifying duplicated email");
        Optional<RegularUser> existingUser = userRepository.findByAccount_Email(userDTO.getEmail());
        if(existingUser.isPresent())
            return null;

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
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Account newAccount = new Account(bCryptPasswordEncoder.encode(userDTO.getPassword()),userDTO.getEmail());
        regularUser.setAccount(newAccount);
        regularUser.setName(userDTO.getName());
        regularUser.setPhone(userDTO.getPhone());

        UserMapper userMapper = new UserMapper();

        accountRepository.save(newAccount);
        return userMapper.convertToDto(userRepository.save(regularUser));
    }


    public List<String> getTechnologies(){
        List<Technology> existingTechnologies = technologyRepository.findAll();

        List<String> technologies = new ArrayList<>();

        for(Technology technology: existingTechnologies){
            technologies.add(technology.getName());
        }
        return technologies;
    }

    public List<String> getActiveProjects(){

        Optional<ProjectStatus> activeStatus = projectStatusRepository.getByName("ACTIVE");

        if(activeStatus.isPresent()){
            List<Project> activeProjects = projectRepository.getAllByProjectStatus(activeStatus.get());

            List<String> projectNames = new ArrayList<>();

            for(Project project: activeProjects){
                projectNames.add(project.getName());
            }
            return projectNames;
        }
        return null;
    }

    public List<String> getManagers(){

        List<ProjectManager> managers = projectManagerRepository.findAll();

        List<String> projectManagersEmails = new ArrayList<>();

        for(ProjectManager projectManager: managers)
            projectManagersEmails.add(projectManager.getAccount().getEmail());
        return  projectManagersEmails;
    }

    public List<String> getEmployees(){

        List<Employee> employees = employeeRepository.findAll();

        List<String> employeesEmail = new ArrayList<>();

        for(Employee employee: employees)
            employeesEmail.add(employee.getAccount().getEmail());

        return  employeesEmail;
    }


    public boolean updateEmployee(EmployeeDTO employeeDTO){

        Optional<Employee> employee = employeeRepository.findByAccount_Email(employeeDTO.getEmail());
        Optional<ProjectManager> manager = projectManagerRepository.findByAccount_Email(employeeDTO.getManager());
        Optional<Technology> technology = technologyRepository.findByName(employeeDTO.getTechnology());

        if(employee.isPresent() && manager.isPresent() && technology.isPresent()){
            employee.get().setProjectManager(manager.get());
            employee.get().setTechnology(technology.get());
            employeeRepository.save(employee.get());
            return true;
        }

        return false;
    }

}
