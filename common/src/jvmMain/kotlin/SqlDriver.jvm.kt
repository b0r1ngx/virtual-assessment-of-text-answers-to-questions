import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.boringx.Database

actual class SqlDriverFactory {
    actual fun createDriver(): SqlDriver {
        // when run tests, possible issue, that path not exists, replace start ./ with ../
        val pathToDatabaseFile = "./backend/src/main/database/va.db"
        val url = "jdbc:sqlite:$pathToDatabaseFile"
        val driver: SqlDriver = JdbcSqliteDriver(url = url)
        Database.Schema.create(driver)
        return driver
    }
}