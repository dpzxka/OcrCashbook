package com.duxiaoman.ocrcashbook.logic.model

data class NLPContentRequest(val content_list:List<Content>)

data class Content(val content:String,val query_list:List<QueryList>)

data class QueryList(val query:String)