import client.Repository
import model.UserType
import users.User

class UserViewModel(
    private val repository: Repository
) {
    var user: User? = User(type = UserType.Teacher.ordinal, name = "", email = "")
//        get() {
//            //// get from shared pref, if null, navigate user to Auth Screen
//        }

}