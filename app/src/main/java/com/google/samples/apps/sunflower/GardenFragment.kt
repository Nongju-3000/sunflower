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
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunflower.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel
import dagger.hilt.android.AndroidEntryPoint

// Dagger Hilt를 사용해 의존성 주입이 가능한 Fragment로 설정
@AndroidEntryPoint
class GardenFragment : Fragment() {

    // View Binding 객체. Fragment의 레이아웃 요소에 쉽게 접근할 수 있도록 함
    private lateinit var binding: FragmentGardenBinding

    // ViewModel 객체를 생성. Fragment 생명주기와 연결된 ViewModel 인스턴스를 제공
    private val viewModel: GardenPlantingListViewModel by viewModels()

    // Fragment의 뷰를 생성하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // FragmentGarden 레이아웃을 바인딩 객체로 inflate
        binding = FragmentGardenBinding.inflate(inflater, container, false)

        // RecyclerView 어댑터 설정
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        // "Add Plant" 버튼 클릭 시 식물 목록 페이지로 이동
        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        // UI를 LiveData와 구독하여 변경 사항에 반응
        subscribeUi(adapter, binding)

        // 생성된 뷰를 반환
        return binding.root
    }

    // LiveData 관찰 및 UI 업데이트
    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            // 데이터가 비어있는지 확인하고 상태를 바인딩 객체에 설정
            binding.hasPlantings = result.isNotEmpty()

            // RecyclerView 어댑터에 데이터 전달
            adapter.submitList(result) {
                // 화면이 완전히 렌더링된 시점을 Activity에 보고 (성능 로깅 목적)
                activity?.reportFullyDrawn()
            }
        }
    }

    // 식물 목록 페이지로 이동 (ViewPager2를 이용해 특정 페이지로 이동)
    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }
}
