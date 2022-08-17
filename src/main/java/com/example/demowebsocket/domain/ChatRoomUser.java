package com.example.demowebsocket.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chat_room")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_room_id")
    private Long chatRoomId;

    @Column(name = "user_name")
    private String username;

}
