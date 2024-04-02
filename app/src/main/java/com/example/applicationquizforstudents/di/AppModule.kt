package com.example.applicationquizforstudents.di

import com.example.applicationquizforstudents.data.impl.AccountServiceImpl
import com.example.applicationquizforstudents.domain.repository.AccountService
import dagger.Provides
import javax.inject.Singleton

object AppModule {
    @Provides
    @Singleton
    fun providesAccountService(): AccountService = AccountServiceImpl()
}