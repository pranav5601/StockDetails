package com.example.stockdetails.presentation.company_listing


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockdetails.domain.repository.StockRepo
import com.example.stockdetails.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(private val stockRepo: StockRepo): ViewModel(){

    var state by mutableStateOf(CompanyListingState())
    private var searchJob: Job? = null
    fun onEvent(event: CompanyListingEvent){
        when (event){
            is CompanyListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListing()
                }

            }
            is CompanyListingEvent.Refresh -> {

                getCompanyListing(fetchFromRemote = true)

            }
        }
    }


    init {
        getCompanyListing()
    }

    private fun getCompanyListing(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ){
        viewModelScope.launch{
            stockRepo.getCompanyListing(fetchFromRemote, query)
                .collect{ result->
                    when (result){
                        is Resource.Success -> {

                            result.data?.let { listings ->
                                state = state.copy(companies = listings)
                            }

                        }
                        is Resource.Loading -> {

                            state = state.copy(isLoading = result.isLoading)
                        }
                        is Resource.Error -> {
                            Log.e("CompanyListing Error",result.message.toString())
                        }
                    }
                }
        }
    }

}