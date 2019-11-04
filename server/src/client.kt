package ca.udes
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import java.net.InetSocketAddress
import kotlinx.coroutines.*
import kotlinx.coroutines.io.*

class client {
    suspend fun main() {
        runBlocking {
            val socket =
                aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(InetSocketAddress("192.168.43.174", 9002))
            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = true)

            output.writeStringUtf8("{\"objectType\": \"request\",\"objectRequested\" : \"Match\",\"idObjectRequested\" : \"1\"}\n")

            //text_match_1.setText("ede")
            val response = input.readUTF8Line()

            println("Server said: '$response'")
        }
    }
}