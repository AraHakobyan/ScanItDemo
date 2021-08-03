package com.example.scanitdemo.koin

import com.google.gson.Gson
import org.koin.dsl.module

/**
 * Created by Ara Hakobyan on 8/11/2020.
 * Company IDT
 */
object AppModule{

//    private val dbModule = module {
//        single {
//            Room.databaseBuilder(get(), HospitalDatabase::class.java, HospitalDatabase.DB_NAME)
//                .fallbackToDestructiveMigration()
//                .build()
//        }
//        single { get<HospitalDatabase>().movementItemsDao() }
//    }


    private val splashModule = module {

    }


    private val mainModule = module {

    }


    private val gsonModule = module {
        single {
            Gson()
        }
    }

    var modules = listOf(
         splashModule,
        mainModule, gsonModule
    )

}