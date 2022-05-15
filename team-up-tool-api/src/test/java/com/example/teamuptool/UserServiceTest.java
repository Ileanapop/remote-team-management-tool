package com.example.teamuptool;


import com.example.teamuptool.dto.AvailabilityDTO;
import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.dto.UserLoginDTO;
import com.example.teamuptool.model.Account;
import com.example.teamuptool.model.Customer;
import com.example.teamuptool.model.Employee;
import com.example.teamuptool.model.ProjectManager;
import com.example.teamuptool.repository.CustomerRepository;
import com.example.teamuptool.repository.EmployeeRepository;
import com.example.teamuptool.repository.ProjectManagerRepository;
import com.example.teamuptool.repository.UserRepository;
import com.example.teamuptool.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectManagerRepository projectManagerRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void loginEmployeeCorrectCredentialsShouldReturnEmployee() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");

        Employee employee = new Employee();
        Account account = new Account();
        account.setEmail("email");
        account.setPassword(encodedPassword);
        employee.setAccount(account);
        employee.setName("name");
        employee.setPhone("+40751513971");


        Mockito.when(employeeRepository.findByAccount_Email(Mockito.anyString())).thenReturn(java.util.Optional.of(employee));

        UserLoginDTO userLoginDTO = new UserLoginDTO("email","password",1);
        UserDTO userDTO = userService.loginEmployee(userLoginDTO);

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals("email", userDTO.getEmail());
        Assertions.assertEquals(encodedPassword, userDTO.getPassword());
        Assertions.assertEquals("name", userDTO.getName());


    }

    @Test
    public void loginEmployeeWrongCredentialsShouldReturnNull() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");

        Employee employee = new Employee();
        Account account = new Account();
        account.setEmail("email");
        account.setPassword(encodedPassword);
        employee.setAccount(account);

        Mockito.when(employeeRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.of(employee));

        String email = "email";
        String password = "wrong";

        UserLoginDTO userLoginDTO = new UserLoginDTO(email,password,1);
        UserDTO userDTO = userService.loginEmployee(userLoginDTO);

        Assertions.assertNull(userDTO);


    }


    @Test
    public void loginManagerCorrectCredentialsShouldReturnProjectManager() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");

        ProjectManager projectManager = new ProjectManager();
        Account account = new Account();
        account.setEmail("email");
        account.setPassword(encodedPassword);
        projectManager.setAccount(account);
        projectManager.setName("name");
        projectManager.setPhone("+40751513971");


        Mockito.when(projectManagerRepository.findByAccount_Email(Mockito.anyString())).thenReturn(java.util.Optional.of(projectManager));

        UserLoginDTO userLoginDTO = new UserLoginDTO("email","password",2);
        UserDTO userDTO = userService.loginProjectManager(userLoginDTO);

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals("email", userDTO.getEmail());
        Assertions.assertEquals(encodedPassword, userDTO.getPassword());
        Assertions.assertEquals("name", userDTO.getName());

    }

    @Test
    public void loginManagerWrongCredentialsShouldReturnNull() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");

        ProjectManager projectManager = new ProjectManager();
        Account account = new Account();
        account.setEmail("email");
        account.setPassword(encodedPassword);
        projectManager.setAccount(account);

        Mockito.when(projectManagerRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.of(projectManager));

        String email = "email";
        String password = "wrong";

        UserLoginDTO userLoginDTO = new UserLoginDTO(email,password,1);
        UserDTO userDTO = userService.loginProjectManager(userLoginDTO);

        Assertions.assertNull(userDTO);

    }

    @Test
    public void loginCustomerCorrectCredentialsShouldReturnCustomer() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");

        Customer customer = new Customer();
        Account account = new Account();
        account.setEmail("email");
        account.setPassword(encodedPassword);
        customer.setAccount(account);
        customer.setName("name");
        customer.setPhone("+40751513971");


        Mockito.when(customerRepository.findByAccount_Email(Mockito.anyString())).thenReturn(java.util.Optional.of(customer));

        UserLoginDTO userLoginDTO = new UserLoginDTO("email","password",3);
        UserDTO userDTO = userService.loginCustomer(userLoginDTO);

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals("email", userDTO.getEmail());
        Assertions.assertEquals(encodedPassword, userDTO.getPassword());
        Assertions.assertEquals("name", userDTO.getName());

    }

    @Test
    public void loginCustomerWrongCredentialsShouldReturnNull() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode("password");

        Customer customer = new Customer();
        Account account = new Account();
        account.setEmail("email");
        account.setPassword(encodedPassword);
        customer.setAccount(account);
        customer.setName("name");
        customer.setPhone("+40751513971");

        Mockito.when(customerRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.of(customer));

        String email = "email";
        String password = "wrong";

        UserLoginDTO userLoginDTO = new UserLoginDTO(email,password,3);
        UserDTO userDTO = userService.loginCustomer(userLoginDTO);

        Assertions.assertNull(userDTO);

    }

    @Test
    public void getManagerByEmailAndAvailabilityIsAvailableReturnsAvailableString() {

        ProjectManager projectManager = new ProjectManager();
        Account account = new Account();
        account.setEmail("email");
        projectManager.setAccount(account);
        projectManager.setMeetings(new ArrayList<>());

        Mockito.when(projectManagerRepository.findByAccount_Email(Mockito.anyString())).thenReturn(Optional.of(projectManager));

        AvailabilityDTO availabilityDTO = userService.getManagerByEmailAndAvailability("email",new Date(), new Date());

        Assertions.assertNotNull(availabilityDTO);
        Assertions.assertEquals("email", availabilityDTO.getEmployeeName());
        Assertions.assertEquals("AVAILABLE", availabilityDTO.getAvailability());

    }

}
