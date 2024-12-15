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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.adapters

import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.samples.apps.sunflower.R

/**
 * 데이터 바인딩 어댑터 함수 정의
 * - XML에서 특정 속성으로 데이터를 직접 바인딩하도록 설정
 * - 재사용 가능한 함수로 코드 간결성과 유지보수성을 높임
 */

/**
 * 이미지 URL을 ImageView에 로드
 * - Glide를 사용하여 비동기로 이미지 로드
 *
 * @param view 대상 ImageView
 * @param imageUrl 로드할 이미지의 URL
 */
@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl) // 이미지 URL 로드
            .transition(DrawableTransitionOptions.withCrossFade()) // 부드러운 페이드 전환
            .into(view) // ImageView에 로드
    }
}

/**
 * FloatingActionButton의 표시/숨기기 설정
 *
 * @param view 대상 FloatingActionButton
 * @param isGone 숨길지 여부 (true: 숨김, false: 표시)
 */
@BindingAdapter("isFabGone")
fun bindIsFabGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.hide() // 숨김
    } else {
        view.show() // 표시
    }
}

/**
 * HTML 텍스트를 TextView에 렌더링
 * - HTML 태그를 포함한 문자열을 TextView에 표시
 *
 * @param view 대상 TextView
 * @param description HTML로 렌더링할 문자열
 */
@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT) // HTML 렌더링
        view.movementMethod = LinkMovementMethod.getInstance() // 링크 클릭 가능 설정
    } else {
        view.text = "" // 내용 없으면 빈 문자열 설정
    }
}

/**
 * 물 주기 텍스트를 설정
 * - 다국어 지원을 위해 `quantityString`을 사용하여 적절한 복수형 리소스를 설정
 *
 * @param textView 대상 TextView
 * @param wateringInterval 물 주기 (일 단위)
 */
@BindingAdapter("wateringText")
fun bindWateringText(textView: TextView, wateringInterval: Int) {
    val resources = textView.context.resources
    val quantityString = resources.getQuantityString(
        R.plurals.watering_needs_suffix, // 복수형 리소스 ID
        wateringInterval, // 복수형 수량
        wateringInterval // 형식 문자열에 삽입할 값
    )

    textView.text = quantityString // 설정된 문자열을 TextView에 적용
}
