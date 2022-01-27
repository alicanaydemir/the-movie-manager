package com.kuka.app.tmm.data.source.remote

import com.kuka.app.tmm.data.model.request.*
import com.kuka.app.tmm.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface TmmApi {

    @GET("authentication/token/new")
    suspend fun requestToken(): Response<ResponseRequestToken>

    @POST("authentication/token/validate_with_login")
    suspend fun createSessionWithLogin(@Body requestWithLogin: RequestCreateSessionWithLogin?): Response<ResponseCreateSessionWithLogin>

    @POST("authentication/session/new")
    suspend fun createSession(@Body requestCreateSession: RequestCreateSession?): Response<ResponseCreateSession>

    @GET("search/movie")
    suspend fun search(@Query("query") query: String): Response<ResponseSearch>

    @GET("account")
    suspend fun account(@Query("session_id") sessionId: String): Response<ResponseAccount>

    @POST("account/{account_id}/favorite")
    suspend fun markAsFavorite(
        @Path("account_id") accountId: Int?,
        @Query("session_id") sessionId: String?,
        @Body requestMarkAsFavorite: RequestMarkAsFavorite?
    ): Response<ResponseMarkAsFavorite>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorite(
        @Path("account_id") accountId: Int?,
        @Query("session_id") sessionId: String?,
        @Query("page") page: Int?
    ): Response<ResponseGetFavorite>

    @POST("account/{account_id}/watchlist")
    suspend fun addWatchList(
        @Path("account_id") accountId: Int?,
        @Query("session_id") sessionId: String?,
        @Body requestAddWatchList: RequestAddWatchList?
    ): Response<ResponseAddWatchList>

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchList(
        @Path("account_id") accountId: Int?,
        @Query("session_id") sessionId: String?,
        @Query("page") page: Int?
    ): Response<ResponseGetWatchList>

    @GET("movie/{movie_id}/account_states")
    suspend fun getAccountStates(
        @Path("movie_id") movieId: Int?,
        @Query("session_id") sessionId: String?,
    ): Response<ResponseAccountStates>

}
