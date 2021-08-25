package id.rofiqoff.orderapp.api

import com.google.gson.GsonBuilder
import id.rofiqoff.orderapp.BuildConfig
import id.rofiqoff.orderapp.utils.AppHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientService {

    private val log = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(log)
        .build()

    private val gSon = GsonBuilder().create()

    fun create(): ApiService = run {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gSon))
            .baseUrl(AppHelper.BASE_URL)
            .client(client)
            .build()
        retrofit.create(ApiService::class.java)
    }

}