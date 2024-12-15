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

package com.google.samples.apps.sunflower.utilities

import kotlin.math.abs

/**
 * 주어진 위도를 기준으로 식물의 성장 가능 구역(Zone)을 결정하는 도우미 함수.
 *
 * 이 함수는 대략적으로 미국 농무부의 Plant Hardiness Zone Map
 * (http://planthardiness.ars.usda.gov/)을 기반으로 작성되었습니다.
 * 이 지도는 특정 위치에서 번성할 가능성이 가장 높은 식물을 결정하는 데 사용됩니다.
 *
 * 주요 특징:
 * - 위도가 두 개의 구역 범위의 경계에 있는 경우, 더 큰 구역 범위를 선택합니다.
 *   예: 위도 14.0 -> Zone 12.
 * - 음수 위도 값은 [Math.abs]를 사용하여 양수로 변환됩니다.
 * - 위도 값이 최대값(90.0)을 초과하면 Zone 1이 반환됩니다.
 *
 * @param latitude 주어진 위도 값 (음수 또는 양수 모두 가능)
 * @return 해당 위도에 대한 성장 가능 구역(Zone)
 */
fun getZoneForLatitude(latitude: Double) = when (abs(latitude)) {
    in 0.0..7.0 -> 13 // 위도 0.0 ~ 7.0 -> Zone 13
    in 7.0..14.0 -> 12 // 위도 7.0 ~ 14.0 -> Zone 12
    in 14.0..21.0 -> 11 // 위도 14.0 ~ 21.0 -> Zone 11
    in 21.0..28.0 -> 10 // 위도 21.0 ~ 28.0 -> Zone 10
    in 28.0..35.0 -> 9 // 위도 28.0 ~ 35.0 -> Zone 9
    in 35.0..42.0 -> 8 // 위도 35.0 ~ 42.0 -> Zone 8
    in 42.0..49.0 -> 7 // 위도 42.0 ~ 49.0 -> Zone 7
    in 49.0..56.0 -> 6 // 위도 49.0 ~ 56.0 -> Zone 6
    in 56.0..63.0 -> 5 // 위도 56.0 ~ 63.0 -> Zone 5
    in 63.0..70.0 -> 4 // 위도 63.0 ~ 70.0 -> Zone 4
    in 70.0..77.0 -> 3 // 위도 70.0 ~ 77.0 -> Zone 3
    in 77.0..84.0 -> 2 // 위도 77.0 ~ 84.0 -> Zone 2
    else -> 1 // 나머지 위도 -> Zone 1
}
