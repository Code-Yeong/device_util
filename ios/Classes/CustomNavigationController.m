//
//  CustomNavigationController.m
//  app_common
//
//  Created by qianzhao on 2018/7/31.
//

#import "CustomNavigationController.h"

@interface CustomNavigationController ()

@end

@implementation CustomNavigationController

- (void) dismissNavigator:(id)sender{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)viewWillAppear:(BOOL)animated{
    self.navigationBar.backgroundColor = [UIColor whiteColor];
    self.navigationBar.tintColor = COLOR_MAIN_PURPLE;
    self.navigationBar.translucent = NO;
    UIBarButtonItem *barItem = [[UIBarButtonItem alloc] initWithTitle:@"完成" style:UIBarButtonItemStyleDone target:self action:@selector(dismissNavigator:)];
    self.navigationBar.topItem.rightBarButtonItems = @[barItem];
    [super viewWillAppear:animated];
}
- (void)viewDidLoad {
    [super viewDidLoad];
}

@end
