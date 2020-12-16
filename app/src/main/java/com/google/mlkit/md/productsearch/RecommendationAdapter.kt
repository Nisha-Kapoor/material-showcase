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

package com.google.mlkit.md.productsearch

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.mlkit.md.R
import com.google.mlkit.md.productsearch.RecommendationAdapter.RecViewHolder

class RecommendationAdapter(private val recList: List<String>) : Adapter<RecViewHolder>() {

    class RecViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val titleView: TextView = view.findViewById(R.id.recommendation)

        fun bindRecommendation(rec: String) {
            titleView.text = rec
        }

        companion object {
            fun create(parent: ViewGroup) =
                    RecViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.`effect_item`, parent, false))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder =
            RecViewHolder.create(parent)

    override fun getItemCount(): Int = recList.size

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
        holder.bindRecommendation(recList[position])
    }
}
