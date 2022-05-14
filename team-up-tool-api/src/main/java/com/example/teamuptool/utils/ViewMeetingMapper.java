package com.example.teamuptool.utils;

import com.example.teamuptool.dto.UserDTO;
import com.example.teamuptool.dto.ViewMeetingDTO;
import com.example.teamuptool.model.Meeting;
import com.example.teamuptool.model.RegularUser;

public class ViewMeetingMapper {

    public ViewMeetingDTO convertToDto(Meeting meeting){

        ViewMeetingDTO meetingDTO = new ViewMeetingDTO();

        meetingDTO.setEndTime(meeting.getEndTime().toString());
        meetingDTO.setStartTime(meeting.getStartTime().toString());
        meetingDTO.setId(String.valueOf(meeting.getId()));
        meetingDTO.setLink(meeting.getLink());
        meetingDTO.setRoom(meeting.getRoom().getName());
        return meetingDTO;

    }

}
