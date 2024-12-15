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

package com.google.samples.apps.sunflower.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * `PlantDao`는 `Plant` 엔터티와 상호작용하는 Data Access Object(DAO)입니다.
 * - 데이터베이스 쿼리를 정의하며, Room이 자동으로 구현합니다.
 * - Coroutine Flow를 반환하여 비동기 데이터 스트림을 제공합니다.
 */
@Dao
interface PlantDao {

    /**
     * 모든 식물을 이름 순서대로 가져옵니다.
     * @return Flow<List<Plant>> 형태의 식물 데이터 스트림
     */
    @Query("SELECT * FROM plants ORDER BY name")
    fun getPlants(): Flow<List<Plant>>

    /**
     * 특정 성장 구역 번호에 해당하는 식물을 이름 순서대로 가져옵니다.
     * @param growZoneNumber 성장 구역 번호
     * @return Flow<List<Plant>> 형태의 필터링된 식물 데이터 스트림
     */
    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int): Flow<List<Plant>>

    /**
     * 특정 식물 ID에 해당하는 식물 데이터를 가져옵니다.
     * @param plantId 식물의 고유 ID
     * @return Flow<Plant> 형태의 단일 식물 데이터 스트림
     */
    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlant(plantId: String): Flow<Plant>

    /**
     * 식물 데이터를 삽입합니다.
     * - 동일한 기본 키가 존재할 경우, 기존 데이터를 대체합니다.
     * @param plants 삽입할 `Plant` 리스트
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Plant>)
}
