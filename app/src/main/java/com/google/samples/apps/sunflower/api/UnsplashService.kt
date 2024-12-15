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

package com.google.samples.apps.sunflower.api

import com.google.samples.apps.sunflower.BuildConfig
import com.google.samples.apps.sunflower.data.UnsplashSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Unsplash API와 통신하기 위한 서비스 인터페이스
 * - Retrofit을 사용하여 Unsplash API에서 사진 데이터를 가져옵니다.
 */
interface UnsplashService {

    /**
     * Unsplash API의 "search/photos" 엔드포인트와 매핑되는 메서드
     * - 사진 검색 결과를 반환합니다.
     *
     * @param query 검색어
     * @param page 페이지 번호
     * @param perPage 페이지당 결과 개수
     * @param clientId Unsplash API 인증 키
     * @return UnsplashSearchResponse 형태의 검색 결과
     */
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String = BuildConfig.UNSPLASH_ACCESS_KEY // 기본값으로 빌드 구성의 API 키 사용
    ): UnsplashSearchResponse

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/" // Unsplash API 기본 URL

        /**
         * UnsplashService의 인스턴스를 생성합니다.
         * - OkHttpClient 및 Retrofit 설정 포함
         *
         * @return UnsplashService 인스턴스
         */
        fun create(): UnsplashService {
            // HTTP 로깅 인터셉터 설정 (기본 로그 수준)
            val logger = HttpLoggingInterceptor().apply { level = Level.BASIC }

            // OkHttpClient에 로깅 인터셉터 추가
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            // Retrofit 빌더를 사용해 UnsplashService 생성
            return Retrofit.Builder()
                .baseUrl(BASE_URL) // 기본 URL 설정
                .client(client) // OkHttpClient 설정
                .addConverterFactory(GsonConverterFactory.create()) // Gson을 사용한 JSON 변환기 추가
                .build()
                .create(UnsplashService::class.java)
        }
    }
}
