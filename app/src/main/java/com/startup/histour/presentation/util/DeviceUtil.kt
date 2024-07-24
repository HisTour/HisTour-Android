package com.startup.histour.presentation.util

import android.content.Context
import android.media.MediaRecorder
import android.os.StatFs
import android.util.Log
import com.startup.histour.presentation.util.DeviceUtil.RecordConst.BIT_RATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object DeviceUtil {
    object RecordConst {
        const val BIT_RATE = 96_000 // bps, 96kbps
        const val SAMPLING_RATE = 44_100
        const val OUTPUT_EXTENSION = ".mp4"
        const val OUTPUT_PATH = "voice"
        const val AUDIO_SOURCE = MediaRecorder.AudioSource.MIC
        const val OUTPUT_FORMAT = MediaRecorder.OutputFormat.MPEG_4
        const val AUDIO_ENCODER = MediaRecorder.AudioEncoder.AAC

        /** Byte 를 보냈을 때 해당 Byte 가 몇 초를 저장할 수 있는지 알려줌
         * @param bytesAvailable 측정 할 Byte 값
         * @return second
         * */
        fun getRecordingTimeInSeconds(bytesAvailable: Long): Long {
            val bytesPerSecond = BIT_RATE / 8
            return bytesAvailable / bytesPerSecond
        }
    }

    /**
     * @param context ExternalCacheDir 에 접근 하기 위해 필요
     * @return Byte 단위로 반환
     * */
    fun getAvailableSpaceInBytes(context: Context): Long {
        return runCatching {
            context.externalCacheDir?.let {
                val stat = StatFs(it.path)
                stat.blockSizeLong * stat.availableBlocksLong
            } ?: 0
        }.getOrElse {
            0
        }
    }

    /** External CacheDir 에 있는 파일을 삭제 하기 위해 사용, Child 를 Traversal 하며 삭제
     * @param dir externalCacheDir 에 특정 Directory
     * @return 삭제 여부
     * @throws NullPointerException
     * @throws SecurityException
     * */
    private suspend fun deleteExternalCacheRecordDirectory(dir: File?): Boolean = withContext(Dispatchers.Default) {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            if (children != null) {
                for (child in children) {
                    val childFile = File(dir, child)
                    if (childFile.isDirectory) {
                        deleteExternalCacheRecordDirectory(childFile)
                    } else {
                        val deleted = childFile.delete()
                        Log.d("LMH", "File ${childFile.absolutePath} deleted: $deleted")
                    }
                }
            }
            val deleted = dir.delete()
            Log.d("LMH", "Directory ${dir.absolutePath} deleted: $deleted")
            return@withContext deleted
        }
        return@withContext false
    }
}