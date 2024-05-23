import client.ClientRepository
import model.UserType

class UserViewModel(
    private val repository: ClientRepository
) {
    var user: User? = User(type = UserType.Teacher.ordinal, name = "", email = "")
//        get() {
//            //// get from shared pref, if null, navigate user to Auth Screen
//        }

}