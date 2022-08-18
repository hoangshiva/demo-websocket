package com.example.demowebsocket.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateCommand implements Serializable {

    private String name;
    private String description;
}
