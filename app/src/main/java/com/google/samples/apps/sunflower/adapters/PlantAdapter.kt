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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.HomeViewPagerFragmentDirections
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.databinding.ListItemPlantBinding

/**
 * RecyclerView의 어댑터 클래스
 * - [PlantListFragment]에서 사용됩니다.
 * - 식물 목록을 표시하며, 각 항목 클릭 시 상세 페이지로 이동합니다.
 */
class PlantAdapter : ListAdapter<Plant, RecyclerView.ViewHolder>(PlantDiffCallback()) {

    /**
     * ViewHolder를 생성합니다.
     *
     * @param parent 부모 ViewGroup
     * @param viewType 뷰 타입
     * @return 생성된 ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(
            ListItemPlantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * 데이터를 ViewHolder에 바인딩합니다.
     *
     * @param holder ViewHolder
     * @param position 항목의 위치
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as PlantViewHolder).bind(plant)
    }

    /**
     * ViewHolder 클래스
     * - 식물 항목을 바인딩하고 클릭 이벤트를 처리합니다.
     */
    class PlantViewHolder(
        private val binding: ListItemPlantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            // 항목 클릭 리스너를 설정합니다.
            binding.setClickListener {
                binding.plant?.let { plant ->
                    navigateToPlant(plant, it)
                }
            }
        }

        /**
         * 특정 식물 항목의 상세 페이지로 이동합니다.
         *
         * @param plant 클릭된 Plant 객체
         * @param view 클릭된 View
         */
        private fun navigateToPlant(
            plant: Plant,
            view: View
        ) {
            val direction =
                HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                    plant.plantId
                )
            view.findNavController().navigate(direction)
        }

        /**
         * Plant 데이터를 View와 바인딩합니다.
         *
         * @param item 바인딩할 Plant 객체
         */
        fun bind(item: Plant) {
            binding.apply {
                plant = item
                executePendingBindings()
            }
        }
    }
}

/**
 * DiffUtil.ItemCallback 구현 클래스
 * - RecyclerView의 성능을 최적화합니다.
 */
private class PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {

    /**
     * 두 항목이 동일한지 확인합니다.
     *
     * @param oldItem 이전 항목
     * @param newItem 새로운 항목
     * @return 두 항목의 ID가 동일한지 여부
     */
    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.plantId == newItem.plantId
    }

    /**
     * 두 항목의 내용이 동일한지 확인합니다.
     *
     * @param oldItem 이전 항목
     * @param newItem 새로운 항목
     * @return 두 항목이 동일한지 여부
     */
    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }
}
