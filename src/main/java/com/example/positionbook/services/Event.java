package com.example.positionbook.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
A class to store each trade event and its associated data
*/
@Data
public class Event {
    @JsonProperty("ID")
    private int id;
    @JsonProperty("Action")
    private String action;
    @JsonProperty("Account")
    private String account;
    @JsonProperty("Security")
    private String security;
    @JsonProperty("Quantity")
    private int quantity;

    public Event(){

    }
}
