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

package com.google.samples.apps.sunflower.di

import com.google.samples.apps.sunflower.api.UnsplashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * NetworkModule은 UnsplashService와 같은 네트워크 관련 의존성을 제공하는 Dagger Hilt 모듈입니다.
 */
@InstallIn(SingletonComponent::class) // SingletonComponent에 의존성을 설치하여 애플리케이션 전역에서 사용 가능하게 설정
@Module
class NetworkModule {

    /**
     * UnsplashService를 제공하는 메서드
     * - @Provides와 @Singleton 어노테이션을 사용하여 Dagger Hilt가 이 객체를 생성하고 관리합니다.
     *
     * @return UnsplashService의 인스턴스
     */
    @Singleton
    @Provides
    fun provideUnsplashService(): UnsplashService {
        return UnsplashService.create() // UnsplashService의 정적 팩토리 메서드로 인스턴스 생성
    }
}
