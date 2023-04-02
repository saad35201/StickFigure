package com.saadi.stickfigure.core.data_store

import com.saadi.stickfigure.core.enums.UserEnum
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataStore(
    private val manager: DataStoreManager
) {

    suspend fun saveUser(user: User){
        manager.save(UserEnum.USER.name,user.toString())
    }

    suspend fun saveToken(token: String){
        manager.save(UserEnum.TOKEN.name,token)
    }

    suspend fun isLoggedIn(isLoggedIn: Boolean){
        manager.save(UserEnum.IS_LOGGED_IN.name,isLoggedIn)
    }

    suspend fun isRememberMe(rememberMe: Boolean){
        manager.save(UserEnum.REMEMBER_ME.name,rememberMe)
    }

    fun getUser(): Flow<String?> {
        return manager.getString(key = UserEnum.USER.name,"")
    }

    fun getToken(): Flow<String?> {
        return manager.getString(key = UserEnum.TOKEN.name,"")
    }

    fun getIsLoggedIn(): Flow<Boolean?> {
        return manager.getBoolean(key = UserEnum.IS_LOGGED_IN.name)
    }

    fun getIsRememberMe(): Flow<Boolean?> {
        return manager.getBoolean(key = UserEnum.REMEMBER_ME.name)
    }

}