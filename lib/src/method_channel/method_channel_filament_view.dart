import 'package:filament/src/platform_interface/filament_view_platform.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

class MethodChannelFilamentView extends FilamentViewPlatform {
  @override
  Widget buildView(
    int creationId,
    PlatformViewCreatedCallback onPlatformViewCreated,
  ) {
    const filamentViewType = 'filament.flutter.thepipecat.com/filament_view';

    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return AndroidView(viewType: filamentViewType);
      case TargetPlatform.iOS:
        return UiKitView(viewType: filamentViewType);
      default:
        return Text(
            '$defaultTargetPlatform is not yet implemented by Filament plugin.');
    }
  }
}
