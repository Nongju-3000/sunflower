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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * GardenPlantingRepository는 GardenPlanting 데이터를 관리하는 레포지토리입니다.
 * - GardenPlantingDao와 상호작용하여 데이터를 삽입, 삭제, 또는 조회합니다.
 *
 * @param gardenPlantingDao GardenPlanting 테이블 작업을 처리하는 DAO
 */
@Singleton
class GardenPlantingRepository @Inject constructor(
    private val gardenPlantingDao: GardenPlantingDao
) {

    /**
     * GardenPlanting 데이터를 생성하여 데이터베이스에 삽입합니다.
     *
     * @param plantId 생성할 GardenPlanting의 식물 ID
     */
    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId) // 새로운 GardenPlanting 객체 생성
        gardenPlantingDao.insertGardenPlanting(gardenPlanting) // DAO를 통해 데이터베이스에 삽입
    }

    /**
     * 주어진 GardenPlanting 데이터를 데이터베이스에서 삭제합니다.
     *
     * @param gardenPlanting 삭제할 GardenPlanting 객체
     */
    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting) {
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting) // DAO를 통해 데이터베이스에서 삭제
    }

    /**
     * 특정 식물이 이미 심어졌는지 여부를 확인합니다.
     *
     * @param plantId 확인할 식물 ID
     * @return Flow<Boolean> 형태로 심어졌는지 여부를 반환
     */
    fun isPlanted(plantId: String) =
        gardenPlantingDao.isPlanted(plantId)

    /**
     * 심어진 모든 GardenPlanting 데이터를 반환합니다.
     *
     * @return Flow<List<PlantAndGardenPlantings>> 형태로 심어진 식물 데이터를 반환
     */
    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()
}
