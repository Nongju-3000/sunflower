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
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * `GardenPlanting`는 사용자가 [Plant]를 정원에 추가할 때 생성되는 엔터티입니다.
 * - 사용자의 정원에 추가된 식물에 대한 메타데이터를 저장합니다.
 * - [plantDate] 및 [lastWateringDate]와 같은 속성은 알림(예: 식물에 물을 줘야 할 때)과 같은 기능에 사용됩니다.
 *
 * @param plantId 추가된 식물의 ID
 * @param plantDate 식물이 심어진 날짜 (기본값은 현재 날짜)
 * @param lastWateringDate 마지막으로 물을 준 날짜 (기본값은 현재 날짜)
 */
@Entity(
    tableName = "garden_plantings", // 테이블 이름
    foreignKeys = [
        ForeignKey(
            entity = Plant::class, // 참조되는 엔터티
            parentColumns = ["id"], // 부모 테이블의 기본 키
            childColumns = ["plant_id"] // 현재 테이블의 외래 키
        )
    ],
    indices = [Index("plant_id")] // plant_id 열에 인덱스 생성
)
data class GardenPlanting(
    @ColumnInfo(name = "plant_id") val plantId: String, // 참조되는 Plant의 ID

    /**
     * 식물이 심어진 날짜를 나타냅니다.
     * - 알림에서 수확 시점을 알려줄 때 사용됩니다.
     */
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance(), // 기본값: 현재 날짜

    /**
     * 식물에 마지막으로 물을 준 날짜를 나타냅니다.
     * - 알림에서 물 주기 시점을 알려줄 때 사용됩니다.
     */
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance() // 기본값: 현재 날짜
) {
    /**
     * GardenPlanting 엔터티의 고유 ID
     * - 자동 생성되는 기본 키
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gardenPlantingId: Long = 0
}
