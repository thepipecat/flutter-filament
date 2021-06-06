import 'package:filament/src/method_channel/method_channel_filament_view.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class FilamentViewPlatform extends PlatformInterface {
  FilamentViewPlatform() : super(token: _token);

  static final Object _token = Object();
  static FilamentViewPlatform _instance = MethodChannelFilamentView();

  static FilamentViewPlatform get instance => _instance;

  Widget buildView(
    int creationId,
    PlatformViewCreatedCallback onPlatformViewCreated,
  ) {
    throw UnimplementedError('buildView() has not been implemented.');
  }
}
