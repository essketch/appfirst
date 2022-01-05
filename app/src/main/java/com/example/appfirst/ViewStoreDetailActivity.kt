package com.example.appfirst

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.appfirst.models.StoreData
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.android.synthetic.main.activity_view_store_detail.*

class ViewStoreDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_store_detail)

        val storeData = intent.getSerializableExtra("store") as StoreData
        txtStoreName.text = storeData.name
        txtPhoneNum.text = storeData.phoneNUM
        txtSiteLink.text = storeData.link
        Glide.with(this).load(storeData.logoURL).into(imgLogo)


        txtSiteLink.setOnClickListener {
            var myUri = Uri.parse("${storeData.link}")
            var myIntent = Intent(Intent.ACTION_VIEW, myUri)
            startActivity(myIntent)
        }

        btnCall.setOnClickListener {
            val pl = object : PermissionListener {
                override fun onPermissionGranted() {
                    var myUri = Uri.parse("tel:${storeData.phoneNUM}")
                    var myIntent = Intent(Intent.ACTION_CALL,myUri)
                    startActivity(myIntent)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@ViewStoreDetailActivity, "전화 연결 불가능", Toast.LENGTH_SHORT).show()

                }
            }
        TedPermission.create()
            .setPermissionListener(pl)
            .setPermissions(Manifest.permission.CALL_PHONE)
            .check()
        }
    }
}