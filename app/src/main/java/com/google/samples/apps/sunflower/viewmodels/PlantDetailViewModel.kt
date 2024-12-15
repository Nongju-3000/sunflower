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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.sunflower.BuildConfig
import com.google.samples.apps.sunflower.PlantDetailFragment
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * PlantDetailViewModel은 [PlantDetailFragment]에서 사용되는 ViewModel입니다.
 * - 식물 상세 정보를 제공하고, 정원에 식물을 추가하는 기능을 관리합니다.
 * - Dagger Hilt를 통해 필요한 Repository를 주입받습니다.
 */
@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, // ViewModel 상태 저장 핸들
    plantRepository: PlantRepository, // 식물 데이터를 관리하는 Repository
    private val gardenPlantingRepository: GardenPlantingRepository // 정원 데이터를 관리하는 Repository
) : ViewModel() {

    // 저장된 상태 핸들에서 plantId를 가져옵니다.
    // plantId는 식물의 고유 식별자입니다.
    val plantId: String = savedStateHandle.get<String>(PLANT_ID_SAVED_STATE_KEY)!!

    // 식물이 이미 정원에 추가되었는지 여부를 나타내는 LiveData
    val isPlanted = gardenPlantingRepository.isPlanted(plantId).asLiveData()

    // 식물 정보를 제공하는 LiveData
    val plant = plantRepository.getPlant(plantId).asLiveData()

    /**
     * 정원에 식물을 추가합니다.
     * - 코루틴을 사용하여 비동기 작업으로 Repository에 요청을 보냅니다.
     */
    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }

    /**
     * Unsplash API 키가 유효한지 확인합니다.
     * - 빌드 구성에서 `UNSPLASH_ACCESS_KEY` 값이 "null"인지 검사하여 결정합니다.
     * @return 유효한 API 키 여부
     */
    fun hasValidUnsplashKey() = (BuildConfig.UNSPLASH_ACCESS_KEY != "null")

    companion object {
        // SavedStateHandle에 사용되는 식물 ID 키
        private const val PLANT_ID_SAVED_STATE_KEY = "plantId"
    }
}
