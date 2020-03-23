package my.app.sportvideofeedapp.utlis.converters

object SecondsToMinutesConverter{
    private const val secondsInMinute = 60
    private const val allNumbersPositiveAndNot2DigitNumberAreLessThen = 10

    fun convertSecondsToMinutesAndSeconds(lengthInSeconds: String): String {

        val pattern = """\d+"""
        if (!Regex(pattern).containsMatchIn(lengthInSeconds)) {
            throw IllegalArgumentException("""Argument must match regex pattern /\d+/""")
        }
        val minutes = lengthInSeconds.toInt() / secondsInMinute
        val seconds = lengthInSeconds.toInt().rem(secondsInMinute)
        return if (seconds < allNumbersPositiveAndNot2DigitNumberAreLessThen) {
            "$minutes:0$seconds"
        } else {
            "$minutes:$seconds"
        }
    }
}
