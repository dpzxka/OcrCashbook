package com.duxiaoman.ocrcashbook.logic

import android.widget.Toast
import androidx.lifecycle.liveData
import com.duxiaoman.ocrcashbook.logic.model.NLPContentRequest
import com.duxiaoman.ocrcashbook.logic.network.OCRNetwork
import com.duxiaoman.ocrcashbook.util.LogUtil
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import kotlin.coroutines.CoroutineContext

object Repository {


    /*生产新增*/
    fun nlpContent(token:String,query: NLPContentRequest) = fire(Dispatchers.IO){
        LogUtil.i(msg = "nlpContent,start")
        coroutineScope {
            val queueRequest = async {
                OCRNetwork.nlpRequest(token,query)
            }
            LogUtil.i(msg = "需求：查询列表请求数据：$query")
            val queueResponse = queueRequest.await()
            LogUtil.e(msg = "需求更新结果：${queueResponse.results_list},${queueResponse.log_id}")

            if (queueResponse.results_list.isNotEmpty()){
                val result = queueResponse.results_list
                Result.success(result)
            } else {
                Result.failure(RuntimeException("realtime response status is failed"))
            }
            /*when(updateDemandDataResponse.code){
                200 -> {
                    val places = DemandCreateResult(updateDemandDataResponse.code,updateDemandDataResponse.message,updateDemandDataResponse.status)
                    Result.success(places)
                }
                1000 -> {
                    val places = DemandCreateResult(updateDemandDataResponse.code,updateDemandDataResponse.message,updateDemandDataResponse.status)
                    Result.success(places)
                }
                else ->  Result.failure(RuntimeException("realtime response status is ${updateDemandDataResponse.code}"))
            }*/
        }
    }


    /*上传文件*/
    /*fun uploadFile(params:File) = fire(Dispatchers.IO){
        LogUtil.i(msg = "searchPurchase,start")
        val file = RequestBody.create(MediaType.parse("multipart/form-data"),params)
        val part = MultipartBody.Part.createFormData("file",params.name,file)
        coroutineScope {
            val deferredRealtime = async {
                IMNetwork.uploadFile(part)
            }
            LogUtil.i(msg = "文件上传请求参数：$params")
            val response = deferredRealtime.await()
            LogUtil.e(msg = "文件上传请求结果：${response.code},${response.message},${response.result}")
            if (response.code == 200){
                val result = response.result
                Result.success(result)
            } else {
                LogUtil.d(msg = "请求异常")
                Result.failure(RuntimeException("realtime response status is ${response.code}"))
            }
        }
    }*/

    private fun <T> fire(context: CoroutineContext, block:suspend() -> Result<T>) = liveData<Result<T>>(context){
        val result = try {
            block()
        } catch (e:Exception){
            LogUtil.e(msg = "协程调用异常：错误信息：$e")
            Result.failure<T>(e)
        }
        emit(result)
    }

}