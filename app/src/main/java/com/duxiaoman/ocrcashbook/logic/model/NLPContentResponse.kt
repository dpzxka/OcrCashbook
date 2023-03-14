package com.duxiaoman.ocrcashbook.logic.model

data class NLPContentResponse(val results_list:List<ResultList>,val log_id:Long)

data class ResultList(val content:String,val results:List<ResponseList>)

data class ResponseList(val items:List<Items>)

data class Items(val text: String)