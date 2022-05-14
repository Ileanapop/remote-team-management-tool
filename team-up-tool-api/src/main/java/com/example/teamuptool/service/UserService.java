package com.example.teamuptool.service;


import com.example.teamuptool.dto.*;
import com.example.teamuptool.model.*;
import com.example.teamuptool.repository.*;
import com.example.teamuptool.utils.AdministratorMapper;
import com.example.teamuptool.utils.UserMapper;
import com.example.teamuptool.utils.ViewMeetingMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class UserService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private  CustomerRepository customerRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());

    public UserService(){

    }

    /**
     * Method for authenticating the user
     *
     * @param userLoginDTO credentials of the user
     * @return the user if the credentials sent were correct else returns null
     */
    public UserDTO loginUser(UserLoginDTO userLoginDTO) {

        LOGGER.info("Verifying email...");
        if(userLoginDTO.getType() == 1)
            return loginEmployee(userLoginDTO);
        if(userLoginDTO.getType() == 2)
            return loginProjectManager(userLoginDTO);
        if(userLoginDTO.getType() == 3)
            return loginCustomer(userLoginDTO);
        return null;
    }

    public UserDTO loginEmployee(UserLoginDTO userLoginDTO)
    {
        Optional<Employee> employee =employeeRepository.findByAccount_Email(userLoginDTO.getEmail());

        if(employee.isEmpty()){
            return null;
        }
        LOGGER.info("Verifying password");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), employee.get().getAccount().getPassword())) {
            LOGGER.info("Password correct");
            UserMapper userMapper = new UserMapper();
            return userMapper.convertToDto(employee.get());
        }
        LOGGER.warning("Incorrect credentials");
        return null;
    }

    public UserDTO loginProjectManager(UserLoginDTO userLoginDTO)
    {
        Optional<ProjectManager> manager =projectManagerRepository.findByAccount_Email(userLoginDTO.getEmail());

        if(manager.isEmpty()){
            return null;
        }
        LOGGER.info("Verifying password");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), manager.get().getAccount().getPassword())) {
            LOGGER.info("Password correct");
            UserMapper userMapper = new UserMapper();
            return userMapper.convertToDto(manager.get());
        }
        LOGGER.warning("Incorrect credentials");
        return null;
    }

    public UserDTO loginCustomer(UserLoginDTO userLoginDTO)
    {
        Optional<Customer> customer =customerRepository.findByAccount_Email(userLoginDTO.getEmail());

        if(customer.isEmpty()){
            return null;
        }
        LOGGER.info("Verifying password");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), customer.get().getAccount().getPassword())) {
            LOGGER.info("Password correct");
            UserMapper userMapper = new UserMapper();
            return userMapper.convertToDto(customer.get());
        }
        LOGGER.warning("Incorrect credentials");
        return null;
    }

    public AvailabilityDTO getUserByEmailAndAvailability(String email, Date startTime, Date endTime)
    {
        AvailabilityDTO result1 = getEmployeeByEmailAndAvailability(email,startTime,endTime);
        if(result1==null){
            return getManagerByEmailAndAvailability(email,startTime,endTime);
        }
        return result1;
    }
    public AvailabilityDTO getEmployeeByEmailAndAvailability(String email, Date startTime, Date endTime){
        Optional<Employee> employee = employeeRepository.findByAccount_Email(email);
        if(employee.isEmpty())
            return null;

        boolean available=true;

        for(Meeting meeting: employee.get().getMeetings()){
            Date s1 = meeting.getStartTime();
            Date e1 = meeting.getEndTime();
            System.out.println(s1);
            System.out.println(e1);
            System.out.println(startTime);
            System.out.println(endTime);
            if (s1.before(startTime) && e1.after(startTime) ||
                    s1.before(endTime) && e1.after(endTime) ||
                    s1.before(startTime) && e1.after(endTime) ||
                    s1.after(startTime) && e1.before(endTime)) {
                available = false;
                break;
            }

        }
        if(available)
            return new AvailabilityDTO(employee.get().getAccount().getEmail(),"AVAILABLE");
        else
            return new AvailabilityDTO(employee.get().getAccount().getEmail(),"NOT AVAILABLE");
    }


    public AvailabilityDTO getManagerByEmailAndAvailability(String email, Date startTime, Date endTime){
        Optional<ProjectManager> manager = projectManagerRepository.findByAccount_Email(email);
        if(manager.isEmpty())
            return null;

        boolean available=true;

        for(Meeting meeting: manager.get().getMeetings()){
            Date s1 = meeting.getStartTime();
            Date e1 = meeting.getEndTime();
            if (s1.before(startTime) && e1.after(startTime) ||
                    s1.before(endTime) && e1.after(endTime) ||
                    s1.before(startTime) && e1.after(endTime) ||
                    s1.after(startTime) && e1.before(endTime)) {
                available = false;
                break;
            }

        }
        if(available)
            return new AvailabilityDTO(manager.get().getAccount().getEmail(), "AVAILABLE");
        else
            return new AvailabilityDTO(manager.get().getAccount().getEmail(), "NOT AVAILABLE");
    }

    public boolean createMeeting(MeetingDTO meetingDTO) throws ParseException {

        Meeting newMeeting = new Meeting();
        ISO8601DateFormat df = new ISO8601DateFormat();
        Date startDate = df.parse(meetingDTO.getStartTime());
        Date endDate = df.parse(meetingDTO.getEndTime());

        if(endDate.before(startDate))
            return false;

        newMeeting.setEndTime(endDate);
        newMeeting.setStartTime(startDate);
        System.out.println("Meeting link: " + meetingDTO.getLink());
        newMeeting.setLink(meetingDTO.getLink());

        if(!meetingDTO.getRoom().equals("")){
            Optional<Room> room = roomRepository.findByName(meetingDTO.getRoom());
            if(room.isEmpty()){
                return false;
            }
            newMeeting.setRoom(room.get());
        }
        List<RegularUser> requiredUsers = new ArrayList<>();
        for(String userName: meetingDTO.getUserNames()){
            Optional<RegularUser> regularUser = userRepository.findByAccount_Email(userName);

            if(regularUser.isEmpty()){
                return false;
            }

            System.out.println(regularUser);
            regularUser.get().getMeetings().add(newMeeting);
            requiredUsers.add(regularUser.get());

        }

        newMeeting.setUsers(requiredUsers);
        meetingRepository.save(newMeeting);
        return true;

    }

    public List<ViewMeetingDTO> getMeetings(String email){
        Optional<RegularUser> regularUser = userRepository.findByAccount_Email(email);

        if(regularUser.isEmpty()){
            return null;
        }

        List<ViewMeetingDTO> viewMeetingDTOS = new ArrayList<>();
        ViewMeetingMapper viewMeetingMapper = new ViewMeetingMapper();
        Date date = new Date();

        for(Meeting meeting: regularUser.get().getMeetings()){
            if(!meeting.getEndTime().before(date)) {
                viewMeetingDTOS.add(viewMeetingMapper.convertToDto(meeting));
            }
        }

        return viewMeetingDTOS;
    }

}
