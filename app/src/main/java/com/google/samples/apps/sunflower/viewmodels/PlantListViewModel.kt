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
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.data.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * PlantListViewModel은 [PlantListFragment]를 위한 ViewModel로,
 * 식물 데이터를 관리하고 UI와 데이터 소스를 연결합니다.
 * - Dagger Hilt를 통해 의존성을 주입받습니다.
 */
@HiltViewModel
class PlantListViewModel @Inject internal constructor(
    plantRepository: PlantRepository, // 식물 데이터를 제공하는 Repository
    private val savedStateHandle: SavedStateHandle // ViewModel 상태를 저장하는 핸들
) : ViewModel() {

    // 성장 구역 정보를 관리하는 StateFlow
    private val growZone: MutableStateFlow<Int> = MutableStateFlow(
        savedStateHandle.get(GROW_ZONE_SAVED_STATE_KEY) ?: NO_GROW_ZONE
    )

    private val searchQuery: MutableStateFlow<String> = MutableStateFlow("")

    // 성장 구역에 따라 필터링된 식물 데이터를 제공
    val plants: LiveData<List<Plant>> = growZone
        .flatMapLatest { zone ->
            searchQuery.flatMapLatest { query ->
                if (zone == NO_GROW_ZONE) {
                    plantRepository.getPlantsByQuery(query) // 검색 쿼리 적용
                } else {
                    plantRepository.getPlantsWithGrowZoneAndQuery(zone, query) // 성장 구역과 검색 적용
                }
            }
        }
        .asLiveData()

    init {
        /**
         * 성장 구역(growZone)이 변경될 때마다 SavedStateHandle에 저장
         * - SavedStateHandle은 상태를 저장하여 프로세스 종료 후에도 데이터 복원 가능
         */
        viewModelScope.launch {
            growZone.collect { newGrowZone ->
                savedStateHandle.set(GROW_ZONE_SAVED_STATE_KEY, newGrowZone)
            }
        }
    }

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    /**
     * 특정 성장 구역 번호를 설정
     * @param num: 설정할 성장 구역 번호
     */
    fun setGrowZoneNumber(num: Int) {
        growZone.value = num
    }

    /**
     * 성장 구역 번호를 초기화 (필터링 해제)
     */
    fun clearGrowZoneNumber() {
        growZone.value = NO_GROW_ZONE
    }

    /**
     * 현재 필터링 상태를 확인
     * @return 필터링 상태 (true: 필터링됨, false: 필터링되지 않음)
     */
    fun isFiltered() = growZone.value != NO_GROW_ZONE


    companion object {
        // 성장 구역이 설정되지 않은 상태를 나타내는 상수
        private const val NO_GROW_ZONE = -1

        // SavedStateHandle에 사용되는 키
        private const val GROW_ZONE_SAVED_STATE_KEY = "GROW_ZONE_SAVED_STATE_KEY"
    }
}
