package com.mkt.task.controllers.errors

import io.ktor.http.*
import java.lang.Exception

/**
 * The purpose of Problem is so that API consumers can be informed of both
 * the high-level error class (using the status code) and
 * the finer-grained details of the problem.
 */
data class Problem(val type:String = "about:blank",
                   val title: String,
                   val status: HttpStatusCode,
                   val detail: String,
                   val instance: String = ""): Throwable(detail)