package com.example.apotek.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apotek.data.model.*
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel: ViewModel() {
    private var _data = MutableLiveData<Boolean>()
    val data get() = _data
    private var _userData = MutableLiveData<User>()
    val userData = _userData
    private var _loginResponse = MutableLiveData<Response<serverResponse>>()
    val loginResponse = _loginResponse
    private var _registResponse = MutableLiveData<Response<serverResponse>>()
    val registResponse = _registResponse
    var allListObat = MutableLiveData<List<Obat>>()
    var listObat = MutableLiveData<MutableList<String>>()
    val triggerListObat = MutableLiveData<String>()
    var tipeObat = MutableLiveData<String>()
    val price = MutableLiveData<Int>()
    val namaPembeli = MutableLiveData<String>()

    fun testConnection(){
        viewModelScope.launch {
            try{
                HttpApi.retrofitStringService.checkConnection().toBoolean()
                _data.value = true
            }
            catch(ee: Exception){
                _data.value = false
            }
        }
    }

    fun getUserData(username: String, password: String){
        viewModelScope.launch{
            _userData.value = HttpApi.retrofitService.getUserData(username, password)
        }
    }

    fun getObat(){
        viewModelScope.launch{
            allListObat.value = HttpApi.retrofitService.getObat()
        }
    }


    fun login(username: String, password: String){
        viewModelScope.launch{
            _loginResponse.value = HttpApi.retrofitService.login(Login(username, password))
        }
    }

    fun register(data: User){
        viewModelScope.launch {
            _registResponse.value = HttpApi.retrofitService.register(data)
        }
    }
}