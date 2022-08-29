import com.example.kmmlearning.checkConnection
import mui.material.Card
import mui.material.Typography
import react.FC
import react.Props
import react.VFC
import react.create
import react.router.Route
import react.router.Routes
import react.router.dom.BrowserRouter

external interface AppProps : Props

val NetCheckApp = FC<AppProps> {

    BrowserRouter {
        Routes {
            Route {
                this.index = true
                this.element = MainScreenLoad.create()
                this.path = "/"
            }
        }
    }

}

val MainScreenLoad = VFC {
    Card {
        checkConnection.getNetworkStatus {
            //window.alert(if (it) "Connected" else "DisConnected")
            Typography {
                + (if (it) "You are on Line" else "You are offLine")
            }
        }
    }
}
