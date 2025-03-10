package com.example.smarttextvision.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
/**
 * Data Access Object (DAO) for interacting with the user table in the database.
 * This interface defines methods for inserting, retrieving, and deleting user data.
 */
@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)
    @Query ("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Int): UserEntity?

    // New method for admins
    @Delete
    suspend fun deleteUser(user: UserEntity)
}

