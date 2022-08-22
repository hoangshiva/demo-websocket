package com.example.demowebsocket.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Document("chat_message")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable {

    @Id
    private String id;

    @Field(name = "room_id")
    private Long roomId;

    @Field(name = "user_from")
    private String userFrom;

    @Field(name = "user_to")
    private String userTo;

    @Field(name = "content")
    private String content;

    @Field(name = "added_timesstamp")
    private Timestamp addedTimestamp;

    @Field(name = "recall")
    private Boolean recall;

    @Field(name = "recall_time")
    private Timestamp recallTime;

    @Field(name = "internal")
    private Boolean internal;

    @Field(name = "is_customer")
    private Boolean isCustomer;

    @Field(name = "attachments")
    private String attachments;

    @Field(name = "is_public")
    private Boolean isPublic;

    @Field(name = "unread")
    private Boolean unread;


}
