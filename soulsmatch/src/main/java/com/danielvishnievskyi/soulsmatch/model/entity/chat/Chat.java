package com.danielvishnievskyi.soulsmatch.model.entity.chat;

import com.danielvishnievskyi.soulsmatch.model.entity.Soul;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chat")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToMany
  @JoinTable(
    name = "chat_souls",
    joinColumns = @JoinColumn(name = "chat_id"),
    inverseJoinColumns = @JoinColumn(name = "soul_id")
  )
  private List<Soul> participants;

  @OneToMany(mappedBy = "chat", cascade = CascadeType.MERGE, orphanRemoval = true)
  private List<Message> messages;
}
