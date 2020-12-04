package com.mkt.task.infra

import com.mkt.task.infra.pojos.RandomApiResponse

interface IRandomTextGateway {

    /**
     * Retrieve random text of type paragraph from the RandomText api.
     *
     * @param minWords Minimum number of words per paragraph.
     * @param maxWords Maximum number of words per paragraph.
     */
    suspend fun getRandomParagraphs(range: IntRange, minWords: Int, maxWords: Int): RandomApiResponse
}