package com.startup.histour.data.local.entity

import androidx.room.Entity

@Entity(tableName = "chatting_list", primaryKeys = ["id", "taskId", "missionId"])
data class ChattingListEntity(
    val taskId: String,
    val missionId: String,
    val placeId: String,
    val lastMessageOrTaskName:String,
    val createTime: Long,
)
