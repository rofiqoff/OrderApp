package id.rofiqoff.orderapp.di

import id.rofiqoff.orderapp.api.ClientService
import id.rofiqoff.orderapp.data.DataRepository
import id.rofiqoff.orderapp.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(): DataRepository {
        val remoteDataSource = RemoteDataSource.getInstance(ClientService.create())

        return DataRepository.getInstance(remoteDataSource)
    }
}