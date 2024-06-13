package server

import api.server.module
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoutes() = testApplication {
        application {
            module()
        }
        helloRouteTest()
        userRouteTest()
    }

    suspend fun ApplicationTestBuilder.helloRouteTest() {
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello, world!", bodyAsText())
        }
    }

    suspend fun ApplicationTestBuilder.userRouteTest() {

    }

}
