package com.duxiaoman.ocrcashbook.logic.network

import retrofit2.Call
import com.duxiaoman.ocrcashbook.logic.model.NLPContentRequest
import com.duxiaoman.ocrcashbook.logic.model.NLPContentResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface OCRService {
    @POST("nlp/v1/txt_monet")
    fun nlpContent(@Query("access_token") token: String,@Body query: NLPContentRequest):Call<NLPContentResponse>
}