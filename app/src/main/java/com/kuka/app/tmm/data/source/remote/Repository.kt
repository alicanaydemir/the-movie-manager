package com.kuka.app.tmm.data.source.remote

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.data.model.request.*
import com.kuka.app.tmm.data.model.response.*
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun requestToken(): Flow<Resource<ResponseRequestToken>>
    fun createSessionWithLogin(request: RequestCreateSessionWithLogin?): Flow<Resource<ResponseCreateSessionWithLogin>>
    fun createSession(request: RequestCreateSession?): Flow<Resource<ResponseCreateSession>>
    fun search(request: RequestSearch?): Flow<Resource<ResponseSearch>>
    fun account(request: RequestAccount?): Flow<Resource<ResponseAccount>>
    fun markAsFavorite(request: RequestMarkAsFavorite?): Flow<Resource<ResponseMarkAsFavorite>>
    fun getFavorite(request: RequestGetFavorite?): Flow<Resource<ResponseGetFavorite>>
    fun addWatchList(request: RequestAddWatchList?): Flow<Resource<ResponseAddWatchList>>
    fun getWatchList(request: RequestGetWatchList?): Flow<Resource<ResponseGetWatchList>>
    fun getAccountStates(request: RequestAccountStates?): Flow<Resource<ResponseAccountStates>>
}