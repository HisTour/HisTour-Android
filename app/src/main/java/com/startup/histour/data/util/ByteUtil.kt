package com.startup.histour.data.util

object ByteUtil {
    const val ONE_GB_OF_BYTE: Long = 1_000_000_000
    const val ONE_GIB_OF_BYTE: Long = 1_024 * 1_024 * 1_024
    const val ONE_MB_OF_BYTE: Long = 1_000_000
    const val ONE_MIB_OF_BYTE: Long = 1_024 * 1_024

    fun bytesToGiB(bytes: Long): Double {
        return bytes.toDouble() / (1_024 * 1_024 * 1_024)
    }

    fun bytesToGB(bytes: Long): Double {
        return bytes.toDouble() / 1_000_000_000
    }

    fun bytesToMiB(bytes: Long): Double {
        return bytes.toDouble() / (1_024 * 1_024)
    }

    fun bytesToMB(bytes: Long): Double {
        return bytes.toDouble() / 1_000_000
    }
}