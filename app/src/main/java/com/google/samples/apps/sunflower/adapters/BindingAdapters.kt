/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.google.samples.apps.sunflower.adapters

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * 데이터 바인딩 어댑터 함수
 * - XML에서 "isGone" 속성을 통해 View의 가시성을 설정합니다.
 *
 * @param view 대상 View
 * @param isGone true이면 GONE, false이면 VISIBLE로 설정
 */
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE // View를 숨깁니다 (공간 차지 안 함)
    } else {
        View.VISIBLE // View를 표시합니다
    }
}
