package com.egoriku.radiotok.util

import android.content.Context
import android.net.Uri
import com.anggrayudi.storage.extension.fromSingleUri
import com.anggrayudi.storage.file.openInputStream
import com.anggrayudi.storage.file.openOutputStream
import com.egoriku.radiotok.db.RadioTokDb
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class BackupManager(
    private val context: Context,
    private val db: RadioTokDb
) {
    private val gson = Gson()

    val backupFileName: String
        get() = SimpleDateFormat(
            "'radiotok_db_'yyyyMMddHHmm'.bin'", Locale.getDefault()
        ).format(Date())

    suspend fun backup(outputFile: Uri) {
        withContext(Dispatchers.IO) {
            context.fromSingleUri(outputFile)
                ?.openOutputStream(context)
                ?.use { stream ->
                    val backup = Backup(
                        likedIds = db.stationDao().getLikedStations()
                            .map { entity -> entity.stationUuid },
                        dislikedIds = db.stationDao().getDislikedStations()
                            .map { entity -> entity.stationUuid }
                    )

                    stream.write(gson.toJson(backup).toByteArray())
                }
        }
    }

    suspend fun restore(inputFile: Uri) = withContext(Dispatchers.IO) {
        context.fromSingleUri(inputFile)
            ?.openInputStream(context)
            ?.use {
                val backup = gson.fromJson(it.bufferedReader(), Backup::class.java)

                db.stationDao().updateLiked(backup.likedIds)
                db.stationDao().updateDisliked(backup.dislikedIds)
            }
            ?: error("")
    }

    data class Backup(
        @SerializedName("likedIds")
        val likedIds: List<String>,

        @SerializedName("dislikedIds")
        val dislikedIds: List<String>
    )
}