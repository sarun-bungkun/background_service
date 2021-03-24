#include "AppDelegate.h"
#include "GeneratedPluginRegistrant.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application
    didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
  [GeneratedPluginRegistrant registerWithRegistry:self];
  // Override point for customization after application launch.
  UIApplication.shared.setMinimumBackgroundFetchInterval(UIApplication.backgroundFetchIntervalMinimum)

  return [super application:application didFinishLaunchingWithOptions:launchOptions];
}

func application(_ application: UIApplication, performFetchWithCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        // Create url which from we will get fresh data
        if let url = URL(string: "https://www.vialyx.com") {
            // Send request
            URLSession.shared.dataTask(with: url, completionHandler: { (data, respone, error) in
                // Check Data
                guard let `data` = data else { completionHandler(.failed); return }
                // Get result from data
                let result = String(data: data, encoding: .utf8)
                // Print result into console
                print("performFetchWithCompletionHandler result: \(String(describing: result))")
                // Call background fetch completion with .newData result
                completionHandler(.newData)
            }).resume()
        }
    }

@end
