package com.example.positionbook.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/*
A class to store list of all positions (aggregated trade events). It also holds the functionality
to process events sent into the program.
*/

@Data
@Service
public class Positions {

    @JsonProperty("Positions")
    private List<Position> positions = new ArrayList<>();

    public void processNewEvents(Events newEvents){
        TreeMap<String,Position> curTreeMap = new TreeMap<>();
        for (Event curEvent : newEvents.getEvents()){
            String curAccount = curEvent.getAccount();
            String curSecurity = curEvent.getSecurity();
            Position curPosition = curTreeMap.getOrDefault(curAccount+curSecurity, new Position(curAccount, curSecurity));

            if (curEvent.getAction().equals("CANCEL")){
                for (Event event: curPosition.getEvents()){
                    if (event.getId() == curEvent.getId()){
                        int curQuantity = curPosition.getQuantity();
                        curQuantity -= event.getQuantity();
                        curPosition.setQuantity(curQuantity);
                    }
                }
            }

            curPosition.addEvent(curEvent);

            int curQuantity = curPosition.getQuantity();
            System.out.println(curQuantity);
            if (curEvent.getAction().equals("BUY")){
                curQuantity += curEvent.getQuantity();
            } else if (curEvent.getAction().equals("SELL")){
                curQuantity -= curEvent.getQuantity();
            }
            System.out.println(curQuantity);
            System.out.println("-----");
            curPosition.setQuantity(curQuantity);
            curTreeMap.put(curAccount+curSecurity,curPosition);
        }

        for (var entry : curTreeMap.entrySet()) {
            positions.add(entry.getValue());
        }
    }
}

