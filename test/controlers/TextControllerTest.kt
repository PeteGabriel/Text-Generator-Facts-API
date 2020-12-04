package controlers

import com.google.gson.Gson
import com.mkt.task.controllers.errors.Problem
import com.mkt.task.domain.model.TextData
import com.mkt.task.module
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TextControllerTest {

    @Test
    fun testTextGenerationWithoutQuery() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/text").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue(response.content?.length!! > 0)

                val cnt = Gson().fromJson(response.content, TextData::class.java)
                assertTrue(cnt.freqWord.isNotBlank())
                assertEquals(-1, cnt.avgParagraphSize)
                assertEquals(-1, cnt.avgParagraphProcessingTime)
                assertEquals(-1, cnt.totalProcessingTime)
            }
        }
    }

    @Test
    fun testTextGenerationWithQueryNAN() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/text?pStart=NAN").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertTrue(response.content?.length!! > 0)

                val problem = Gson().fromJson(response.content, Problem::class.java)
                assertEquals("Invalid type of parameter", problem.title)
                assertEquals("Parameters supplied must be numbers", problem.detail)
                assertEquals(HttpStatusCode.BadRequest, problem.status)
                //assertEquals("application/problem+json", response.headers["Content-Type"])
            }
        }
    }

    @Test
    fun testTextGenerationWithInvalidRangeValues() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/text?pStart=80").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertTrue(response.content?.length!! > 0)

                val problem = Gson().fromJson(response.content, Problem::class.java)
                assertEquals("Value out of bounds", problem.title)
                assertEquals("Parameter value is out of bounds", problem.detail)
                assertEquals(HttpStatusCode.BadRequest, problem.status)
                //assertEquals("application/problem+json", response.headers["Content-Type"])
            }
        }
    }

    @Test
    fun testTextGenerationWithInvalidRangeValues2() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/text?pStart=2&pEnd=1").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertTrue(response.content?.length!! > 0)

                val problem = Gson().fromJson(response.content, Problem::class.java)
                assertEquals("Invalid range of values", problem.title)
                assertEquals("Parameter pStart cannot be bigger than pEnd", problem.detail)
                assertEquals(HttpStatusCode.BadRequest, problem.status)
                //assertEquals("application/problem+json", response.headers["Content-Type"])
            }
        }
    }

    @Test
    fun testTextGenerationWithInvalidRangeValues3() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/text?pStart=-34").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertTrue(response.content?.length!! > 0)

                val problem = Gson().fromJson(response.content, Problem::class.java)
                assertEquals("Value out of bounds", problem.title)
                assertEquals("Parameter value is out of bounds", problem.detail)
                assertEquals(HttpStatusCode.BadRequest, problem.status)
                //assertEquals("application/problem+json", response.headers["Content-Type"])
            }
        }
    }

    @Test
    fun testTextGenerationWithInvalidRangeValues4() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/text?wCountMin=2&wCountMax=1").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertTrue(response.content?.length!! > 0)

                val problem = Gson().fromJson(response.content, Problem::class.java)
                assertEquals("Invalid range of values", problem.title)
                assertEquals("Parameter wCountMin cannot be bigger than wCountMax", problem.detail)
                assertEquals(HttpStatusCode.BadRequest, problem.status)
                //assertEquals("application/problem+json", response.headers["Content-Type"])
            }
        }
    }

}