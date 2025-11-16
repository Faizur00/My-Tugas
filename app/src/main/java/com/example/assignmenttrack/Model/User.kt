package com.example.assignmenttrack.Model

import com.example.assignmenttrack.R
import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String,
    var ProfilePictureId: Int,
    var TaskCompleted: Int,
    var TaskLate: Int,
    var TaskPending: Int,
    var TaskTotal: Int = TaskCompleted + TaskLate + TaskPending,

    var TugasTotal: Int,
    var BelajarTotal: Int,
    var KerjaTotal: Int,
)

val defaultUser = User(
    name = "User00",
    ProfilePictureId = R.drawable.profile,
    TaskCompleted = 3,
    TaskPending = 2,
    TaskLate = 1,
    TugasTotal = 1,
    BelajarTotal = 1,
    KerjaTotal = 2
)
