package com.zrq.sese.base

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.zrq.sese.network.ApiService
import com.zrq.sese.network.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.ref.WeakReference
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit

abstract class BaseViewModel : ViewModel() {

    protected val apiService: ApiService by lazy {
        RetrofitProvider.apiService
    }

    lateinit var context: WeakReference<Context>

    protected fun launch(
        block: Block<Unit>,
        error: Error? = null,
        showErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                onError(e, showErrorToast)
                error?.invoke(e)
                e.printStackTrace()
            }
        }
    }

    /**
     * 统一处理错误
     * @param e 异常
     * @param showErrorToast 是否显示错误吐司
     */
    private fun onError(e: Exception, showErrorToast: Boolean) {
        when (e) {
            // 网络请求失败
            is ConnectException, is SocketTimeoutException, is UnknownHostException, is HttpException -> {
                if (showErrorToast) Toast.makeText(context.get(), "网络请求失败", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "网络请求失败" + e.message)
            }
            // 数据解析错误
            is JsonParseException -> {
                if (showErrorToast) Toast.makeText(context.get(), "数据解析错误", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "数据解析错误" + e.message)
            }
            // 其他错误
            else -> {
                if (showErrorToast) Toast.makeText(context.get(), "未知错误\n${e.message}", Toast.LENGTH_SHORT).show()
                Log.e(TAG, e.message ?: return)
            }

        }
    }


    companion object {
        private const val TAG = "BaseViewModel"
    }

}