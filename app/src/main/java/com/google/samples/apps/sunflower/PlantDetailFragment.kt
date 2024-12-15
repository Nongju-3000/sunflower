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

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.samples.apps.sunflower.PlantDetailFragment.Callback
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.databinding.FragmentPlantDetailBinding
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * PlantDetailFragment는 특정 식물의 세부 정보를 보여주는 화면을 구현하는 Fragment입니다.
 * - 사용자가 식물을 추가하거나 갤러리로 이동할 수 있는 기능을 제공합니다.
 * - Dagger Hilt를 통해 의존성 주입을 지원합니다.
 */
@AndroidEntryPoint
class PlantDetailFragment : Fragment() {

    // PlantDetailViewModel을 생성하여 Fragment와 생명주기를 공유
    private val plantDetailViewModel: PlantDetailViewModel by viewModels()

    /**
     * Fragment의 UI를 초기화하고 바인딩하는 메서드
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // DataBinding 객체를 생성하여 Fragment와 XML 레이아웃을 결합
        val binding = DataBindingUtil.inflate<FragmentPlantDetailBinding>(
            inflater,
            R.layout.fragment_plant_detail,
            container,
            false
        ).apply {
            // ViewModel과 생명주기 소유자를 바인딩하여 UI와 데이터 동기화
            viewModel = plantDetailViewModel
            lifecycleOwner = viewLifecycleOwner

            // 사용자 입력에 따라 식물을 추가하는 콜백을 설정
            callback = Callback { plant ->
                plant?.let {
                    hideAppBarFab(fab) // FloatingActionButton 숨기기
                    plantDetailViewModel.addPlantToGarden() // ViewModel에 식물 추가 요청
                    Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG).show() // 알림 표시
                }
            }

            // 갤러리로 이동하는 버튼 클릭 리스너 설정
            galleryNav.setOnClickListener { navigateToGallery() }

            var isToolbarShown = false

            // 스크롤 변화에 따라 툴바의 상태를 업데이트
            plantDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                    val shouldShowToolbar = scrollY > toolbar.height // 툴바를 표시해야 하는지 확인
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar
                        appbar.isActivated = shouldShowToolbar // 툴바의 그림자 상태 업데이트
                        toolbarLayout.isTitleEnabled = shouldShowToolbar // 툴바 제목 활성화
                    }
                }
            )

            // 툴바 네비게이션 버튼 클릭 시 뒤로 가기
            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

            // 툴바 메뉴 클릭 리스너 설정
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_share -> {
                        createShareIntent() // 공유 기능 실행
                        true
                    }
                    else -> false
                }
            }
        }

        // Fragment가 옵션 메뉴를 가질 수 있도록 설정
        setHasOptionsMenu(true)

        // 생성된 바인딩 객체의 루트 뷰 반환
        return binding.root
    }

    /**
     * 갤러리로 이동하는 메서드
     * - 현재 식물 이름을 Safe Args로 전달하여 GalleryFragment로 이동합니다.
     */
    private fun navigateToGallery() {
        plantDetailViewModel.plant.value?.let { plant ->
            val direction =
                PlantDetailFragmentDirections.actionPlantDetailFragmentToGalleryFragment(plant.name)
            findNavController().navigate(direction)
        }
    }

    /**
     * 공유 Intent를 생성하고 실행하는 메서드
     * - 사용자 선택에 따라 텍스트를 공유할 수 있는 앱으로 데이터를 전달합니다.
     */
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        // 공유할 텍스트 생성
        val shareText = plantDetailViewModel.plant.value?.let { plant ->
            getString(R.string.share_text_plant, plant.name)
        } ?: ""

        // 공유 Intent 생성
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(shareText) // 공유할 텍스트 설정
            .setType("text/plain") // 텍스트 공유의 MIME 타입 설정
            .createChooserIntent() // 앱 선택창 포함 Intent 생성
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK) // 플래그 설정
        startActivity(shareIntent) // Intent 실행
    }

    /**
     * FloatingActionButton의 자동 숨김 동작을 비활성화하고 명시적으로 숨깁니다.
     * - FAB는 AppBarLayout의 스크롤 위치에 따라 기본적으로 표시되거나 숨겨집니다.
     * - 이 메서드는 클릭 시 FAB를 명시적으로 숨깁니다.
     */
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false // 자동 숨김 비활성화
        fab.hide() // FAB 숨기기
    }

    /**
     * Plant 객체를 추가하기 위한 콜백 인터페이스
     */
    fun interface Callback {
        fun add(plant: Plant?)
    }
}
