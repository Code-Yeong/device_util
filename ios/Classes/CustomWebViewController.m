//
//  QBTWebViewController.m
//  app_common
//
//  Created by qianzhao on 2018/7/31.
//

#import "CustomWebViewController.h"

@interface CustomWebViewController ()<WKUIDelegate, WKNavigationDelegate>
@property(nonatomic,strong)NSString  *localFilePath;
@property(nonatomic,assign)BOOL needLocalAndRemote;
@property(nonatomic,strong)QBTWKWebView *webView;

@end

@implementation CustomWebViewController

- (instancetype)initWithUrl:(NSString *)url
{
    self = [super init];
    if (self) {
        self.url = url;
    }
    return self;
}

- (instancetype)initWithLocalFilePath:(NSString *)filePath
{
    self = [super init];
    if (self) {
        self.localFilePath = filePath;
    }
    return self;
}

- (instancetype)initWithLocalFilePath:(NSString *)filePath withRemoteUrl:(NSString *)remoteUrl{
    
    self = [super init];
    if (self) {
        self.localFilePath = filePath;
        self.url = remoteUrl;
        self.needLocalAndRemote = YES;
    }
    return self;
}

- (void)dealloc{
    NSLog(@"QBTWebViewController dealloc");
    self.webView.UIDelegate = nil;
    self.webView.navigationDelegate = nil;
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    [self layoutWebView];
    
    //for WKWebview
    self.edgesForExtendedLayout = UIRectEdgeNone;
    [self reloadWeb];
}

- (void)viewWillAppear:(BOOL)animated{
    
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
}

- (void)viewDidDisappear:(BOOL)animated{
    [super viewDidDisappear:animated];
}

- (void)layoutWebView{
    self.webView = [[QBTWKWebView alloc] initWithFrame:CGRectMake(0, 0, self.view.bounds.size.width, self.view.bounds.size.height)];
    self.webView.UIDelegate = self;
    self.webView.navigationDelegate = self;
    [self.view addSubview:self.webView];
}

- (void)reloadWeb {
    if (self.needLocalAndRemote && self.localFilePath.length > 0) {
        // TODO: fixMe
    }else{
        if (self.url.length > 0) {
            NSURL *nsUrl = [NSURL URLWithString:self.url];
            NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:nsUrl];
            [self.webView loadRequest:request];
        }else if(self.localFilePath.length > 0){
            NSURL *url = [NSURL fileURLWithPath:self.localFilePath];
            NSURL *baseURL = [url URLByDeletingLastPathComponent];
            [self.webView loadFileURL:url allowingReadAccessToURL:baseURL];
        }
    }
}

- (void)onBack:(id)sender{
    if ([self.webView canGoBack]) {
        [self.webView goBack];
    }else{
        [self.navigationController popViewControllerAnimated:YES];
    }
}

#pragma mark -
#pragma mark UIWebViewDelegate
//================================================================================================
- (void)didFinishNavigation:(WKNavigation *)navigation{
}

- (void)webView:(WKWebView *)webView decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler {
    NSString *urlString = [navigationAction.request.URL.absoluteString stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    
    if ([urlString hasSuffix:@"setting://qqtranslate"]) {
        NSURL *url = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
        if ([[UIApplication sharedApplication] canOpenURL:url]) {
            [[UIApplication sharedApplication] openURL:url];
        }
    }
    
    if ([urlString hasSuffix:@"setting://wifi"]) {
        NSURL *url = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
        if ([[UIApplication sharedApplication] canOpenURL:url]) {
            [[UIApplication sharedApplication] openURL:url];
        }
    }
    
    if ([urlString hasSuffix:@"setting://mobile_network"]) {
        NSURL *url = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
        if ([[UIApplication sharedApplication] canOpenURL:url]) {
            [[UIApplication sharedApplication] openURL:url];
        }
    }
    
    decisionHandler(WKNavigationActionPolicyAllow);
}

@end

#pragma mark -
#pragma mark QBTWKWebView
//================================================================================================
@implementation QBTWKWebView

- (instancetype)initWithFrame:(CGRect)frame configuration:(WKWebViewConfiguration *)configuration
{
    WKUserContentController* userContentController = [WKUserContentController new];
    configuration.userContentController = userContentController;
    configuration.preferences.javaScriptCanOpenWindowsAutomatically = true;
    if (self = [super initWithFrame:frame configuration:configuration]) {
        NSString *guid = @"test_guid";
        NSString *systemVersion = [[UIDevice currentDevice] systemVersion];
        if (guid || systemVersion) {
            NSString *jsCode = [NSString stringWithFormat:@"var translator_guid = '%@';var translator_system_version = '%@'; if(update_system_version){update_system_version()}",guid,systemVersion];
            [self evaluateJavaScript:jsCode completionHandler:^(id _Nullable object, NSError * _Nullable error) {
                NSLog(@"inject guid and system_version success..");
            }];
        }
    }
    return self;
}

- (void)dealloc{
    self.UIDelegate = nil;
}

@end
