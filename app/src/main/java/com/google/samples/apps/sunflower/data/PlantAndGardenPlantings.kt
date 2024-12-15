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

import androidx.room.Embedded
import androidx.room.Relation

/**
 * `PlantAndGardenPlantings`는 [Plant]와 사용자의 [GardenPlanting] 간의 관계를 나타내는 데이터 클래스입니다.
 * - Room은 이 클래스를 사용하여 두 엔터티 간의 관계를 자동으로 처리하고 관련 데이터를 가져옵니다.
 *
 * @param plant [Plant] 엔터티를 포함합니다.
 * @param gardenPlantings [GardenPlanting] 엔터티의 리스트로, 특정 Plant에 연결된 사용자의 GardenPlanting 데이터를 포함합니다.
 */
data class PlantAndGardenPlantings(
    @Embedded
    val plant: Plant, // Plant 엔터티

    @Relation(
        parentColumn = "id", // Plant 테이블의 기본 키
        entityColumn = "plant_id" // GardenPlanting 테이블의 외래 키
    )
    val gardenPlantings: List<GardenPlanting> = emptyList() // 특정 Plant와 관련된 GardenPlanting 데이터 리스트
)
