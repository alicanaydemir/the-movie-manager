package com.kuka.app.tmm.data.source.remote

import com.kuka.app.tmm.core.Resource
import com.kuka.app.tmm.data.model.request.*
import com.kuka.app.tmm.data.model.response.*
import com.kuka.app.tmm.data.source.local.dao.AppDao
import com.kuka.app.tmm.utils.helper.SharedHelper
import com.kuka.app.tmm.utils.extensions.filterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api: TmmApi,
    private val appDao: AppDao,
    val sharedHelper: SharedHelper
) : Repository {

    override fun requestToken(): Flow<Resource<ResponseRequestToken>> =
        flow {
            val data = api.requestToken()
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun createSessionWithLogin(request: RequestCreateSessionWithLogin?): Flow<Resource<ResponseCreateSessionWithLogin>> =
        flow {
            val data = api.createSessionWithLogin(request)
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun createSession(request: RequestCreateSession?): Flow<Resource<ResponseCreateSession>> =
        flow {
            val data = api.createSession(request)
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun search(request: RequestSearch?): Flow<Resource<ResponseSearch>> =
        flow {
            val data = api.search(request?.query ?: "")
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun account(request: RequestAccount?): Flow<Resource<ResponseAccount>> =
        flow {
            val data = api.account(request?.sessionId ?: "")
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun markAsFavorite(
        request: RequestMarkAsFavorite?
    ): Flow<Resource<ResponseMarkAsFavorite>> =
        flow {
            val x = RequestMarkAsFavorite(
                request?.favorite,
                request?.mediaId,
                request?.mediaType,
                null,
                null
            )
            val data = api.markAsFavorite(request?.accountId, request?.sessionId, x)
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun getFavorite(request: RequestGetFavorite?): Flow<Resource<ResponseGetFavorite>> =
        flow {
            val data = api.getFavorite(request?.accountId, request?.sessionId, request?.page)
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun addWatchList(request: RequestAddWatchList?): Flow<Resource<ResponseAddWatchList>> =
        flow {
            val data = api.addWatchList(request?.accountId, request?.sessionId, request)
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun getWatchList(request: RequestGetWatchList?): Flow<Resource<ResponseGetWatchList>> =
        flow {
            val data = api.getWatchList(request?.accountId, request?.sessionId, request?.page)
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }

    override fun getAccountStates(request: RequestAccountStates?): Flow<Resource<ResponseAccountStates>> =
        flow {
            val data = api.getAccountStates(request?.movieId, request?.sessionId)
            emit(Resource.Loading(false))
            emit(data.filterResponse())
        }
}