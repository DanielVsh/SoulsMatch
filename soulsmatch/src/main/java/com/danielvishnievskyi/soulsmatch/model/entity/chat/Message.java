package com.danielvishnievskyi.soulsmatch.model.entity.chat;

import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "chat_id",  nullable = false)
  private Chat chat;

  @ManyToOne
  @JoinColumn(name = "soul_id")
  private Soul soul;

  @Column(name = "content", columnDefinition = "TEXT", nullable = false)
  private String content;

  @Column(name = "time", nullable = false)
  private LocalDateTime time;
}
