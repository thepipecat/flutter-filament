
import 'dart:async';

import 'package:flutter/services.dart';

class Filament {
  static const MethodChannel _channel =
      const MethodChannel('filament');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
