package com.anggit97.cataloguemovie.data.remote

import com.anggit97.cataloguemovie.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Anggit Prayogo on 2019-09-05.
 * Github : @anggit97
 */
inline fun <reified T> services(): T {
    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setLenient()
        .create()

    val httpClient = OkHttpClient.Builder()

    httpClient.addInterceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url()

        original.newBuilder().addHeader(
            "Accept", "application/json"
        )

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", "en")
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    if (BuildConfig.DEBUG) {
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit.create(T::class.java)
}