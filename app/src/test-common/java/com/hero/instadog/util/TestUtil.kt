/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hero.instadog.util

import com.hero.instadog.repository.model.Breed


object TestUtil {

    fun createBreed(name: String, image: String) = Breed(
        name = name
    )

    fun createBreeds(count: Int, name: String, imageUrl: String): List<Breed> {
        return (0 until count).map {
            createBreed(
                name = name + it,
                image = imageUrl + it
            )
        }
    }
}
