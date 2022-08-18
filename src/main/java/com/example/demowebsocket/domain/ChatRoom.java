package com.example.demowebsocket.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chat_room")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name")
    private String chatName;

    @Column(name = "description")
    private String description;

    /**
     * ACTIVE, CLOSED
     */
    @Column(name = "status")
    private String status;

    @Column(name = "username")
    private String username;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chatRoom",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<ChatRoomUser> chatRoomUsers;

    public void addUser(ChatRoomUser chatRoomUser) {
        this.chatRoomUsers.add(chatRoomUser);
        chatRoomUser.setChatRoom(this);
    }

}
