package com.mustfaibra.instagraph.repositories

import com.mustfaibra.instagraph.data.fake.FakeServicesImpl
import com.mustfaibra.instagraph.models.Chat
import com.mustfaibra.instagraph.models.User
import com.mustfaibra.instagraph.sealed.DataResponse
import javax.inject.Inject

class ChatsRepository @Inject constructor(
    private val fakeServicesImpl: FakeServicesImpl,
) {
    suspend fun getFakeChats() : DataResponse<List<Chat>>{
        return fakeServicesImpl.getFakeChats()
    }

    suspend fun getFakeOnlineUsers() : DataResponse<List<User>>{
        return fakeServicesImpl.getFakeOnlineUsers()
    }
}