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

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

// Dagger Hilt를 사용해 의존성 주입이 가능한 Application으로 설정
@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    // WorkManager의 기본 설정을 제공
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            // 디버그 모드에서는 상세 로그(DEBUG), 릴리즈 모드에서는 간단한 로그(ERROR) 수준 설정
            .setMinimumLoggingLevel(
                if (BuildConfig.DEBUG) android.util.Log.DEBUG
                else android.util.Log.ERROR
            )
            .build()
}
