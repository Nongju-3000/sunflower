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

package com.google.samples.apps.sunflower.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.samples.apps.sunflower.data.UnsplashPhoto
import com.google.samples.apps.sunflower.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * GalleryViewModel은 Unsplash API를 통해 사진 데이터를 관리하고,
 * Paging 라이브러리를 사용하여 대량의 데이터를 효율적으로 처리합니다.
 */
@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository // Unsplash API 데이터를 관리하는 Repository
) : ViewModel() {

    // 현재 검색어를 저장
    private var currentQueryValue: String? = null

    // 현재 검색 결과를 저장 (PagingData 스트림)
    private var currentSearchResult: Flow<PagingData<UnsplashPhoto>>? = null

    /**
     * 사진을 검색하고 결과를 PagingData 형태로 반환
     * @param queryString 검색어
     * @return 검색 결과 스트림 (PagingData)
     */
    fun searchPictures(queryString: String): Flow<PagingData<UnsplashPhoto>> {
        // 검색어를 업데이트
        currentQueryValue = queryString

        // UnsplashRepository에서 검색 스트림 가져오기
        val newResult: Flow<PagingData<UnsplashPhoto>> =
            repository.getSearchResultStream(queryString)
                .cachedIn(viewModelScope) // PagingData를 ViewModel의 생명주기에 캐싱

        // 현재 검색 결과 업데이트
        currentSearchResult = newResult

        // 새로운 검색 결과 반환
        return newResult
    }
}
