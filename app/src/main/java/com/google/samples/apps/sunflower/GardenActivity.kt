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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.samples.apps.sunflower.databinding.ActivityGardenBinding
import dagger.hilt.android.AndroidEntryPoint

// Dagger Hilt를 사용해 의존성 주입이 가능한 Activity로 설정
@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {

    // Activity 생명 주기에서 onCreate 메서드 실행
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 데이터 바인딩을 사용해 레이아웃 XML 파일(activity_garden.xml)을 설정
        // ActivityGardenBinding 객체를 생성하여 이 Activity에서 UI 요소를 쉽게 접근 가능하게 함
        setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
    }
}
