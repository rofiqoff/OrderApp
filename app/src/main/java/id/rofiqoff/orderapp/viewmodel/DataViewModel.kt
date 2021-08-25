package id.rofiqoff.orderapp.viewmodel

import androidx.lifecycle.ViewModel
import id.rofiqoff.orderapp.data.DataRepository

class DataViewModel(dataRepository: DataRepository) : ViewModel() {
    val data = dataRepository.getItemData()

}