/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.samples.apps.sunflower.data.AppDatabase
import com.google.samples.apps.sunflower.data.Plant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * SeedDatabaseWorker는 WorkManager를 사용하여 JSON 데이터를 데이터베이스에 삽입하는 작업을 수행하는 클래스입니다.
 * - CoroutineWorker를 상속하여 비동기 작업을 지원합니다.
 */
class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    /**
     * doWork는 Worker가 실행할 실제 작업을 정의합니다.
     * - JSON 파일에서 데이터를 읽어와 데이터베이스에 삽입합니다.
     */
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // 입력 데이터에서 파일 이름을 가져옴
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                // 파일을 열고 JSON 데이터를 읽음
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        // JSON 데이터를 List<Plant>로 변환
                        val plantType = object : TypeToken<List<Plant>>() {}.type
                        val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)

                        // 데이터베이스 인스턴스를 가져와 데이터 삽입
                        val database = AppDatabase.getInstance(applicationContext)
                        database.plantDao().insertAll(plantList)

                        // 작업 성공 반환
                        Result.success()
                    }
                }
            } else {
                // 파일 이름이 없을 경우 오류 로그 및 작업 실패 반환
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            // 예외 발생 시 오류 로그 및 작업 실패 반환
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        // 로그 태그
        private const val TAG = "SeedDatabaseWorker"

        // WorkManager에 전달할 입력 데이터 키
        const val KEY_FILENAME = "PLANT_DATA_FILENAME"
    }
}
