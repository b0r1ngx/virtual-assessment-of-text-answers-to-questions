import app.cash.sqldelight.db.SqlDriver
import dev.boringx.Database

expect class SqlDriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(sqlDriverFactory: SqlDriverFactory): Database {
    return Database(sqlDriverFactory.createDriver())
}