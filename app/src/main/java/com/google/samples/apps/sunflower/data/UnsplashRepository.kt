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

package com.google.samples.apps.sunflower.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.samples.apps.sunflower.api.UnsplashService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UnsplashRepository는 Unsplash API와 상호작용하는 데이터를 관리하는 클래스입니다.
 * - Paging 라이브러리를 사용하여 대규모 데이터셋을 효율적으로 관리합니다.
 * - Unsplash API에서 제공하는 사진 검색 데이터를 처리합니다.
 *
 * @param service Unsplash API와의 네트워크 요청을 처리하는 UnsplashService
 */
class UnsplashRepository @Inject constructor(private val service: UnsplashService) {

    /**
     * 검색어를 기반으로 사진 데이터를 PagingData 형태로 반환합니다.
     *
     * @param query 검색어
     * @return Flow 형태의 PagingData 스트림
     */
    fun getSearchResultStream(query: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false, // 자리 표시자를 비활성화
                pageSize = NETWORK_PAGE_SIZE // 한 페이지당 데이터 항목 수
            ),
            pagingSourceFactory = { UnsplashPagingSource(service, query) } // 데이터 로드를 위한 PagingSource 생성
        ).flow // Flow 형태로 데이터 반환
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25 // 네트워크 페이지 크기 상수
    }
}
