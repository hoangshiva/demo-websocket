package com.example.demowebsocket.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;

@Document("chat_room_user")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomUser implements Serializable {

    @Id
    private String id;

    private String chatRoomId;

    @Field(name = "user_name")
    private String username;

}
