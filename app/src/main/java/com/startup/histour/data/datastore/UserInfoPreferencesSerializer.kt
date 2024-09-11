package com.startup.histour.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import com.startup.histour.UserInfo

object UserInfoPreferencesSerializer : Serializer<UserInfo> {
    override val defaultValue: UserInfo
        get() = UserInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserInfo {
        try {
            return UserInfo.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("proto buf readFrom() exception occurred", exception)
        }
    }

    override suspend fun writeTo(t: UserInfo, output: OutputStream) {
        t.writeTo(output)
    }
}
