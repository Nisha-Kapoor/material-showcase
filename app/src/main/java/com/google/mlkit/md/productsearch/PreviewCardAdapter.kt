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
import com.google.mlkit.md.R

/** Powers the bottom card carousel for displaying the preview of product search result.  */
class PreviewCardAdapter(
    private val searchedObjectList: List<SearchedObject>,
    private val previewCordClickedListener: (searchedObject: SearchedObject) -> Any
) : RecyclerView.Adapter<PreviewCardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.products_preview_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val searchedObject = searchedObjectList[position]
        holder.bindProducts(searchedObject.recommendationtList)
        holder.itemView.setOnClickListener { previewCordClickedListener.invoke(searchedObject) }
    }

    override fun getItemCount(): Int = searchedObjectList.size

    class CardViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleView: TextView = itemView.findViewById(R.id.recommendation)

        internal fun bindProducts(recommendations: List<String>) {
            if (recommendations.isEmpty()) {
                titleView.setText(R.string.static_image_card_no_result_title)
            } else {
                val topRec = recommendations[0]
                titleView.text = topRec
            }
        }
    }
}
