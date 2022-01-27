package com.kuka.app.tmm.di

import android.content.Context
import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kuka.app.tmm.BuildConfig
import com.kuka.app.tmm.data.source.local.dao.AppDao
import com.kuka.app.tmm.data.source.remote.Repository
import com.kuka.app.tmm.data.source.remote.RepositoryImp
import com.kuka.app.tmm.data.source.remote.TmmApi
import com.kuka.app.tmm.utils.SharedHelper
import com.kuka.app.tmm.utils.service.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideTokenInterceptor(
        pref: SharedHelper,
        @ApplicationContext context: Context
    ): TokenInterceptor {
        return TokenInterceptor(pref, context)
    }

    @Singleton
    @Provides
    fun provideSharedHelper(sharedPreferences: SharedPreferences): SharedHelper {
        return SharedHelper(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideTmmApi(tokenInterceptor: TokenInterceptor): TmmApi {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()

        val okClient = OkHttpClient.Builder()
            .addNetworkInterceptor(tokenInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(TmmApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(
        api: TmmApi,
        appDao: AppDao,
        sharedHelper: SharedHelper
    ): Repository = RepositoryImp(api, appDao, sharedHelper)
}