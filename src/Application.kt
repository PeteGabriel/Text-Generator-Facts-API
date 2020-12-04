package com.mkt.task

import com.mkt.task.controllers.TextController
import com.mkt.task.controllers.errors.Problem
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
fun Application.module() {
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

                call.respond(ctrl.getTextData(call.parameters))

            }catch(e: Problem){
                log.warn(e.detail)
                //call.response.header("Content-Type", "application/problem+json")
                call.respond(status=e.status, e)
            }
        }

        get("/history") {
            call.respondText { "History endpoint" }
        }
    }
}