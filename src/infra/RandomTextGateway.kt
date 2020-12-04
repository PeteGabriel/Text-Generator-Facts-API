package com.mkt.task.infra
import com.google.gson.Gson
import com.mkt.task.infra.pojos.RandomApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class RandomTextGateway: IRandomTextGateway {

    private val uri: String = "http://www.randomtext.me/api/gibirish"
    private val typeOfText = "p"

    override suspend fun getRandomParagraphs(range: IntRange, minWords: Int, maxWords: Int): RandomApiResponse {

        //TODO do it in cycles using the range

        return withContext(Dispatchers.IO) {
            val client = HttpClient.newBuilder().build()
            val request = HttpRequest.newBuilder()
                    .uri(URI.create("${uri}/${typeOfText}-5/${minWords}-${maxWords}"))
                    .build()
            val response: HttpResponse<String>?
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString())
            } catch (ioe: IOException) {
                //"Error requesting third party"
                // TODO handle this better
                throw ioe
            }
            return@withContext Gson().fromJson(response?.body()!!, RandomApiResponse::class.java)
        }
    }

}