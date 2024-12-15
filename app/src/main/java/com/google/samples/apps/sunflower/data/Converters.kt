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

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Room에서 복잡한 데이터 타입을 처리할 수 있도록 변환기를 정의하는 클래스.
 * - Room은 기본 데이터 타입(int, string 등)만 지원하므로, 복잡한 타입(Calendar 등)을 저장하려면 변환기가 필요합니다.
 */
class Converters {

    /**
     * `Calendar` 객체를 타임스탬프(`Long`)로 변환합니다.
     * - Room에 저장할 수 있는 형식으로 변환.
     *
     * @param calendar 변환할 Calendar 객체
     * @return Long 타입의 타임스탬프 (밀리초)
     */
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    /**
     * 타임스탬프(`Long`)를 `Calendar` 객체로 변환합니다.
     * - Room에서 데이터를 가져올 때 다시 Calendar 형식으로 변환.
     *
     * @param value 변환할 Long 타입의 타임스탬프 (밀리초)
     * @return Calendar 객체
     */
    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}
