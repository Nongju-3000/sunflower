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

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * GardenPlantingListViewModel은 사용자의 정원에 심어진 식물 목록을 관리하는 ViewModel입니다.
 * - Dagger Hilt를 통해 GardenPlantingRepository를 주입받아 데이터 소스와 UI를 연결합니다.
 */
@HiltViewModel
class GardenPlantingListViewModel @Inject internal constructor(
    gardenPlantingRepository: GardenPlantingRepository // 정원 데이터를 관리하는 Repository
) : ViewModel() {

    /**
     * 심어진 식물과 그 관련 정보를 포함하는 LiveData
     * - GardenPlantingRepository에서 Flow 형태로 데이터를 가져오고, LiveData로 변환하여 UI에서 관찰 가능하도록 제공합니다.
     */
    val plantAndGardenPlantings: LiveData<List<PlantAndGardenPlantings>> =
        gardenPlantingRepository.getPlantedGardens().asLiveData()
}
