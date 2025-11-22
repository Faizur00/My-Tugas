package com.example.assignmenttrack.model

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