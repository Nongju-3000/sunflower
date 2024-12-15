/*
 * Copyright 2020 Google LLC
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.samples.apps.sunflower.adapters.GalleryAdapter
import com.google.samples.apps.sunflower.databinding.FragmentGalleryBinding
import com.google.samples.apps.sunflower.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// Dagger Hilt를 사용해 의존성 주입이 가능한 Fragment로 설정
@AndroidEntryPoint
class GalleryFragment : Fragment() {

    // GalleryAdapter를 생성하여 RecyclerView에 데이터 바인딩
    private val adapter = GalleryAdapter()

    // Safe Args로 전달된 인자를 관리
    private val args: GalleryFragmentArgs by navArgs()

    // Coroutine Job을 관리하여 이전 작업 취소 가능
    private var searchJob: Job? = null

    // GalleryViewModel을 Fragment 생명주기와 연결
    private val viewModel: GalleryViewModel by viewModels()

    // Fragment의 뷰를 생성하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 데이터 바인딩 객체 생성
        val binding = FragmentGalleryBinding.inflate(inflater, container, false)

        // context가 null이면 root 뷰를 반환
        context ?: return binding.root

        // RecyclerView에 어댑터 연결
        binding.photoList.adapter = adapter

        // 전달받은 식물 이름으로 검색 수행
        search(args.plantName)

        // Toolbar의 뒤로가기 버튼 설정
        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        return binding.root
    }

    // 검색 작업 수행 메서드
    private fun search(query: String) {
        // 이전 검색 작업이 있다면 취소
        searchJob?.cancel()

        // 새로운 검색 작업을 Coroutine으로 실행
        searchJob = lifecycleScope.launch {
            // ViewModel에서 검색 결과를 Flow 형태로 수집
            viewModel.searchPictures(query).collectLatest {
                // Adapter에 데이터 전달
                adapter.submitData(it)
            }
        }
    }
}
