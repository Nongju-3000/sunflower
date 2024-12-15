/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.google.samples.apps.sunflower.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.PlantAndGardenPlantings
import com.google.samples.apps.sunflower.databinding.ListItemGardenPlantingBinding
import com.google.samples.apps.sunflower.viewmodels.PlantAndGardenPlantingsViewModel

/**
 * GardenPlantingAdapter는 RecyclerView를 위한 어댑터입니다.
 * - 사용자의 정원에 심어진 식물을 표시합니다.
 * - 각 항목을 클릭하면 상세 화면으로 이동합니다.
 */
class GardenPlantingAdapter :
    ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder>(
        GardenPlantDiffCallback()
    ) {

    /**
     * ViewHolder를 생성합니다.
     *
     * @param parent 부모 ViewGroup
     * @param viewType 뷰 타입
     * @return 생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_garden_planting, // 레이아웃 XML 파일
                parent,
                false
            )
        )
    }

    /**
     * ViewHolder에 데이터를 바인딩합니다.
     *
     * @param holder ViewHolder
     * @param position 항목의 위치
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder 클래스
     * - 각 항목에 데이터를 바인딩하고 클릭 이벤트를 처리합니다.
     */
    class ViewHolder(
        private val binding: ListItemGardenPlantingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            // 항목 클릭 이벤트 리스너 설정
            binding.setClickListener { view ->
                binding.viewModel?.plantId?.let { plantId ->
                    navigateToPlant(plantId, view)
                }
            }
        }

        /**
         * 특정 식물의 상세 화면으로 이동합니다.
         *
         * @param plantId 클릭된 식물의 ID
         * @param view 클릭된 View
         */
        private fun navigateToPlant(plantId: String, view: View) {
            val direction = HomeViewPagerFragmentDirections
                .actionViewPagerFragmentToPlantDetailFragment(plantId)
            view.findNavController().navigate(direction) // Navigation Component를 통해 이동
        }

        /**
         * 데이터를 View와 바인딩합니다.
         *
         * @param plantings 바인딩할 PlantAndGardenPlantings 객체
         */
        fun bind(plantings: PlantAndGardenPlantings) {
            with(binding) {
                viewModel = PlantAndGardenPlantingsViewModel(plantings) // ViewModel 생성 및 바인딩
                executePendingBindings() // 대기 중인 바인딩을 즉시 실행
            }
        }
    }
}

/**
 * DiffUtil.ItemCallback 구현
 * - RecyclerView에서 데이터 변경 사항을 효율적으로 처리하기 위한 클래스
 */
private class GardenPlantDiffCallback : DiffUtil.ItemCallback<PlantAndGardenPlantings>() {

    /**
     * 두 항목의 ID가 동일한지 확인합니다.
     *
     * @param oldItem 이전 항목
     * @param newItem 새로운 항목
     * @return 두 항목의 ID가 동일한지 여부
     */
    override fun areItemsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant.plantId == newItem.plant.plantId
    }

    /**
     * 두 항목의 내용이 동일한지 확인합니다.
     *
     * @param oldItem 이전 항목
     * @param newItem 새로운 항목
     * @return 두 항목의 내용이 동일한지 여부
     */
    override fun areContentsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant == newItem.plant
    }
}
