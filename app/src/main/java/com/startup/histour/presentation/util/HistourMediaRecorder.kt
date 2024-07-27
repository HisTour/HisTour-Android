package com.startup.histour.presentation.util

import android.content.Context
import android.media.MediaRecorder
import com.startup.histour.core.osversion.OsVersions
import com.startup.histour.presentation.util.DeviceUtil.RecordConst.AUDIO_ENCODER
import com.startup.histour.presentation.util.DeviceUtil.RecordConst.AUDIO_SOURCE
import com.startup.histour.presentation.util.DeviceUtil.RecordConst.BIT_RATE
import com.startup.histour.presentation.util.DeviceUtil.RecordConst.OUTPUT_EXTENSION
import com.startup.histour.presentation.util.DeviceUtil.RecordConst.OUTPUT_FORMAT
import com.startup.histour.presentation.util.DeviceUtil.RecordConst.OUTPUT_PATH
import com.startup.histour.presentation.util.DeviceUtil.RecordConst.SAMPLING_RATE
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistourMediaRecorder @Inject constructor(@ApplicationContext private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null

    /* Recording 여부 */
    private var isRecording: Boolean = false

    private lateinit var recordPath: String

    private var start: Long = 0L

    /* 1초 이상 기록을 했을 때를 위해 중지 체크, Recorder 도 할당이 되어 있어야 함 */
    fun isEnabledStop() = ((System.currentTimeMillis() - start) / 1_000).toInt() >= 1 && mediaRecorder != null

    fun isEnabledRecordSpace(): Long = DeviceUtil.getAvailableSpaceInBytes(context)

    /* 권한이 없을 경우 조심 */
    fun initRecorder(path: String, maxDurationRecordedCallback: () -> Unit) {
        release()
        mediaRecorder = if (OsVersions.isGreaterThanOrEqualsS()) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION") MediaRecorder()
        }
        mediaRecorder?.apply {
            setAudioSource(AUDIO_SOURCE)
            setOutputFormat(OUTPUT_FORMAT)
            setAudioEncoder(AUDIO_ENCODER)
            setAudioSamplingRate(SAMPLING_RATE)
            setOnInfoListener { _, statusCode, _ ->
                when (statusCode) {
                    // setMaxDuration 이 끝났을 때 뷰에게 콜백 전달
                    MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED -> {
                        maxDurationRecordedCallback.invoke()
                    }
                    /* 파일 사이즈는 정하지 않았지만 나중에 사용 할 것을 대비해 추가
                    * MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED : MAX 사이즈에 도달 했을 때, 중지해야함
                    * MEDIA_RECORDER_INFO_MAX_FILESIZE_APPROACHING : MAX 사이즈에 90 % 에 도달 했을 때, 유저에게 알리거나 다른 캐시 파일에 추가적으로 저장하거나 MAX 를 늘러야함
                    *  */
                    MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED -> {}
                    MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_APPROACHING -> {}
                }
            }
            val dateFormat = SimpleDateFormat("yyyymmdd_hhmmss", Locale.getDefault(Locale.Category.FORMAT))
            recordPath =
                kotlin.runCatching { "${context.externalCacheDir?.absolutePath}/${OUTPUT_PATH}/Voice_${dateFormat.format(Date(System.currentTimeMillis()))}${OUTPUT_EXTENSION}" }.getOrElse { "" }
            setOutputFile(recordPath)
            setMaxDuration(60 * 60 * 1_000)
            setAudioEncodingBitRate(BIT_RATE)
            prepare()
        }
    }

    fun startRecord() {
        this.isRecording = true
        // voice 라는 디렉토리에 저장 할 예정, 나중에 다 지워야하는데 다른 캐시까지 지워질 것을 고려
        val voiceDirectory = File(context.externalCacheDir, OUTPUT_PATH)
        if (!voiceDirectory.exists()) {
            voiceDirectory.mkdirs()
        }
        mediaRecorder?.start()
    }

    /**
     * @return Recording Path, RecordingTime
     *  */
    fun finishRecord(): Pair<String, Int> {
        releaseRecorder()
        return Pair(recordPath, (System.currentTimeMillis() - start).toInt())
    }

    fun pauseRecord() {
        if (isRecording) {
            isRecording = false
            mediaRecorder?.pause()
        }
    }

    fun resumeRecord() {
        if (!isRecording) {
            isRecording = true
            mediaRecorder?.resume()
        }
    }

    fun releaseRecorder() {
        isRecording = false
        release()
    }

    private fun release() {
        try {
            mediaRecorder?.run {
                stop()
                release()
            }
        } catch (_: Exception) {
        }
        mediaRecorder = null
    }
}