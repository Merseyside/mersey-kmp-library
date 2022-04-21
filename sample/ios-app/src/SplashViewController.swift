//
//  LibTest.swift
//  ios-app
//
//  Created by Ivan Sablin on 03.04.2022.
//  Copyright © 2022 IceRock Development. All rights reserved.
//

import Foundation
import MultiPlatformLibrary
import UIKit


class SplashViewController: UIViewController {
    
    override func viewDidLoad() { // should be moved to base view model controller
        super.viewDidLoad()
        
        let csc: ConnectionStateChecker = ConnectionStateChecker()
        
        
        csc.addNetworkStateCallback(callback: { (state: KotlinBoolean) -> Void in
            Logger.shared.log(tag: "network", msg: "network state \(state.boolValue)")
        })
        
        csc.start()
    }
    
}
