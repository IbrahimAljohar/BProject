package com.example.smarttextvision.data


/**
 * Repository class for managing user data.
 *
 * This class acts as an intermediary between the application's business logic and the
 * underlying data access layer (DAOs). It provides methods to perform common operations
 * on user data, such as inserting, retrieving, and deleting users.
 *
 * @property userDao The data access object (DAO) responsible for interacting with the
 *                    user data in the underlying database.
 */
class UserRepository(private val userDao: UserDao) {
    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
    suspend fun getUserByEmail(email: String): UserEntity? = userDao.getUserByEmail(email)
    suspend fun deleteUser(user: UserEntity) = userDao.deleteUser(user)
    suspend fun getAllUsers(): List<UserEntity> = userDao.getAllUsers()
    suspend fun getUserById(userId:Int): UserEntity? = userDao.getUserById(userId)
}
class ChatRepository(private val messageDao: MessageDao) {
    suspend fun insertMessage(message: MessageEntity) = messageDao.insertMessage(message)
    suspend fun getChatHistory(userId: Long, otherUserId: Long): List<MessageEntity> =
        messageDao.getChatHistory(userId, otherUserId)
    suspend fun getAllMessages(): List<MessageEntity> = messageDao.getAllMessages()
}