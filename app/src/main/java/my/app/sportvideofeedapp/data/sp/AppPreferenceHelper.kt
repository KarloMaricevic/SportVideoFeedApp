package my.app.sportvideofeedapp.data.sp

import android.content.Context
import android.content.SharedPreferences

class AppPreferenceHelper(appContext: Context) : PreferenceHelper {

    companion object {

        const val PREF_FILE_NAME = "PREF_FILE_NAME"
    }

    private var sp: SharedPreferences? = appContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
}
