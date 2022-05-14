package com.example.teamuptool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
public class MeetingDTO {

    @NotEmpty(message = "Required users list cannot be empty.")
    private List<String> userNames;

    @NotNull(message = "Meeting link cannot be null")
    private String link;

    private String room;

    @NotNull(message = "Start time of meeting cannot be null")
    private String startTime;

    @NotNull(message = "End time of meeting cannot be null")
    private String endTime;

}
