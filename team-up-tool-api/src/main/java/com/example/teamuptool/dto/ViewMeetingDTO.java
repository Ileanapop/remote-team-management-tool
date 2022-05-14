package com.example.teamuptool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
public class ViewMeetingDTO {

    private String id;

    private String link;

    private String room;

    private String startTime;

    private String endTime;
}
