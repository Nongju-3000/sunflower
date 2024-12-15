/*
 * Copyright 2020 Google LLC
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

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.GalleryFragment
import com.google.samples.apps.sunflower.adapters.GalleryAdapter.GalleryViewHolder
import com.google.samples.apps.sunflower.data.UnsplashPhoto
import com.google.samples.apps.sunflower.databinding.ListItemPhotoBinding

/**
 * RecyclerView 어댑터
 * - [GalleryFragment]에서 사용됩니다.
 * - Unsplash에서 가져온 사진 데이터를 페이징 처리하여 RecyclerView에 표시합니다.
 */
class GalleryAdapter : PagingDataAdapter<UnsplashPhoto, GalleryViewHolder>(GalleryDiffCallback()) {

    /**
     * ViewHolder를 생성합니다.
     *
     * @param parent 부모 ViewGroup
     * @param viewType 뷰 타입
     * @return GalleryViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            ListItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * ViewHolder에 데이터를 바인딩합니다.
     *
     * @param holder GalleryViewHolder
     * @param position 항목의 위치
     */
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }

    /**
     * ViewHolder 클래스
     * - 각 사진 항목에 데이터를 바인딩하고 클릭 이벤트를 처리합니다.
     */
    class GalleryViewHolder(
        private val binding: ListItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            // 항목 클릭 이벤트 리스너 설정
            binding.setClickListener { view ->
                binding.photo?.let { photo ->
                    // 사진을 클릭하면 사용자 URL을 웹 브라우저에서 엽니다.
                    val uri = Uri.parse(photo.user.attributionUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    view.context.startActivity(intent)
                }
            }
        }

        /**
         * UnsplashPhoto 데이터를 View와 바인딩합니다.
         *
         * @param item 바인딩할 UnsplashPhoto 객체
         */
        fun bind(item: UnsplashPhoto) {
            binding.apply {
                photo = item
                executePendingBindings() // 대기 중인 바인딩을 즉시 실행
            }
        }
    }
}

/**
 * DiffUtil.ItemCallback 구현
 * - RecyclerView에서 데이터 변경 사항을 효율적으로 처리하기 위한 클래스
 */
private class GalleryDiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {

    /**
     * 두 항목의 ID가 동일한지 확인합니다.
     *
     * @param oldItem 이전 항목
     * @param newItem 새로운 항목
     * @return 두 항목의 ID가 동일한지 여부
     */
    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * 두 항목의 내용이 동일한지 확인합니다.
     *
     * @param oldItem 이전 항목
     * @param newItem 새로운 항목
     * @return 두 항목이 동일한지 여부
     */
    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem == newItem
    }
}
