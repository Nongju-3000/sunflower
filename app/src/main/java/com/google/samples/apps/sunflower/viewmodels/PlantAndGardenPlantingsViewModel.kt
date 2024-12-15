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

package com.google.samples.apps.sunflower.viewmodels

import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * PlantAndGardenPlantingsViewModel은 Plant와 GardenPlanting 데이터를 결합하여
 * UI에서 표시할 데이터를 준비하는 역할을 합니다.
 *
 * @param plantings Plant와 GardenPlanting 데이터를 포함하는 PlantAndGardenPlantings 객체
 */
class PlantAndGardenPlantingsViewModel(plantings: PlantAndGardenPlantings) {

    // Plant 객체와 GardenPlanting 리스트의 첫 번째 항목을 가져옵니다.
    // - Plant 객체는 null이 될 수 없음을 보장하기 위해 checkNotNull로 확인합니다.
    private val plant = checkNotNull(plantings.plant)
    private val gardenPlanting = plantings.gardenPlantings[0]

    /**
     * 마지막으로 물을 준 날짜를 포맷팅한 문자열
     * - GardenPlanting의 lastWateringDate를 "MMM d, yyyy" 형식으로 변환
     */
    val waterDateString: String = dateFormat.format(gardenPlanting.lastWateringDate.time)

    /**
     * 물 주기 (일 단위)
     * - Plant의 wateringInterval 속성을 반환
     */
    val wateringInterval
        get() = plant.wateringInterval

    /**
     * 식물의 이미지 URL
     * - Plant의 imageUrl 속성을 반환
     */
    val imageUrl
        get() = plant.imageUrl

    /**
     * 식물 이름
     * - Plant의 name 속성을 반환
     */
    val plantName
        get() = plant.name

    /**
     * 식물이 심어진 날짜를 포맷팅한 문자열
     * - GardenPlanting의 plantDate를 "MMM d, yyyy" 형식으로 변환
     */
    val plantDateString: String = dateFormat.format(gardenPlanting.plantDate.time)

    /**
     * 식물의 고유 ID
     * - Plant의 plantId 속성을 반환
     */
    val plantId
        get() = plant.plantId

    companion object {
        // 날짜를 "MMM d, yyyy" 형식으로 포맷팅하기 위한 SimpleDateFormat 객체
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }
}
