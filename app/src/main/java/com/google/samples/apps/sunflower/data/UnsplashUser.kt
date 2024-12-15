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

import com.google.gson.annotations.SerializedName

/**
 * UnsplashUser는 Unsplash API에서 반환되는 사용자 데이터를 나타내는 데이터 클래스입니다.
 *
 * 이 클래스는 Unsplash 사용자 정보를 저장하며, 필요한 필드만 포함하고 있습니다.
 * API에서 반환되는 모든 필드를 사용하지 않고, 프로젝트에서 필요한 필드만 정의했습니다.
 *
 * API의 전체 필드 목록은 [Unsplash API 문서](https://unsplash.com/documentation#get-a-users-public-profile)에서 확인할 수 있습니다.
 *
 * @param name 사용자 이름
 * @param username 사용자 이름 (고유한 식별자)
 */
data class UnsplashUser(
    @field:SerializedName("name") val name: String, // API에서 반환되는 사용자 이름
    @field:SerializedName("username") val username: String // API에서 반환되는 고유 사용자 이름
) {
    /**
     * Unsplash 사용자 프로필에 대한 참조 URL을 생성
     * - Unsplash의 사용자 페이지로 리디렉션하며, 특정 프로젝트(이 경우 Sunflower)를 언급하기 위한 쿼리 매개변수를 포함
     * - @return 사용자의 Unsplash 프로필 URL
     */
    val attributionUrl: String
        get() {
            return "https://unsplash.com/$username?utm_source=sunflower&utm_medium=referral"
        }
}
