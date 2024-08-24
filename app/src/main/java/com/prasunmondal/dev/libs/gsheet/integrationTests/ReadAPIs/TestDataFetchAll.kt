package com.prasunmondal.dev.libs.gsheet.integrationTests.ReadAPIs

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.integrationTests.NameTitleDataHandler
import com.prasunmondal.dev.libs.gsheet.integrationTests.NameTitleModel
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class TestDataFetchAll {

    fun main(context: Context)
    {
//        prepareData(context)
//        fetchValidData(context)
//        fetchEmptyData(context)
    }

//    fun prepareData(context:Context){
//        val data:ArrayList<NameTitleModel> =ArrayList();
//        data.add(NameTitleModel("name1","title1"))
//        data.forEach{ item ->
//            NameTitleDataHandler(context).insertObject(item)
//        }
//        GScript.execute(ProjectConfig.dBServerScriptURL)
//    }
//
//    fun fetchValidData(context: Context) {
//       val name: List<NameTitleModel> = NameTitleDataHandler(context).fetch()
//        name.forEach{item->
//            LogMe.log(item.name);
//        }
//    }
//
//    fun fetchEmptyData(context: Context){
//        NameTitleDataHandler(context).queueDeleteAll()
//        GScript.execute(ProjectConfig.dBServerScriptURL);
//        val name: List<NameTitleModel> = NameTitleDataHandler(context).fetch(false)
//        name.forEach{item->
//            LogMe.log(item.name);
//        }
//    }
}