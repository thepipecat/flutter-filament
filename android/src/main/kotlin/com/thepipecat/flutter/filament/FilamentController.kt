package com.thepipecat.flutter.filament

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.view.TextureView
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView
import java.io.IOException

class FilamentController(
    private val viewId: Int,
    private val context: Context,
    private val activity: Activity,
    private val binaryMessenger: BinaryMessenger,
) : DefaultLifecycleObserver, TextureView.SurfaceTextureListener, MethodChannel.MethodCallHandler, PlatformView {

    companion object {
        const val TAG = "FilamentController"
        private const val REQUEST_PERMISSION_CODE = 9
    }

    private lateinit var _camera: Camera
    private val _methodChannel: MethodChannel
    private val _view: TextureView
    private var _disposed = false
    private var _hasPermission = false

    init {
        MethodChannel(binaryMessenger, FilamentPlugin.VIEW_TYPE + '_' + viewId).also {
            _methodChannel = it
            it.setMethodCallHandler(this)
        }

        TextureView(context).also {
            _view = it
            it.surfaceTextureListener = this
        }
    }

    override fun onFlutterViewAttached(flutterView: View) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), REQUEST_PERMISSION_CODE)
        } else {
            _hasPermission = true
        }
    }

    override fun getView(): View = _view

    override fun dispose() {
        if (_disposed) return

        _methodChannel.setMethodCallHandler(null)

        _disposed = true
    }

    // SurfaceTextureListener

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        if (!_hasPermission) return

        _camera = Camera.open()

        try {
            _camera.setPreviewTexture(surface)
            _camera.startPreview()
        } catch (ex: IOException) {

        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) = Unit

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        if (!_hasPermission) return true

        with(_camera) {
            stopPreview()
            release()
        }

        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) = Unit

    // MethodCallHandler

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            else -> Unit
        }
    }

    // DefaultLifecycleObserver

}
