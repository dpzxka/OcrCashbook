package com.duxiaoman.ocrcashbook.logic.model

/*result:
    {   "direction":0,
        "words_result":[
            {"words":"(applicationcontext"},
            {"words":"tyResult(requestCode:Int resultCode:Int"},
            {"words":"Result(requestCode,resultCode,data)"},
            {"words":"用文字银别"},
            {"words":"-REQUEST_CODE_GENERAL_BASIC &resultCode"},
            {"words":"vice.recGeneralBasic(applicationcontext,"},
            {"words":"getsaveFile(applicationcontext).absolutePa"},
            {"words":"lt -infoPopText(result)"},
            {"words":"i(tag:\"zhangtao\",msg:\"result:${result.tost"},
            {"words":"ding.result.text result.tostring()"},
            {"words":"pText(result:String){"},
            {"words":"\"\"result)"}
         ],
      "words_result_num":12,
      "log_id":1635072327502270867}*/
data class OCRResponse(val words_result:List<Words>, val words_result_num:Int, val log_id:Long)
data class Words(val words:String)