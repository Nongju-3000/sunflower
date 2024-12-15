/*
 * Copyright 2019 Google LLC
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

package com.google.samples.apps.sunflower.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import com.google.android.material.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.ShapeAppearancePathProvider

/**
 * MaskedCardView는 사용자 정의 MaterialCardView로, 내용물이 특정 모양에 따라 잘리는 기능을 제공합니다.
 * - 카드 내부의 내용을 지정된 ShapeAppearanceModel에 따라 마스킹(클리핑)합니다.
 */
class MaskedCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyle) {

    // ShapeAppearancePathProvider는 지정된 모양을 기반으로 경로(Path)를 생성합니다.
    @SuppressLint("RestrictedApi")
    private val pathProvider = ShapeAppearancePathProvider()

    // 클리핑에 사용할 Path 객체
    private val path: Path = Path()

    // 카드의 모양을 정의하는 ShapeAppearanceModel
    private val shapeAppearance: ShapeAppearanceModel = ShapeAppearanceModel.builder(
        context,
        attrs,
        defStyle,
        R.style.Widget_MaterialComponents_CardView // 기본 스타일 설정
    ).build()

    // 카드의 크기를 나타내는 RectF 객체
    private val rectF = RectF(0f, 0f, 0f, 0f)

    /**
     * 카드의 내용물을 그리기 전에 클리핑 경로를 설정하여 지정된 모양에 맞게 잘립니다.
     */
    override fun onDraw(canvas: Canvas) {
        // Path를 사용하여 클리핑 경로를 설정
        canvas.clipPath(path)

        // 부모 클래스의 onDraw를 호출하여 기본 그리기 동작 수행
        super.onDraw(canvas)
    }

    /**
     * 카드의 크기가 변경될 때 호출되는 메서드
     * - 크기에 따라 클리핑 경로를 다시 계산합니다.
     */
    @SuppressLint("RestrictedApi")
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // RectF 객체를 새로운 크기에 맞게 업데이트
        rectF.right = w.toFloat()
        rectF.bottom = h.toFloat()

        // ShapeAppearanceModel을 사용하여 새로운 Path를 계산
        pathProvider.calculatePath(shapeAppearance, 1f, rectF, path)

        // 부모 클래스의 onSizeChanged 호출
        super.onSizeChanged(w, h, oldw, oldh)
    }
}
