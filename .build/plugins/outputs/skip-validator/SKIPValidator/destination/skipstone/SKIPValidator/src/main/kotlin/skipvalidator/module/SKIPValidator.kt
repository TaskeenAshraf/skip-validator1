package skipvalidator.module

import skip.lib.*

import skip.foundation.*


class SKIPValidatorModule {




    companion object {
        /// The shared skip module.
        //    public static let shared = SKIPValidatorModule()


        fun validateEmail(email: String): Boolean {

            val emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
            val regex = Regex(pattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")
            return regex.containsMatchIn(email)
        }
    }
}
