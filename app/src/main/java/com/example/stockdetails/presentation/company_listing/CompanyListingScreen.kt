package com.example.stockdetails.presentation.company_listing

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stockdetails.presentation.StockScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListingScreen(
    navController: NavController,
    viewModel: CompanyListingViewModel = hiltViewModel(),
    clickSingleCompany:(String) -> Unit
) {
    var searchQueryText by rememberSaveable() {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    val state = viewModel.state
    Column (
        modifier = Modifier.fillMaxSize()
            ){

        OutlinedTextField(value = state.searchQuery, onValueChange = {newText ->
            searchQueryText = newText
            viewModel.onEvent(CompanyListingEvent.OnSearchQueryChange(searchQueryText))
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
            placeholder = {Text(text = "Search...")},
            maxLines = 1,
            singleLine = true,
            trailingIcon = {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier
                        .clickable {
                            Log.e("Clear Text","Click")
                            searchQueryText = ""
                            viewModel.onEvent(CompanyListingEvent.OnSearchQueryChange(searchQueryText))
                            focusManager.clearFocus(true)
                        }
                )
            }
        )
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(CompanyListingEvent.Refresh)
            }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ){
                items(state.companies.size){i->

                    val company = state.companies[i]
                    CompanyItem(
                        company = company,
                        modifier = Modifier.fillMaxSize()
                            .clickable {
                                clickSingleCompany(company.symbol)
                            }.padding(16.dp),

                    )

                    if (i<state.companies.size){
                        Divider(modifier = Modifier.padding(
                            horizontal = 16.dp
                        ))
                    }
                }
            }

        }

        
    }

}