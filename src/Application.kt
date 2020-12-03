package com.mkt.task

import com.mkt.task.controllers.TextController
import com.mkt.task.domain.TextDataService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val ctrl = TextController(TextDataService())

    routing {
        get("/text") {
            try{
                //assign default values
                val pStart = call.request.queryParameters["pStart"]?.toInt() ?: 1
                val pEnd = call.request.queryParameters["pEnd"]?.toInt() ?: 1
                val wordCountMin = call.request.queryParameters["wCountMin"]?.toInt() ?: 1
                val wordCountMax = call.request.queryParameters["wCountMax"]?.toInt() ?: 1

                //TODO validate accepted values for query parameters

                call.respond(ctrl.getTextData(pStart, pEnd, wordCountMin, wordCountMax))
            }catch(nfe: NumberFormatException){
                //log
                log.warn(nfe.toString())
                //Bad request
                call.respond(HttpStatusCode.BadRequest, "invalid parameters")
            }
        }

        get("/history") {
            call.respondText { "History endpoint" }
        }
    }
}

