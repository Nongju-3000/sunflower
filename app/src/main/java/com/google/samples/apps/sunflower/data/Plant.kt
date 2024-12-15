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

package com.google.samples.apps.sunflower.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

/**
 * `Plant`는 데이터베이스의 `plants` 테이블과 매핑되는 데이터 클래스입니다.
 *
 * @param plantId 식물의 고유 ID (기본 키)
 * @param name 식물의 이름
 * @param description 식물의 설명
 * @param growZoneNumber 성장 구역 번호
 * @param wateringInterval 물을 줘야 하는 주기 (일 단위, 기본값은 7일)
 * @param imageUrl 식물의 이미지 URL (기본값은 빈 문자열)
 */
@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey @ColumnInfo(name = "id") val plantId: String, // 식물의 고유 ID
    val name: String, // 식물 이름
    val description: String, // 식물 설명
    val growZoneNumber: Int, // 성장 구역 번호
    val wateringInterval: Int = 7, // 물 주기 (기본값: 7일)
    val imageUrl: String = "" // 이미지 URL (기본값: 빈 문자열)
) {

    /**
     * 식물이 물을 줘야 하는지 판단합니다.
     * - [since] 날짜가 마지막 물을 준 날짜 + 물 주기보다 크면 true를 반환
     *
     * @param since 현재 날짜 또는 기준 날짜
     * @param lastWateringDate 마지막으로 물을 준 날짜
     * @return 물을 줘야 하는지 여부
     */
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        since > lastWateringDate.apply { add(DAY_OF_YEAR, wateringInterval) }

    /**
     * `Plant` 객체의 이름을 반환합니다.
     * - 디버깅 또는 출력 시 유용합니다.
     */
    override fun toString() = name
}
