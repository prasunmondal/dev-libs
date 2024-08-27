package com.prasunmondal.dev.libs.gsheet.integrationTests

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import java.io.Serializable


class NameTitleDataHandler(context: Context) : Serializable, GSheetSerialized<NameTitleModel>(
    context, ProjectConfig.dBServerScriptURL,
    ProjectConfig.DB_SHEET_ID,
    "TestSheet1", classTypeForResponseParsing = NameTitleModel::class.java,
    appendInServer = true,
    appendInLocal = true)

class NameTitleModel(name:String,title:String): Serializable {
    val name:String=name;
    val title:String=title;
    override fun toString(): String {
        return "NameTitleClassModel(name='$name', title='$title')"
    }
}
