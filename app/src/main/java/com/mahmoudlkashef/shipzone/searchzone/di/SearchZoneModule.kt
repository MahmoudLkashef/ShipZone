import com.mahmoudlkashef.shipzone.searchzone.data.api.SearchZoneApi
import com.mahmoudlkashef.shipzone.searchzone.data.dataSource.SearchZoneRemoteDataSource
import com.mahmoudlkashef.shipzone.searchzone.data.dataSource.SearchZoneRemoteDataSourceImpl
import com.mahmoudlkashef.shipzone.searchzone.data.repository.SearchZoneRepositoryImpl
import com.mahmoudlkashef.shipzone.searchzone.domain.repository.SearchZoneRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchZoneModule {

    @Provides
    @Singleton
    fun provideSearchZoneService(retrofit: Retrofit): SearchZoneApi {
        return  retrofit.create(SearchZoneApi::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
interface SearchZoneBindsModule {
    @Binds
    @Singleton
    fun bindSearchZoneRepository(
        impl: SearchZoneRepositoryImpl
    ): SearchZoneRepository

    @Binds
    @Singleton
    fun bindSearchZoneRemoteDataSource(
        impl: SearchZoneRemoteDataSourceImpl
    ): SearchZoneRemoteDataSource
}