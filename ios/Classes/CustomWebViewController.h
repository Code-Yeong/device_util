//
//  QBTWebViewController.h
//  app_common
//
//  Created by qianzhao on 2018/7/31.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <WebKit/WebKit.h>

@interface CustomWebViewController : UIViewController
@property(nonatomic,strong)NSString *url;

- (instancetype)initWithUrl:(NSString *)url;
- (instancetype)initWithLocalFilePath:(NSString *)filePath;

- (instancetype)initWithLocalFilePath:(NSString *)filePath withRemoteUrl:(NSString *)remoteUrl;

- (void)reloadWeb;

@end

#pragma mark -
#pragma mark QBTWKWebView
//================================================================================================
@interface QBTWKWebView : WKWebView
@end

