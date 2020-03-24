package my.app.sportvideofeedapp.data.sp

import android.content.Context
import android.content.SharedPreferences
import my.app.sportvideofeedapp.EXO_PREF_NAME
import my.app.sportvideofeedapp.di.qualifiers.AppContext
import javax.inject.Inject

class AppPreferenceHelper @Inject constructor(@AppContext appContext: Context) {

    companion object {
        const val PREF_FILE_NAME = EXO_PREF_NAME
    }

    private var sp: SharedPreferences? =
        appContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    fun putIntData(key: String, value: Int) {
        sp?.edit()?.putInt(key, value)?.apply()
    }

    fun getIntData(key: String, defVal: Int): Int {
        return sp?.getInt(key, defVal) ?: defVal
    }

    fun putLongData(key: String, value: Long) {
        sp?.edit()?.putLong(key, value)?.apply()
    }

    fun getLongData(key: String, defVal: Long): Long {
        return sp?.getLong(key, defVal) ?: defVal
    }

    fun putStringData(key: String, value: String?) {
        sp?.edit()?.putString(key, value)?.apply()
    }

    fun getStringData(key: String, defVal: String?): String? {
        return sp?.getString(key, defVal)
    }

    fun putBooleanData(key: String, value: Boolean) {
        sp?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBooleanData(key: String, defVal: Boolean): Boolean {
        return sp?.getBoolean(key, defVal) ?: defVal
    }

    fun removeData(key: String) {
        sp?.edit()?.remove(key)?.apply()
    }
}
