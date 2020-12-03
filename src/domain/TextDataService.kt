package com.mkt.task.domain

import com.mkt.task.domain.model.TextData

class TextDataService {

    fun getTextData(pStart: Int,
                    pEnd: Int,
                    wordCountMin: Int,
                    wordCountMax: Int): TextData {
        //TODO access Infra layer to request data from source
        return TextData(-1, -1, -1, -1)
    }

}