package com.example.positionbook.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
A class to store aggregated events
*/
@Data
public class Position {
    @JsonProperty("Account")
    private String account;
    @JsonProperty("Security")
    private String security;
    @JsonProperty("Quantity")
    private int quantity;
    @JsonProperty("Events")
    private List<Event> events;

    public Position(String account, String security){
        this.account = account;
        this.security = security;
        this.quantity = 0;
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event){
        events.add(event);
    }

}
