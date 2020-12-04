package com.mkt.task.domain.model

data class TextData(val freqWord: String,
                    val avgParagraphSize: Int,
                    val avgParagraphProcessingTime: Long,
                    val totalProcessingTime: Long)
