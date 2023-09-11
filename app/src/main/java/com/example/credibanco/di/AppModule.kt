package com.example.credibanco.di

import android.app.Application
import androidx.room.Room
import com.example.credibanco.data.PaymentApi
import com.example.credibanco.data.database.AppDatabase
import com.example.credibanco.data.database.dao.TransactionDao
import com.example.credibanco.data.repository_implementation.RepositoryImpl
import com.example.credibanco.domain.repository_definition.Repository
import com.example.credibanco.domain.use_case.*
import com.example.credibanco.presentation.viewModel.TransactionViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Define las dependencias a inyectar
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DATABASE_NAME = "app_database"

    /**
     * Provee la instancia del base de datos
     */
    @Singleton
    @Provides
    fun provideRoom( context: Application) =
        synchronized(this){
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }

    /**
     * Provee la instancia del base de datos
     */
    @Singleton
    @Provides
    fun provideAppDao(db: AppDatabase) = db.transactionDao

    /**
     * Provee la instancia del api
     */
    @Provides
    @Singleton
    fun provideCeibaApi(): PaymentApi {
        return Retrofit.Builder()
            .baseUrl("http://192.168.3.38:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PaymentApi::class.java)
    }

    /**
     * Provee la instancia del repositorio
     */
    @Provides
    @Singleton
    fun provideRepository(transactionApi: PaymentApi, appDao: TransactionDao): Repository {
        return RepositoryImpl(transactionApi, appDao)
    }

    /**
     * Provee la instancia del caso de uso de las Autorizacciones
     */
    @Provides
    @Singleton
    fun provideAuthorizationUseCase(repository: Repository): AuthorizationUseCase {
        return AuthorizationUseCase(repository)
    }

    /**
     * Provee la instancia del caso de uso de las Anulaciones
     */
    @Provides
    @Singleton
    fun provideAnnulationUseCase(repository: Repository): AnnulationUseCase {
        return AnnulationUseCase(repository)
    }

    /**
     * Provee la instancia del caso de uso del beneficio
     */
    @Provides
    fun provideTransactionViewModel(
        authorizationUseCase: AuthorizationUseCase,
        annulationUseCase: AnnulationUseCase,
        transactionInsertUseCase: TransactionInsertUseCase,
        transactionsUseCase: TransactionsGetAllUseCase,
        transactionUpdateUseCase: TransactionUpdateUseCase
    ): TransactionViewModel {
        return TransactionViewModel(authorizationUseCase, annulationUseCase,
            transactionInsertUseCase, transactionsUseCase, transactionUpdateUseCase)
    }
}