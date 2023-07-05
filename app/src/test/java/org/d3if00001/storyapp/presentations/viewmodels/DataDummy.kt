package org.d3if00001.storyapp.presentations.viewmodels

import org.d3if00001.storyapp.data.database.Item.StoryResponseItem

object DataDummy {
    fun generateDataStory():List<StoryResponseItem>{
        val storyList = ArrayList<StoryResponseItem>()
        for (iterate in 0..10){
            val story = StoryResponseItem(
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