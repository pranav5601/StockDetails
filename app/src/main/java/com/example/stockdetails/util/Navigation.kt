package com.example.stockdetails.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockdetails.presentation.company_info.CompanyInfoScreen
import com.example.stockdetails.presentation.company_listing.CompanyListingScreen
import com.example.stockdetails.presentation.StockScreen

@Composable
fun Navigation(
    navHostController: NavHostController = rememberNavController()
) {


    NavHost(
        navController = navHostController,
        startDestination = StockScreen.CompanyListingScreen.route
    ) {
        composable(route = StockScreen.CompanyListingScreen.route) {
            CompanyListingScreen(
                navController = navHostController,
                clickSingleCompany = { symbol ->
                    navHostController.navigate(StockScreen.CompanyInfoScreen.route + "/${symbol}")

                }
            )
        }

        composable(
            route = StockScreen.CompanyInfoScreen.route + "/{symbol}",
            arguments = listOf(
                navArgument("symbol"){
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            CompanyInfoScreen(symbol = it.arguments?.getString("symbol"))
        }

    }

}

