package org.example.project.nutrisport

import android.app.Application
import com.nutrisport.di.initalizeKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext


class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
       initalizeKoin(
           config = {
               androidContext(this@MyApplication)

           }

       )
        Firebase.initialize(this)
    }
}