package com.startup.histour.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.startup.histour.data.local.entity.ChatMessageEntity
import com.startup.histour.data.local.entity.ChattingListEntity

@Database(entities = [ChattingListEntity::class, ChatMessageEntity::class], version = 1)
abstract class ChattingDatabase : RoomDatabase() {

}