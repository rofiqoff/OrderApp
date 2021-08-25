package id.rofiqoff.orderapp.data

import id.rofiqoff.orderapp.data.remote.RemoteDataSource
import id.rofiqoff.orderapp.utils.resultLiveData

class DataRepository constructor(private val remoteDataSource: RemoteDataSource) : DataSource {
    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(remoteDataSource).apply { instance = this }
            }

    }

    override fun getItemData() = resultLiveData { remoteDataSource.requestData() }


}