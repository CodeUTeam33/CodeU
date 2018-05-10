// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

/** Class representing a message. Messages are sent by a User in a Conversation. */
public class Message {

  private final UUID id;
  private final UUID conversation;
  private final UUID author;
  private final String content;
  private final Instant creation;
  private int likesCount;
  private List<User> userLikes;

  /**
   * Constructs a new Message.
   *
   * @param id the ID of this Message
   * @param conversation the ID of the Conversation this Message belongs to
   * @param author the ID of the User who sent this Message
   * @param content the text content of this Message
   * @param creation the creation time of this Message
   */
  public Message(UUID id, UUID conversation, UUID author, String content, Instant creation) {
    this.id = id;
    this.conversation = conversation;
    this.author = author;
    this.content = content;
    this.creation = creation;
    this.likesCount = 0;
    this.userLikes = new ArrayList<User>();
  }

  /** Returns the ID of this Message. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the Conversation this Message belongs to. */
  public UUID getConversationId() {
    return conversation;
  }

  /** Returns the ID of the User who sent this Message. */
  public UUID getAuthorId() {
    return author;
  }

  /** Returns the text content of this Message. */
  public String getContent() {
    return content;
  }

  /** Returns the creation time of this Message. */
  public Instant getCreationTime() {
    return creation;
  }
  
  /** Returns the number of likes for this Message. */
  public int getLikeCount() {
      return likesCount;
  }
  
  /** Increments the number of likes to this Message by one. */
  public void incrementLike() {
      likesCount++;
  }
  
  /** Returns the users who have liked the message. */
  public List<User> getUserLikes() {
      return userLikes;
  }
  
  /** Returns true if the user has already liked the message. */
  public boolean hasUserLiked(User user) {
      return userLikes.contains(user);
  }
  
  /** Adds users the list of users who have liked this message. */
  public void userLikedMessage(User user) {
      userLikes.add(user);
  }
}
