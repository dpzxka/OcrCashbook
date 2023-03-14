package com.duxiaoman.ocrcashbook.logic.network

import android.widget.Toast
import com.duxiaoman.ocrcashbook.OCRApplication
import com.duxiaoman.ocrcashbook.logic.model.NLPContentRequest
import com.duxiaoman.ocrcashbook.util.LogUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object OCRNetwork {
    
    /*采购需求*/
    private val placeService = ServiceCreator.create<OCRService>()


    /*采购需求创建*/
    suspend fun nlpRequest(token:String,query: NLPContentRequest) = placeService.nlpContent(token,query).await()


    /*挂起函数，*/
    private suspend fun <T> Call<T>.await():T {
        /*suspendCoroutine函数必须在协程作用域或挂起函数中才能调用，它接收一个Lambda表达
        式参数，主要作用是将当前协程立即挂起，然后在一个普通的线程中执行Lambda表达式中的代码*/
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if(body!=null) {
                        LogUtil.i(msg = "网络请求响应成功结果：$body")
                        continuation.resume(body)  //恢复被挂起的函数，并回调结果
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Toast.makeText(OCRApplication.context, "服务器连接失败: $t ", Toast.LENGTH_SHORT).show()
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}