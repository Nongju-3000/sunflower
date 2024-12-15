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

package com.google.samples.apps.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.samples.apps.sunflower.adapters.PlantAdapter
import com.google.samples.apps.sunflower.databinding.FragmentPlantListBinding
import com.google.samples.apps.sunflower.viewmodels.PlantListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * PlantListFragment는 식물 목록을 표시하는 UI를 제공하는 Fragment입니다.
 * - 사용자가 식물 목록을 필터링하거나 전체 목록을 확인할 수 있는 기능을 제공합니다.
 * - Dagger Hilt를 통해 ViewModel의 의존성을 주입받습니다.
 */
@AndroidEntryPoint
class PlantListFragment : Fragment() {

    // PlantListViewModel 객체를 생성하여 Fragment와 ViewModel을 연결
    private val viewModel: PlantListViewModel by viewModels()

    /**
     * Fragment의 뷰를 초기화하고 바인딩하는 메서드
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 데이터 바인딩 객체를 생성하여 XML 레이아웃과 연결
        val binding = FragmentPlantListBinding.inflate(inflater, container, false)

        // context가 null인 경우 루트 뷰를 반환하여 안전성을 보장
        context ?: return binding.root

        // PlantAdapter를 생성하고 RecyclerView에 연결
        val adapter = PlantAdapter()
        binding.plantList.adapter = adapter

        // LiveData와 UI를 구독하여 데이터 변경 시 RecyclerView 업데이트
        subscribeUi(adapter)

        // Fragment에서 옵션 메뉴를 사용할 수 있도록 설정
        setHasOptionsMenu(true)

        // 생성된 바인딩 객체의 루트 뷰 반환
        return binding.root
    }

    /**
     * 옵션 메뉴 생성
     * - 메뉴 XML 파일(menu_plant_list)을 기반으로 메뉴를 초기화
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    /**
     * 옵션 메뉴 아이템 선택 이벤트 처리
     * - 특정 아이템 선택 시 데이터 필터링을 업데이트
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
                // 필터링 동작 실행
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * LiveData를 구독하여 RecyclerView 업데이트
     * - ViewModel의 `plants` LiveData를 관찰하여 데이터 변경 시 어댑터에 전달
     */
    private fun subscribeUi(adapter: PlantAdapter) {
        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }
    }

    /**
     * 필터링 상태를 업데이트하는 메서드
     * - 필터링 활성화/비활성화에 따라 ViewModel의 데이터 상태를 변경
     */
    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                // 필터링 해제
                clearGrowZoneNumber()
            } else {
                // 특정 필터 값으로 필터링 설정 (예: 9번 성장 구역)
                setGrowZoneNumber(9)
            }
        }
    }
}
