import SwiftUI
import shared

struct ContentView: View {
    @EnvironmentObject var viewModel: ViewModel

    var body: some View {
        VStack {
            Text("Status: \(viewModel.state.description)")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
