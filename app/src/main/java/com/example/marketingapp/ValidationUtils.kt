package com.example.marketingapp

object ValidationUtils {
    fun isValidName(name: String?): Boolean {
        return !name.isNullOrEmpty() && name.length > 3
    }

    fun isValidEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String?): Boolean {
        val phonePattern = Regex("^01[0,1,2,5]\\d{8}\$")
        return !phone.isNullOrEmpty() && phone.matches(phonePattern)
    }

    fun isValidPassword(password: String?): Boolean {
        if (password.isNullOrEmpty() || password.length < 6) return false
        val containsNumber = password.any { it.isDigit() }
        val containsChar = password.any { it.isLetter() }
        val containsSpecialChar = password.any { !it.isLetterOrDigit() }
        return containsNumber && containsChar && containsSpecialChar
    }

    fun isPasswordConfirmed(password: String?, confirmPassword: String?): Boolean {
        return password == confirmPassword
    }
}
