package com.example.applicationquizforstudents.di

import com.example.applicationquizforstudents.data.impl.AccountServiceImpl
import com.example.applicationquizforstudents.data.impl.FirebaseServiceImpl
import com.example.applicationquizforstudents.data.impl.StorageServiceImpl
import com.example.applicationquizforstudents.data.service.AccountService
import com.example.applicationquizforstudents.data.service.FirebaseService
import com.example.applicationquizforstudents.data.service.StorageService
import com.google.firebase.database.FirebaseDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds
    abstract fun provideFirebaseService(impl: FirebaseServiceImpl): FirebaseService
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}