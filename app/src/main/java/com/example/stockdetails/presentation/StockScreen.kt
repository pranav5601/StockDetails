package com.example.stockdetails.presentation

sealed class StockScreen (val route: String){
    object CompanyListingScreen: StockScreen("company_listing")
    object CompanyInfoScreen: StockScreen("company_info")

    /*fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {arg->
                append("/$arg")
            }
        }
    }*/
}