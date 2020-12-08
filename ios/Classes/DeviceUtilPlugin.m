#import "DeviceUtilPlugin.h"
#import "CustomWebViewController.h"
#import "CustomNavigationController.h"
#import <Flutter/Flutter.h>
@interface DeviceUtilPlugin()
@property (nonatomic, retain) UIViewController *viewController;
@property (nonatomic, strong) FlutterEngine *engine;
@end
@implementation DeviceUtilPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"device_util"
            binaryMessenger:[registrar messenger]];
    UIViewController *viewController =
    [UIApplication sharedApplication].delegate.window.rootViewController;
    DeviceUtilPlugin* instance = [[DeviceUtilPlugin alloc] initWithViewController:viewController];
    if ([registrar.messenger isKindOfClass:[FlutterEngine class]]){
        instance.engine = (FlutterEngine *)registrar.messenger;
    }
  [registrar addMethodCallDelegate:instance channel:channel];
}
- (instancetype)initWithViewController:(UIViewController *)viewController {
    self = [super init];
    if (self) {
        self.viewController = viewController;
    }
    return self;
}
- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else if([@"getVersionName" isEqualToString:call.method]){
      NSDictionary *infoDictionary = [[NSBundle mainBundle] infoDictionary];
      NSString *majorVersion = [infoDictionary objectForKey:@"CFBundleShortVersionString"];
      result(majorVersion);
  }else if ([@"getVersionCode" isEqualToString:call.method]){
      NSDictionary *infoDictionary = [[NSBundle mainBundle] infoDictionary];
      NSString *minorVersion = [infoDictionary objectForKey:@"CFBundleVersion"];
      result(minorVersion);
  }else if([@"launchNoNetwork" isEqualToString:call.method]){
      NSString* filePath = [[[NSBundle bundleForClass:[self class]] bundlePath] stringByAppendingPathComponent:@"network_warning.html"];
      CustomWebViewController *newVC = [[CustomWebViewController alloc] initWithLocalFilePath:filePath];
      newVC.view.backgroundColor = UIColor.whiteColor;
      UINavigationController *nav = [[CustomNavigationController alloc] initWithRootViewController:newVC];
      newVC.title = @"网络设置帮助";
      [self.viewController presentViewController:nav animated:NO completion:nil];
      result(nil);
  }else if([@"systemSettingPage" isEqualToString:call.method]){
      [UIApplication.sharedApplication openURL:[NSURL URLWithString:UIApplicationOpenSettingsURLString]];
      result(nil);
  } else if([@"getChannelInfo" isEqualToString:call.method]){
     result(@{
              @"first_install_channel": @"app store",
              @"current_install_channel": @"app store"
             });
  }else  {
    result(FlutterMethodNotImplemented);
  }
}

@end
