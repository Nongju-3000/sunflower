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

package com.google.samples.apps.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.samples.apps.sunflower.adapters.MY_GARDEN_PAGE_INDEX
import com.google.samples.apps.sunflower.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter
import com.google.samples.apps.sunflower.databinding.FragmentViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint

// Dagger Hilt를 사용해 의존성 주입이 가능한 Fragment로 설정
@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {

    // Fragment의 뷰를 생성하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 데이터 바인딩 객체 생성
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        // TabLayout과 ViewPager2 연결
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        // SunflowerPagerAdapter를 ViewPager에 설정
        viewPager.adapter = SunflowerPagerAdapter(this)

        // TabLayout과 ViewPager를 동기화하며 각 탭에 아이콘과 제목 설정
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position)) // 각 탭의 아이콘 설정
            tab.text = getTabTitle(position) // 각 탭의 제목 설정
        }.attach() // 설정 완료 후 탭과 ViewPager 연결

        // Toolbar를 AppCompatActivity의 ActionBar로 설정
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    // 각 탭의 아이콘을 반환
    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector // "My Garden" 탭 아이콘
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector // "Plant List" 탭 아이콘
            else -> throw IndexOutOfBoundsException() // 유효하지 않은 인덱스 처리
        }
    }

    // 각 탭의 제목을 반환
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title) // "My Garden" 제목
            PLANT_LIST_PAGE_INDEX -> getString(R.string.plant_list_title) // "Plant List" 제목
            else -> null // 유효하지 않은 인덱스 처리
        }
    }
}
