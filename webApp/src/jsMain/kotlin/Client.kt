import com.example.kmmlearning.checkConnection
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

fun main() {
  val container = document.createElement("div")
  document.body!!.appendChild(container)

    checkConnection.getNetworkStatus {
      console.log("Status",it)
      val welcome = Welcome.create {
        name = "You are " + if (it) "Connected" else "DisConnected"
      }
      createRoot(container).render(welcome)
    }
}