import Foundation

extension Foundation.Bundle {
    static let module: Bundle = {
        let mainPath = Bundle.main.bundleURL.appendingPathComponent("skip-unit_SkipUnit.bundle").path
        let buildPath = "/Users/taskeenashraf/Downloads/newSKIPFramework/skip-validator/.build/arm64-apple-macosx/debug/skip-unit_SkipUnit.bundle"

        let preferredBundle = Bundle(path: mainPath)

        guard let bundle = preferredBundle ?? Bundle(path: buildPath) else {
            // Users can write a function called fatalError themselves, we should be resilient against that.
            Swift.fatalError("could not load resource bundle: from \(mainPath) or \(buildPath)")
        }

        return bundle
    }()
}