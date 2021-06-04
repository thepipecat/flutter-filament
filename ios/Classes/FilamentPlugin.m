#import "FilamentPlugin.h"
#if __has_include(<filament/filament-Swift.h>)
#import <filament/filament-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "filament-Swift.h"
#endif

@implementation FilamentPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFilamentPlugin registerWithRegistrar:registrar];
}
@end
