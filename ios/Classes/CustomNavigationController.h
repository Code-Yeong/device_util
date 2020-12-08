//
//  CustomNavigationController.h
//  app_common
//
//  Created by qianzhao on 2018/7/31.
//

#import <UIKit/UIKit.h>

#define ColorValue(rgbValue) [UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 green:((float)((rgbValue & 0xFF00) >> 8))/255.0 blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]
#define COLOR_MAIN_PURPLE       ColorValue(0xa04cf7)//purple

@interface CustomNavigationController : UINavigationController

@end
