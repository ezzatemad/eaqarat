package com.example.data.localdatabase

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.data.PropertyItem
import com.example.data.PropertyItemQueries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PropertyItem {
        val driver = AndroidSqliteDriver(PropertyItem.Schema, context, "mydatabase.db")
        return PropertyItem(driver)
    }

    @Provides
    fun provideTaskQueries(database: PropertyItem): PropertyItemQueries {
        return database.propertyItemQueries
    }


}
