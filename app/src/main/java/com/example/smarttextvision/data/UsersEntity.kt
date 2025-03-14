package com.example.smarttextvision.data
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user in the application's database.
 *
 * This data class defines the structure of the 'users' table in the database.
 * It includes essential user information such as their unique ID, name, email,
 * password, and role.
 *
 * @property id The unique identifier for the user. It's auto-generated by the database.
 * @property name The user's full name.
 * @property email The user's email address, which should be unique.
 * @property password The user's password.
 *                   **Important Security Note:** In a production environment,
 *                   this should be a securely hashed value (e.g., using SHA-256 or bcrypt)
 *                   and never stored as plain text.
 * @property role The user's role within the application. It defaults to "user"
 *                but can be set to "admin" for administrative users.
 *                Valid values are "user" and "admin".
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val email: String,
    val password: String, // In production, hash this (e.g., with SHA-256)
    val role: String = "user" // "user" or "admin"
)