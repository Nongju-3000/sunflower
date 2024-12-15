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

import android.content.Context
import com.google.samples.apps.sunflower.data.AppDatabase
import com.google.samples.apps.sunflower.data.GardenPlantingDao
import com.google.samples.apps.sunflower.data.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DatabaseModule은 데이터베이스와 관련된 의존성을 제공하는 Dagger Hilt 모듈입니다.
 */
@InstallIn(SingletonComponent::class) // SingletonComponent에 의존성을 설치하여 애플리케이션 전역에서 사용 가능
@Module
class DatabaseModule {

    /**
     * AppDatabase를 제공하는 메서드
     * - @Provides와 @Singleton 어노테이션을 사용하여 Dagger Hilt가 이 객체를 생성하고 관리합니다.
     *
     * @param context 애플리케이션 컨텍스트
     * @return AppDatabase 인스턴스
     */
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context) // 싱글톤으로 데이터베이스 인스턴스 생성
    }

    /**
     * PlantDao를 제공하는 메서드
     * - AppDatabase에서 PlantDao를 가져옵니다.
     *
     * @param appDatabase AppDatabase 인스턴스
     * @return PlantDao 인스턴스
     */
    @Provides
    fun providePlantDao(appDatabase: AppDatabase): PlantDao {
        return appDatabase.plantDao()
    }

    /**
     * GardenPlantingDao를 제공하는 메서드
     * - AppDatabase에서 GardenPlantingDao를 가져옵니다.
     *
     * @param appDatabase AppDatabase 인스턴스
     * @return GardenPlantingDao 인스턴스
     */
    @Provides
    fun provideGardenPlantingDao(appDatabase: AppDatabase): GardenPlantingDao {
        return appDatabase.gardenPlantingDao()
    }
}
