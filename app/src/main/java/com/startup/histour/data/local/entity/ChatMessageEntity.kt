package com.startup.histour.data.local.entity

import androidx.room.Entity
import com.startup.histour.presentation.mission.viewmodel.Author

@Entity(tableName = "chatting_message", primaryKeys = ["id", "taskId", "missionId"])
data class ChatMessageEntity(
    val id: Long,
    val taskId: String,
    val missionId: String,
    val placeId: String,
    val author: Author,
    val message: String,
    val time: Long,
)