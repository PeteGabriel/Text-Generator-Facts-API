package com.mkt.task.controllers

import com.mkt.task.domain.model.TextData
import com.mkt.task.domain.TextDataService

class TextController(private val svc: TextDataService) {

    fun getTextData(pStart: Int,
                    pEnd: Int,
                    wordCountMin: Int,
                    wordCountMax: Int): TextData {
        //ask the injected service for data
        return svc.getTextData(pStart, pEnd, wordCountMin, wordCountMax)
    }

}