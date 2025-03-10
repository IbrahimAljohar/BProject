package com.example.smarttextvision.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
/**
 * Data Access Object (DAO) interface for interacting with the messages table in the database.
 * This interface provides methods for inserting messages, retrieving chat history, and
 * retrieving all messages (for administrative purposes).
 */
@Dao
interface MessageDao {
    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT * FROM messages WHERE (senderId = :userId AND receiverId = :otherUserId) OR (senderId = :otherUserId AND receiverId = :userId) ORDER BY timestamp")
    suspend fun getChatHistory(userId: Long, otherUserId: Long): List<MessageEntity>

    // New method for admins
    @Query("SELECT * FROM messages ORDER BY timestamp")
    suspend fun getAllMessages(): List<MessageEntity>
}