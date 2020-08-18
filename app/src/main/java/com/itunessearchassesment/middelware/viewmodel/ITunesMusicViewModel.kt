package com.itunessearchassesment.middelware.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itunessearchassesment.middelware.model.ITunesResultResponse
import com.itunessearchassesment.middelware.net.api.ServiceProvider
import com.itunessearchassesment.middelware.net.error_handle.APIError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ITunesMusicViewModel : ViewModel() {

    val resultsMutableLiveData: MutableLiveData<List<ITunesResultResponse.Result>> =
        MutableLiveData()
    val errorLiveData: MutableLiveData<APIError> = MutableLiveData()
    var allAlbums = listOf<ITunesResultResponse.Result>()

    fun performFiltering(charString: String) {
        if (TextUtils.isEmpty(charString)) {
            resultsMutableLiveData.postValue(allAlbums)
            return
        }
        val filteredList = arrayListOf<ITunesResultResponse.Result>()
        for (row in allAlbums) {
            if (row.artistName.toLowerCase()
                    .contains(charString.toLowerCase())
                or row.trackName.toLowerCase()
                    .contains(charString.toLowerCase())
                or row.collectionName.toLowerCase()
                    .contains(charString.toLowerCase())
                or row.collectionPrice.toString().toLowerCase()
                    .contains(charString.toLowerCase())
                or row.releaseDate.toLowerCase()
                    .contains(charString.toLowerCase())
            ) {

                filteredList.add(row)
            }
        }
        resultsMutableLiveData.postValue(filteredList)
    }

    fun getITunesResults() {
        ServiceProvider.serviceApi.getITunesResults()
            .enqueue(object : Callback<ITunesResultResponse> {
                override fun onFailure(call: Call<ITunesResultResponse>, t: Throwable) {
                    errorLiveData.postValue(ServiceProvider.handleError(t))
                }

                override fun onResponse(
                    call: Call<ITunesResultResponse>,
                    response: Response<ITunesResultResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseModel = response.body()
                        if (responseModel != null) {
                            val list =
                                responseModel.results.distinctBy { it.trackName } // a. Remove Duplicate data by Trackname#.
                            val sortedList =
                                list.sortedWith(compareBy { it.releaseDate }) //b. Sort the Data by ReleaseDate in ascending by Default.

                            resultsMutableLiveData.postValue(sortedList)
                            allAlbums = sortedList
                        }
                    } else {
                        errorLiveData.postValue(ServiceProvider.parseError(response))
                    }
                }

            })
    }

    //    c. Have Sorting implemented for Collection Name, track name, artist name, collection price Descending. and provide it through the UI for User Selection.
    fun sortByCollectionName() {
        val sortedList =
            allAlbums.sortedByDescending { it.collectionName }

        resultsMutableLiveData.postValue(sortedList)
    }

    fun sortByTrackName() {
        val sortedList =
            allAlbums.sortedByDescending { it.trackName }

        resultsMutableLiveData.postValue(sortedList)
    }

    fun sortByArtistName() {
        val sortedList =
            allAlbums.sortedByDescending { it.artistName }

        resultsMutableLiveData.postValue(sortedList)
    }

    fun sortByCollectionPrice() {
        val sortedList =
            allAlbums.sortedByDescending { it.collectionPrice }

        resultsMutableLiveData.postValue(sortedList)
    }

}