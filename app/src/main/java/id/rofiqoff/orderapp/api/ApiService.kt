package id.rofiqoff.orderapp.api

import id.rofiqoff.orderapp.data.remote.ResponseItem
import retrofit2.Response
import retrofit2.http.POST

interface ApiService {
    @POST("rest/items/search/api_key/teampsisthebest")
    suspend fun getItemData(): Response<List<ResponseItem>>
}