package my.app.sportvideofeedapp.bindingConversions

import androidx.databinding.BindingConversion
import java.text.DateFormat
import java.util.Date

object DateToStringBindingConversions {

    @BindingConversion
    @JvmStatic
    fun convertDateToString(date: Date?): String {
        return if (date == null) {
            "Not specified"
        } else {
            return DateFormat.getDateInstance().format(date)
        }
    }
}
