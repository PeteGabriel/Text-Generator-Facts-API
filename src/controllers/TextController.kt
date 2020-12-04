package com.mkt.task.controllers

import com.mkt.task.controllers.errors.Problem
import com.mkt.task.domain.model.TextData
import com.mkt.task.domain.TextDataService
import io.ktor.application.*
import io.ktor.http.*

class TextController(private val svc: TextDataService) {

    suspend fun getTextData(query: Parameters): TextData {
        val pStart: Int
        val pEnd: Int
        val wordCountMin: Int
        val wordCountMax: Int

        try {
            //assign default values
            pStart = query["pStart"]?.toInt() ?: 1
            pEnd = query["pEnd"]?.toInt() ?: 1
            wordCountMin = query["wCountMin"]?.toInt() ?: 1
            wordCountMax = query["wCountMax"]?.toInt() ?: 1
        }catch(nfe: NumberFormatException){
            throw Problem(title="Invalid type of parameter",
                    detail="Parameters supplied must be numbers",
                    status=HttpStatusCode.BadRequest)
        }

        if (pStart !in 1..50 || pEnd !in 1..50 || wordCountMin !in 1..100 || wordCountMax !in 1..100) {
            throw Problem(title="Value out of bounds",
                    detail="Parameter value is out of bounds",
                    status=HttpStatusCode.BadRequest)
        }

        if (pStart > pEnd){
            throw Problem(title="Invalid range of values",
                    detail="Parameter pStart cannot be bigger than pEnd",
                    status=HttpStatusCode.BadRequest)
        }
        if (wordCountMin > wordCountMax){
            throw Problem(title="Invalid range of values",
                    detail="Parameter wCountMin cannot be bigger than wCountMax",
                    status=HttpStatusCode.BadRequest)
        }


        //ask the injected service for data
        return svc.getTextData(pStart, pEnd, wordCountMin, wordCountMax)
    }

}