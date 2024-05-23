enum class Endpoints(val path: String) {
    user("/${user.name}"),
    course("/${course.name}"),
    test("/${test.name}"),
}