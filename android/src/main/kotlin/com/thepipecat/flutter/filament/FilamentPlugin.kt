package com.thepipecat.flutter.filament

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.embedding.engine.plugins.lifecycle.HiddenLifecycleReference
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class FilamentPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    companion object {
        const val VIEW_TYPE = "filament.flutter.thepipecat.com/filament_view"
    }

    private lateinit var channel: MethodChannel
    private var lifecycle: Lifecycle? = null
    private lateinit var pluginBinding: FlutterPlugin.FlutterPluginBinding

    // FlutterPlugin

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(binding.binaryMessenger, "filament")
        channel.setMethodCallHandler(this)

        pluginBinding = binding
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    // MethodCallHandler

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else {
            result.notImplemented()
        }
    }

    // ActivityAware

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        lifecycle = (binding.lifecycle as? HiddenLifecycleReference)?.lifecycle

        pluginBinding.platformViewRegistry
            .registerViewFactory(VIEW_TYPE, FilamentFactory(binding.activity, pluginBinding.binaryMessenger))
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onDetachedFromActivity() {
        lifecycle = null
    }

}
