package com.work.cuidese.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.work.cuidese.data.AppDatabase
import com.work.cuidese.data.Vaccine
import com.work.cuidese.utils.VACCINES_FILENAME
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(VACCINES_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val vaccineType = object : TypeToken<List<Vaccine>>() {}.type
                    val vaccineList: List<Vaccine> = Gson().fromJson(jsonReader, vaccineType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.vaccineDao().insertAll(vaccineList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Erro ao preencher vacinas", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}