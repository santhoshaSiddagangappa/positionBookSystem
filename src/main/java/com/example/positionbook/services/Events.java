package com.example.positionbook.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
A class to store the list of all events read into the program
*/

@Data
public class Events {

    @JsonProperty("Events")
    private List<Event> events = new ArrayList<>();
}
