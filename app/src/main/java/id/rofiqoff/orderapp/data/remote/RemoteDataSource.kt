package id.rofiqoff.orderapp.data.remote

import id.rofiqoff.orderapp.api.ApiService
import id.rofiqoff.orderapp.base.BaseDataSource

class RemoteDataSource private constructor(private val apiService: ApiService) : BaseDataSource() {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService).apply { instance = this }
            }
    }

    suspend fun requestData() = getResult { apiService.getItemData() }

}