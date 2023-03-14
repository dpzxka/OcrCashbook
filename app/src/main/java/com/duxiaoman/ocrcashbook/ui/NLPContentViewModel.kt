package com.duxiaoman.ocrcashbook.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.duxiaoman.ocrcashbook.logic.Repository
import com.duxiaoman.ocrcashbook.logic.model.NLPContentRequest
import com.duxiaoman.ocrcashbook.util.LogUtil

class NLPContentViewModel: ViewModel()  {

    var productNameId = ""

    var productName = ""

    var locationId = 0

    var specifier = 0
    var locationIds = 0

    /*盘库列表*/
    private val contentListLiveData = MutableLiveData<NLPContentRequest>()

    val nlpContentListGetLiveData = Transformations.switchMap(contentListLiveData) { info ->
        LogUtil.e(msg = "nlpContentListGetLiveData,执行")
        Repository.nlpContent("", info)
    }

    fun nlpContentQuery(query: NLPContentRequest) {
        LogUtil.i(msg = "nlpContentQuery 执行,$query")
        contentListLiveData.value = query
    }
}