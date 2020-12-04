package com.mkt.task.domain

import com.mkt.task.domain.model.TextData
import com.mkt.task.infra.IRandomTextGateway
import com.mkt.task.infra.RandomTextGateway

class TextDataService {
    private var gateway: IRandomTextGateway = RandomTextGateway()

    suspend fun getTextData(pStart: Int,
                    pEnd: Int,
                    wordCountMin: Int,
                    wordCountMax: Int): TextData {
        //TODO access Infra layer to request data from source
        val resp = gateway.getRandomParagraphs((pStart..pEnd), wordCountMin, wordCountMax)
        return TextData(resp.format, resp.amount, -1, -1)
    }

}