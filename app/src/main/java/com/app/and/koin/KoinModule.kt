package com.app.and.koin

import com.app.and.Utils
import com.app.and.repositories.RadioRepository
import com.app.and.services.AuthService
import com.app.and.viewmodels.RadioViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var  koinModule = module {

    single { getOkhttpClientInstance() }
    single { getRetrofitInstance(get()) }
    single { getRepoInstance(get()) }
    viewModel { RadioViewModel(get()) }

}

fun getOkhttpClientInstance() : OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    val interceptor = httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return  OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}

fun getRetrofitInstance(okHttpClient: OkHttpClient) : AuthService {
    return Retrofit.Builder()
        .baseUrl(Utils.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build().create(AuthService::class.java)
}
fun getRepoInstance(authService: AuthService) : RadioRepository {
    return  RadioRepository(authService)
}