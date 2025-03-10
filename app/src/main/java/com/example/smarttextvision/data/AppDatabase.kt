package com.example.smarttextvision.data
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * AppDatabase is the main database class for the application, managing data persistence using Room.
 *
 * It defines the database schema, including the entities (tables) and the Data Access Objects (DAOs)
 * used to interact with the database.
 *
 * @property userDao Provides access to methods for interacting with the [UserEntity] table.
 * @property messageDao Provides access to methods for interacting with the [MessageEntity] table.
 *
 * @see RoomDatabase
 * @see UserDao
 * @see MessageDao
 * @see UserEntity
 * @see MessageEntity
 */
@Database(entities = [UserEntity::class, MessageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_text_vision_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}