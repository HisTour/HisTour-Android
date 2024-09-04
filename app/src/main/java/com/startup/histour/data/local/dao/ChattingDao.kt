package com.startup.histour.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.startup.histour.data.local.entity.ChatMessageEntity
import com.startup.histour.data.local.entity.ChattingListEntity

@Dao
interface ChattingDao {

    // 1. placeId, missionId에 따른 채팅방 리스트 GET (createTime에 따라 최신순으로)
    @Query("SELECT * FROM chatting_list WHERE placeId = :placeId AND missionId = :missionId ORDER BY createTime DESC")
    suspend fun getChattingListByPlaceAndMission(placeId: String, missionId: String): List<ChattingListEntity>

    // 2. 채팅방 리스트 Add 함수
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChattingList(chattingList: ChattingListEntity)

    // 3. 채팅방 리스트에서 lastMessageOrTaskName만 업데이트 하는 함수
    @Query("UPDATE chatting_list SET lastMessageOrTaskName = :lastMessageOrTaskName WHERE taskId = :taskId AND missionId = :missionId AND placeId = :placeId")
    suspend fun updateLastMessageOrTaskName(taskId: String, missionId: String, placeId: String, lastMessageOrTaskName: String)

    // 4. 채팅 메세지 add 함수
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(chatMessage: ChatMessageEntity)

    // 5. 채팅방과 채팅 메세지를 JOIN하는 함수 (채팅방에 따른 메세지들을 가져오는 함수)
    @Query(
        """
        SELECT * FROM chatting_message 
        INNER JOIN chatting_list 
        ON chatting_message.taskId = chatting_list.taskId 
        AND chatting_message.missionId = chatting_list.missionId 
        AND chatting_message.placeId = chatting_list.placeId
        WHERE chatting_list.taskId = :taskId AND chatting_list.missionId = :missionId AND chatting_list.placeId = :placeId
        ORDER BY chatting_message.time ASC
    """
    )
    suspend fun getMessagesByChattingList(taskId: String, missionId: String, placeId: String): List<ChatMessageEntity>
}