package com.example.demowebsocket.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.List;

@Document("chat_room")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom implements Serializable {

    @Id
    private String id;

    @Field(name = "room_name")
    private String chatName;

    @Field(name = "description")
    private String description;

    /**
     * ACTIVE, CLOSED
     */
    @Field(name = "status")
    private String status;

    @Field(name = "username")
    private String username;

    @Field(name = "chatRoomUserIds")
    List<String> chatRoomUserIds;
}
