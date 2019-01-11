#import "LargeFileCopyPlugin.h"
#import <large_file_copy/large_file_copy-Swift.h>

@implementation LargeFileCopyPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftLargeFileCopyPlugin registerWithRegistrar:registrar];
}
@end
