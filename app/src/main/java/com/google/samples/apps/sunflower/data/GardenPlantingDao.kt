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
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * `GardenPlantingDao`는 [GardenPlanting] 엔터티와 상호작용하는 Data Access Object(DAO)입니다.
 * - Room을 사용해 데이터베이스 쿼리를 정의하며, Room이 자동으로 구현합니다.
 */
@Dao
interface GardenPlantingDao {

    /**
     * 모든 `GardenPlanting` 데이터를 반환합니다.
     *
     * @return Flow<List<GardenPlanting>> 형태의 GardenPlanting 데이터 스트림
     */
    @Query("SELECT * FROM garden_plantings")
    fun getGardenPlantings(): Flow<List<GardenPlanting>>

    /**
     * 특정 `plantId`에 해당하는 식물이 이미 심어졌는지 확인합니다.
     *
     * @param plantId 확인할 식물 ID
     * @return Flow<Boolean> 형태로 심어졌는지 여부를 반환
     */
    @Query("SELECT EXISTS(SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1)")
    fun isPlanted(plantId: String): Flow<Boolean>

    /**
     * `Plant`와 `GardenPlanting` 테이블을 조인하여 심어진 식물 데이터를 가져옵니다.
     * - Room은 이 쿼리를 기반으로 [PlantAndGardenPlantings] 객체를 자동으로 매핑합니다.
     *
     * @return Flow<List<PlantAndGardenPlantings>> 형태의 심어진 식물 데이터 스트림
     */
    @Transaction
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    fun getPlantedGardens(): Flow<List<PlantAndGardenPlantings>>

    /**
     * 새로운 `GardenPlanting` 데이터를 삽입합니다.
     *
     * @param gardenPlanting 삽입할 GardenPlanting 객체
     * @return 삽입된 행의 ID
     */
    @Insert
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlanting): Long

    /**
     * 주어진 `GardenPlanting` 데이터를 삭제합니다.
     *
     * @param gardenPlanting 삭제할 GardenPlanting 객체
     */
    @Delete
    suspend fun deleteGardenPlanting(gardenPlanting: GardenPlanting)
}
