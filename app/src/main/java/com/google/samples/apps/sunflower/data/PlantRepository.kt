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

import javax.inject.Inject
import javax.inject.Singleton

/**
 * PlantRepository는 식물 데이터를 관리하는 레포지토리 모듈입니다.
 *
 * - [PlantDao]에서 제공되는 Flow를 사용하여 안전하게 데이터를 수집할 수 있습니다.
 * - Room은 Coroutine을 지원하며, 쿼리 실행을 메인 스레드가 아닌 별도의 스레드에서 처리합니다.
 *
 * @param plantDao 데이터베이스 작업을 처리하는 Data Access Object (DAO)
 */
@Singleton
class PlantRepository @Inject constructor(private val plantDao: PlantDao) {

    /**
     * 모든 식물 데이터를 반환
     * @return Flow<List<Plant>> 형태의 식물 데이터 스트림
     */
    fun getPlants() = plantDao.getPlants()

    /**
     * 특정 식물 ID에 해당하는 식물 데이터를 반환
     * @param plantId 식물의 고유 ID
     * @return Flow<Plant> 형태의 단일 식물 데이터 스트림
     */
    fun getPlant(plantId: String) = plantDao.getPlant(plantId)

    /**
     * 특정 성장 구역 번호에 해당하는 식물 데이터를 반환
     * @param growZoneNumber 성장 구역 번호
     * @return Flow<List<Plant>> 형태의 필터링된 식물 데이터 스트림
     */
    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)
}
