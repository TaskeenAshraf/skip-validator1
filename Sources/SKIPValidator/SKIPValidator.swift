#if !SKIP
import Foundation
#else
import SkipFoundation
#endif


public struct SKIPValidatorModule {
    /// The shared skip module.
//    public static let shared = SKIPValidatorModule()
    

    public static func validateEmail(email:String) -> Bool {
        
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        // SKIP REPLACE:
        // val regex = Regex(pattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}")
        let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
        // SKIP REPLACE:
        // return regex.containsMatchIn(email)
        return emailPred.evaluate(with: email)
    }
    

     
}
