/*
 * Copyright 2019 Google LLC
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

package com.google.samples.apps.sunflower.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.samples.apps.sunflower.GardenFragment
import com.google.samples.apps.sunflower.PlantListFragment

// ViewPager의 페이지 인덱스를 정의
const val MY_GARDEN_PAGE_INDEX = 0 // 'My Garden' 페이지 인덱스
const val PLANT_LIST_PAGE_INDEX = 1 // 'Plant List' 페이지 인덱스

/**
 * `SunflowerPagerAdapter`는 ViewPager2를 위한 어댑터입니다.
 * - 페이지 인덱스와 해당 Fragment 간의 매핑을 관리합니다.
 * - 각 페이지에 대해 적절한 Fragment를 생성합니다.
 *
 * @param fragment 부모 Fragment로, ViewPager2와 어댑터를 연결합니다.
 */
class SunflowerPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * ViewPager의 페이지 인덱스와 해당 Fragment 생성자를 매핑합니다.
     * - MY_GARDEN_PAGE_INDEX는 GardenFragment와 연결됩니다.
     * - PLANT_LIST_PAGE_INDEX는 PlantListFragment와 연결됩니다.
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MY_GARDEN_PAGE_INDEX to { GardenFragment() }, // 'My Garden' Fragment
        PLANT_LIST_PAGE_INDEX to { PlantListFragment() } // 'Plant List' Fragment
    )

    /**
     * ViewPager2의 총 페이지 수를 반환합니다.
     * - `tabFragmentsCreators`의 크기를 기반으로 계산됩니다.
     *
     * @return 페이지 수
     */
    override fun getItemCount() = tabFragmentsCreators.size

    /**
     * 주어진 위치(position)에 해당하는 Fragment를 생성합니다.
     *
     * @param position ViewPager2에서의 페이지 위치
     * @return 생성된 Fragment
     * @throws IndexOutOfBoundsException 잘못된 인덱스에 대한 예외
     */
    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke()
            ?: throw IndexOutOfBoundsException("Invalid position: $position")
    }
}
