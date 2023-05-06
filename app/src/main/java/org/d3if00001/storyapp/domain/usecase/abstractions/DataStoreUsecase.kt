package org.d3if00001.storyapp.domain.usecase.abstractions

import org.d3if00001.storyapp.domain.models.User

interface DataStoreUsecase {
    fun login(user:User)
    fun register(user:User)
}