package com.example.applicationquizforstudents.di

import com.example.applicationquizforstudents.data.impl.AccountServiceImpl
import com.example.applicationquizforstudents.data.impl.FirebaseServiceImpl
import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.data.service.FirebaseService
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseDatabase() : FirebaseDatabase = FirebaseDatabase.getInstance("https://quizapplication-e5c26-default-rtdb.europe-west1.firebasedatabase.app/")
    @Provides
    @Singleton
    fun provideFirebaseStorage():FirebaseStorage = FirebaseStorage.getInstance()
}