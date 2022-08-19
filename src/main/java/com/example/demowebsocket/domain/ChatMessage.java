package com.example.demowebsocket.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "chat_message")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "user_from")
    private String userFrom;

    @Column(name = "user_to")
    private String userTo;

    @Column(name = "content")
    private String content;

    @Column(name = "added_timesstamp")
    private Timestamp addedTimestamp;

    @Column(name = "recall")
    private Boolean recall;

    @Column(name = "recall_time")
    private Timestamp recallTime;

    @Column(name = "internal")
    private Boolean internal;

    @Column(name = "is_customer")
    private Boolean isCustomer;

    @Column(name = "attachments")
    private String attachments;

    @Column(name = "is_public")
    private Boolean isPublic;


}
