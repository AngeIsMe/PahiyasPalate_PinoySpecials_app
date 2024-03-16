import android.content.Context
import android.content.SharedPreferences
import com.domondon.angeline.block4.p1.pahiyaspalate.model.User

class SharedPrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "SharedPrefManager"
        private const val KEY_USER_ID = "userId"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"

        @Volatile
        private var INSTANCE: SharedPrefManager? = null

        fun getInstance(context: Context): SharedPrefManager {
            return INSTANCE ?: synchronized(this) {
                val instance = SharedPrefManager(context)
                INSTANCE = instance
                instance
            }
        }
    }

    fun userLogin(user: User) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_USER_ID, user.id)
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_EMAIL, user.email)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getInt(KEY_USER_ID, -1) != -1
    }

    fun getUser(): User {
        val userId = sharedPreferences.getInt(KEY_USER_ID, -1)
        val username = sharedPreferences.getString(KEY_USERNAME, "") ?: ""
        val email = sharedPreferences.getString(KEY_EMAIL,"") ?:""
        return User(userId, username,email)
    }

    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
