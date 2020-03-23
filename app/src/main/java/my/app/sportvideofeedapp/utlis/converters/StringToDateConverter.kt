package my.app.sportvideofeedapp.utlis.converters

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

object StringToDateConverter {

    private const val mDatePattern = "yyyy-MM-dd'T'HH:mm:ssX"

    fun convertStringToDate(dateString: String): Date {
        val locale = Locale.getDefault()
        val df = SimpleDateFormat(mDatePattern, locale)
        return df.parse(dateString)!!
    }
}
