package com.example.assignmenttrack.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey var id: Int = 1,
    var name: String,
    var profilePictureId: Int,
    var taskCompleted: Int,
    var taskLate: Int,
    var taskPending: Int,
    var taskTotal: Int = taskCompleted + taskLate + taskPending,

    var tugasTotal: Int,
    var belajarTotal: Int,
    var kerjaTotal: Int,
)