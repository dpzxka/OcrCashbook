package com.duxiaoman.ocrcashbookk

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.baidu.ocr.sdk.OCR
import com.baidu.ocr.sdk.OnResultListener
import com.baidu.ocr.sdk.exception.OCRError
import com.baidu.ocr.sdk.model.AccessToken
import com.baidu.ocr.ui.camera.CameraActivity
import com.duxiaoman.ocrcashbook.model.OCRResponse
import com.duxiaoman.ocrcashbookk.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var alertDialog: AlertDialog.Builder? = null
    private var hasGotToken = false
    private val REQUEST_CODE_GENERAL_BASIC = 106


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alertDialog = AlertDialog.Builder(this)
        initAccessToken()
        binding.button.setOnClickListener {
            if (!checkTokenStatus()) {
                return@setOnClickListener
            }
            val intent = Intent(this@MainActivity, CameraActivity::class.java)
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(applicationContext).absolutePath)
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL)
            startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initAccessToken()
        } else {
            Toast.makeText(
                applicationContext,
                "需要android.permission.READ_PHONE_STATE",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initAccessToken(){
        OCR.getInstance(applicationContext).initAccessToken(object :OnResultListener<AccessToken>{
            override fun onResult(accessToken: AccessToken?) {
                val token: String = accessToken?.accessToken.toString()
                hasGotToken = true
            }

            override fun onError(error: OCRError?) {
                error?.printStackTrace();
                alertText("licence方式获取token失败", error!!.message!!)
            }
        }, "aip-ocr.license",applicationContext);
    }

    private fun alertText(title: String, message: String) {
        runOnUiThread {
            alertDialog?.let {
                it.setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show()
            }
        }
    }
    private fun checkTokenStatus(): Boolean {
        if (!hasGotToken) {
            Toast.makeText(applicationContext, "token还未成功获取", Toast.LENGTH_LONG).show()
        }
        return hasGotToken
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 识别成功回调，通用文字识别
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recGeneralBasic(applicationContext,
                FileUtil.getSaveFile(applicationContext).absolutePath) {

                    result -> infoPopText(result)
                val fromJson = Gson().fromJson(result, OCRResponse::class.java)
                for (item in fromJson.words_result){
                    Log.i("zhangtao",item.words)
                }
                Log.i("zhangtao","result:${result.toString()}")
                    binding.result.text = result.toString()
            }
        }
    }
    private fun infoPopText(result: String) {
        alertText("", result)
    }

}