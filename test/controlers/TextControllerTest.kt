package com.mkt.task.controlers

import com.google.gson.Gson
import com.mkt.task.controllers.TextController
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
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/text").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertTrue(response.content?.length!! > 0)

                val cnt = Gson().fromJson(response.content, TextData::class.java)
                assertEquals(1, cnt.freqWord)
                assertEquals(-1, cnt.avgParagraphSize)

            }
        }
    }

    @Test
    fun testTextGenerationWithInvalidQuery() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/text?pStart=NAN").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertTrue(response.content?.length!! > 0)
            }
        }
    }

}