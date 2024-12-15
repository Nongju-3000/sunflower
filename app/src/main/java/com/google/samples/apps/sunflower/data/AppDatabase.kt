/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.google.samples.apps.sunflower.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.samples.apps.sunflower.utilities.DATABASE_NAME
import com.google.samples.apps.sunflower.utilities.PLANT_DATA_FILENAME
import com.google.samples.apps.sunflower.workers.SeedDatabaseWorker
import com.google.samples.apps.sunflower.workers.SeedDatabaseWorker.Companion.KEY_FILENAME

/**
 * 애플리케이션의 Room 데이터베이스를 정의하는 클래스
 * - Room을 통해 데이터베이스와 상호작용하는 DAO를 포함합니다.
 * - 데이터베이스를 초기화하고 싱글톤 패턴으로 관리합니다.
 */
@Database(entities = [GardenPlanting::class, Plant::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // Calendar와 같은 복잡한 데이터 타입을 변환
abstract class AppDatabase : RoomDatabase() {

    // DAO 정의
    abstract fun gardenPlantingDao(): GardenPlantingDao
    abstract fun plantDao(): PlantDao

    companion object {

        // 싱글톤 인스턴스 관리
        @Volatile private var instance: AppDatabase? = null

        /**
         * 데이터베이스 인스턴스를 반환
         * - 싱글톤 패턴을 사용하여 인스턴스를 한 번만 생성하고 재사용합니다.
         *
         * @param context 애플리케이션 컨텍스트
         * @return AppDatabase 인스턴스
         */
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        /**
         * 데이터베이스를 생성 및 초기화
         * - 데이터베이스가 처음 생성될 때 `SeedDatabaseWorker`를 통해 초기 데이터를 삽입합니다.
         *
         * @param context 애플리케이션 컨텍스트
         * @return AppDatabase 인스턴스
         */
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // WorkManager를 사용하여 데이터베이스에 초기 데이터 삽입
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}
