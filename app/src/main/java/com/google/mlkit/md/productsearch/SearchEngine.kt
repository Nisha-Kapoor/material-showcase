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

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.md.objectdetection.DetectedObjectInfo
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class SearchEngine(context: Context) {

    fun search(
        detectedObject: DetectedObjectInfo,
        listener: (detectedObject: DetectedObjectInfo, recommendationList: List<String>) -> Unit
    ) {
        // Cropping the object image out of the full image is expensive, so do it off the UI thread.
        var recommendationList = getRecommendations(detectedObject)
        listener.invoke(detectedObject, recommendationList)
    }


    companion object {
        private const val TAG = "SearchEngine"

        @Throws(Exception::class)
        private fun getRecommendations(searchingObject: DetectedObjectInfo): List<String> {
            var recommendation_list = ArrayList<String>()
            val imageData = searchingObject.getBitmap()
            val objectImageData = searchingObject.getObjectBitmap()
                ?: throw Exception("Failed to get object image data!")
            var bg_recommendation = edgeScoreBasedSuggestion(imageData, objectImageData)
//            var light_recommendation = lightBasedSuggestion(imageData)
            recommendation_list.add(bg_recommendation)
//            recommendation_list.add(light_recommendation)
            return recommendation_list
        }

//        private fun lightBasedSuggestion(imageData: Bitmap): String {
//            val hsv = Mat()
//            Utils.bitmapToMat(imageData, hsv)
//            Imgproc.cvtColor(hsv, hsv, Imgproc.COLOR_BGR2HSV)
//            return "Increase light"
//        }

        private fun edgeScoreBasedSuggestion(imageData: Bitmap, objectImageData: Bitmap): String {
            val bg_edges = detectEdges(imageData) - detectEdges(objectImageData)
            var recommendation = "LGTM :)"
            if (bg_edges > 10) {
                recommendation = "Place the product in clean background"
            }
            return recommendation
        }

        private fun detectEdges(bitmap: Bitmap): Int {
            val rgba = Mat()
            Utils.bitmapToMat(bitmap, rgba)
            val edges = Mat()
            Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_BGR2GRAY)
            Imgproc.Canny(
                    edges,
                    edges,
                    50.0,
                    200.0
            )
            var contours = mutableListOf<MatOfPoint>()
            Imgproc.findContours(edges, contours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE)
            return contours.size
        }
    }
}
