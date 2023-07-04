package org.d3if00001.storyapp.utils

import org.d3if00001.storyapp.data.remote.retrofit.result.getStoryResult

object DataDummy {
    fun generateDataStory():List<getStoryResult>{
        val storyList = ArrayList<getStoryResult>()
        for (iterate in 0..10){
            val story = getStoryResult(
                id = "$iterate",
                description = "Description",
                photo = "https://unsplash.com/photos/a-man-in-a-space-suit-walking-through-a-desert-UzcmuacTX7s",
                createdAt = "20/20/2023"
            )
            storyList.add(story)
        }
        return storyList
    }
}