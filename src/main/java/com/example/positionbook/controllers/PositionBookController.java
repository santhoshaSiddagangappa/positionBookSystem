package com.example.positionbook.controllers;

import com.example.positionbook.services.Events;
import com.example.positionbook.services.Positions;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/*
A controller class providing the endpoints to send in a list of events and get the positions held in the program.
*/

@RestController
public class PositionBookController {

    @Autowired
    private Positions positions;

    // Get all position book
    @GetMapping("/positions")
    Positions getPositionBook() throws JsonProcessingException {
        return positions;
    }

    @PostMapping("/events")
    void sendTradeEvents(@RequestBody Events newEvents) {
        positions.processNewEvents(newEvents);
    }

}


