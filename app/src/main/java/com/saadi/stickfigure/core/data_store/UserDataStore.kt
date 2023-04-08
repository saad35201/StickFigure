package com.saadi.stickfigure.core.data_store

import com.saadi.stickfigure.core.enums.UserEnum
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataStore(
    private val manager: DataStoreManager
) {

    suspend fun saveUser(user: User){
        manager.saveData(UserEnum.USER.name,user.toString())
    }

    suspend fun saveToken(token: String){
        manager.saveData(UserEnum.TOKEN.name,token)
    }

    suspend fun isLoggedIn(isLoggedIn: Boolean){
        manager.saveData(UserEnum.IS_LOGGED_IN.name,isLoggedIn)
    }

    suspend fun isRememberMe(rememberMe: Boolean){
        manager.saveData(UserEnum.REMEMBER_ME.name,rememberMe)
    }

    suspend fun getUser(): Flow<String?> {
        return manager.getData(key = UserEnum.USER.name,"")
    }

    suspend fun getToken(): Flow<String?> {
        return manager.getData(key = UserEnum.TOKEN.name,"")
    }

    suspend fun getIsLoggedIn(): Flow<Boolean?> {
        return manager.getData(key = UserEnum.IS_LOGGED_IN.name,false)
    }

    suspend fun getIsRememberMe(): Flow<Boolean?> {
        return manager.getData(key = UserEnum.REMEMBER_ME.name,false)
    }

}