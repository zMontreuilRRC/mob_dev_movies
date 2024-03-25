/*
This set of string extensions is adapted from Google's StringExt
 */


import android.util.Patterns
import java.util.regex.Pattern

private const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

fun String.validEmail(): Boolean {
    return this.isNotBlank()
}

