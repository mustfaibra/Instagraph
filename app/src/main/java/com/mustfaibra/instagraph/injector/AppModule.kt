package com.mustfaibra.instagraph.injector

import android.content.Context
import com.mustfaibra.instagraph.data.fake.FakeServicesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 3/20/2022

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** A function to provide a single Context's instance of the application throughout our app */
    @Provides
    @Singleton
    fun provideContextInstance(@ApplicationContext cxt: Context) = cxt

    /** A function to provide a single instance of FakeServicesImp */
    @Provides
    @Singleton
    fun provideServicesIm() = FakeServicesImpl()
}