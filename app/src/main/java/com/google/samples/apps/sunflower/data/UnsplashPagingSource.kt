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

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.samples.apps.sunflower.api.UnsplashService

// Unsplash API 호출 시 시작 페이지 번호
private const val UNSPLASH_STARTING_PAGE_INDEX = 1

/**
 * UnsplashPagingSource는 Paging 3 라이브러리에서 사용되는 커스텀 PagingSource입니다.
 * - Unsplash API에서 검색된 사진 데이터를 페이징 처리하여 로드합니다.
 *
 * @param service Unsplash API 호출을 처리하는 서비스
 * @param query 검색에 사용될 문자열
 */
class UnsplashPagingSource(
    private val service: UnsplashService, // Unsplash API 서비스
    private val query: String // 검색어
) : PagingSource<Int, UnsplashPhoto>() {

    /**
     * 페이징 데이터를 로드하는 메서드
     *
     * @param params 데이터 로드 파라미터 (key와 loadSize 포함)
     * @return LoadResult 페이징 데이터 로드 결과
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        // 현재 페이지를 가져오거나 기본 시작 페이지로 설정
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            // Unsplash API를 호출하여 데이터를 가져옴
            val response = service.searchPhotos(query, page, params.loadSize)
            val photos = response.results

            // 데이터 로드 결과 반환
            LoadResult.Page(
                data = photos, // 현재 페이지의 데이터
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1, // 이전 페이지 키
                nextKey = if (page == response.totalPages) null else page + 1 // 다음 페이지 키
            )
        } catch (exception: Exception) {
            // 예외 발생 시 오류 결과 반환
            LoadResult.Error(exception)
        }
    }

    /**
     * 페이징 새로고침에 사용되는 키를 반환
     *
     * @param state PagingState 객체
     * @return 새로고침 시 사용할 키
     */
    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // 현재 사용자가 보고 있는 항목에 가장 가까운 페이지의 이전 키 반환
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
