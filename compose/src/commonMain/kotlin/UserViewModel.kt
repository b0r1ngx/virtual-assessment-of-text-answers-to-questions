import client.Repository
import users.User

class UserViewModel(
    private val repository: Repository
) {
    var user: User? = null
//        get() {
//            //// get from shared pref, if null, navigate user to Auth Screen
//        }

}