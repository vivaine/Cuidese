package com.work.cuidese.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.work.cuidese.utils.DATABASE_NAME
import com.work.cuidese.workers.SeedDatabaseWorker
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Person::class, Vaccine::class, PersonVaccine::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun vaccineDao(): VaccineDao
    abstract fun personVaccineDao(): PersonVaccineDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(
            context: Context
        ): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object: RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                ).build()
        }
    }

}
