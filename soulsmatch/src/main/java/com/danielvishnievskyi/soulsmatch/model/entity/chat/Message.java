package com.danielvishnievskyi.soulsmatch.model.entity.chat;

import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "uuid", nullable = false)
  private UUID uuid;

  @ManyToOne
  @JoinColumn(name = "chat_id", nullable = false)
  private Chat chat;

  @ManyToOne
  @JoinColumn(name = "soul_id")
  private Soul soul;

  @Column(name = "content", columnDefinition = "TEXT", nullable = false)
  private String content;

  @Column(name = "isRead", nullable = false)
  private boolean isRead;

  @Column(name = "time", nullable = false)
  private LocalDateTime time;
}
