package com.example.assignmenttrack;

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // Memberitahu kalau Module di buat mulai dari Aplikasi nyala
class MainApplication : Application(){
}

