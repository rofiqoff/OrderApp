package id.rofiqoff.orderapp.data

import androidx.lifecycle.LiveData
import id.rofiqoff.orderapp.data.remote.ResponseItem
import id.rofiqoff.orderapp.data.remote.Result

interface DataSource {
    fun getItemData(): LiveData<Result<List<ResponseItem>>>
}