package com.moly.edi.data.dataSource.local.dao

import android.database.Observable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.moly.edi.domain.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity WHERE correo = :email AND password = :password")
    fun findByEmailAndPassword(email: String, password: String): User

    @Query("SELECT * FROM UserEntity WHERE correo = :email")
    fun findByEmail(email: String): User

    @Query("SELECT COUNT(*) FROM UserEntity")
    fun countUsers(): Observable<Int>

    @Insert
    fun insertUser(user: User): Int

    @Query("SELECT COUNT(*) FROM UserEntity WHERE correo = :emailId")
    fun isDataExist(emailId: String): Int
}
