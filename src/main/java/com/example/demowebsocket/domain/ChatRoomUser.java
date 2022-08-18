package com.example.demowebsocket.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chat_room_user")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", updatable = false)
    @JsonBackReference
    private ChatRoom chatRoom;

    @Column(name = "user_name")
    private String username;

}
