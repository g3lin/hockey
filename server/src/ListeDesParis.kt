package ca.udes
import java.util.concurrent.Semaphore

class ListeDesParis(var ListeDesParis: Array<Paris>) {
    val semaBets = Semaphore(1,true)

}