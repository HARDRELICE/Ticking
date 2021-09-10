package com.hardrelice.base

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Build

object Permission {
    const val FINISH = 0
    const val WAIT = 1
    const val OK = 2
    fun get(
        activity: Activity,
        requestCode: Int,
        permissions: Array<String>,
        title: String = "",
        message: String = "",
        button: String = ""
    ): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var needGranted = false
            val needGrantedPermission = arrayListOf<String>()
            for (permission in permissions) {
                if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    needGranted = true
                    needGrantedPermission.add(permission)
                }
            }
            var needRequest = true
            for (permission in needGrantedPermission) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    needRequest = false
                }
            }

            if (needGranted && needRequest) {
                activity.requestPermissions(
                    needGrantedPermission.toTypedArray(),
                    requestCode
                )
            } else if(needGranted) {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(title) //对话框标题
                    .setMessage(message) //对话框内容
//                    .setIcon(R.drawable.) //对话框图标
                    .setCancelable(false) //点击对话框之外的部分是否取消对话框
                    .setPositiveButton(
                        button
                    ) { dialog, which ->
                        activity.finish() //结束当前Activity
                    }
                val dialog: Dialog = builder.create()
                dialog.show()
                return FINISH
            }
        }
        return OK
    }
}