import Foundation
import shared

final class ViewModel: NSObject, ObservableObject {
    private let checkConnection: CheckConnection = CheckConnection()
    
    @Published var state: Bool = false
    
    override init() {
        super.init()

        checkConnection.getNetworkStatus(onConnectionChange: { status in
            self.state = status.boolValue
        })

    }
}
