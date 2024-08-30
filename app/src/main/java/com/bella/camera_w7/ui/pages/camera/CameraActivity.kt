package com.bella.camera_w7.ui.pages.camera

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bella.camera_w7.R
import com.bella.camera_w7.databinding.ActivityCameraBinding
import com.bella.camera_w7.ui.core.BaseActivity
import com.bella.camera_w7.ui.extension.gone
import com.bella.camera_w7.ui.extension.visible
import com.bella.camera_w7.ui.pages.camera.util.BarcodeAnalyzer
import com.bella.camera_w7.ui.pages.camera.util.createToast
import com.bella.camera_w7.ui.pages.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : BaseActivity() {

    private val viewModel: CameraViewModel by viewModels()
    private lateinit var binding: ActivityCameraBinding

    private var imageCapture: ImageCapture? = null

    private val cameraExecutor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value) {
                    permissionGranted = false
                }
            }

            if (!permissionGranted) {
                requestPermissions()
            } else {
                startCamera()
            }
        }

    private var animationPlayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnImageCapture.setOnClickListener { lifecycleScope.launch { takePhoto() } }

        lifecycleScope.launch {
            val bitmap = viewModel.getNewestImage(this@CameraActivity)
            setImageButton(bitmap)
        }

        binding.btnImageGallery.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.animate_slide_up_enter,
                R.anim.animate_slide_up_exit
            )
            startActivity(intent, options.toBundle())
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        try {
            imageCapture.takePicture(
                ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        val bitmap = image.toBitmap()

                        val matrix = Matrix().apply {
                            postRotate(image.imageInfo.rotationDegrees.toFloat())
                        }

                        val rotatedBitmap = Bitmap.createBitmap(
                            bitmap,
                            0,
                            0,
                            image.width,
                            image.height,
                            matrix,
                            true
                        )

                        image.close()

                        setImageButton(rotatedBitmap)

                        lifecycleScope.launch {
                            viewModel.saveToDatabase(rotatedBitmap)
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        createToast(this@CameraActivity, R.string.photo_capture_failed)
                    }
                }
            )
        } catch (e: Exception) {
            createToast(this, R.string.photo_capture_failed)
        }
    }

    private fun setImageButton(bitmap: Bitmap) {
        binding.btnImageGallery.setImageBitmap(bitmap)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .setTargetRotation(binding.viewFinder.display.rotation)
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetRotation(binding.viewFinder.display.rotation)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer(
                        onBarcodeDetected = { rawValue ->
                            if (rawValue == BarcodeAnalyzer.imageIsNull) {
                                createToast(this, R.string.image_is_null)
                                return@BarcodeAnalyzer
                            }

                            playAnimation(rawValue)
                        }
                    ))
                }

            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.viewFinder.display.rotation)
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis, imageCapture
                )

            } catch (exc: Exception) {
                createToast(this, R.string.camera_start_failed)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun requestPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            AlertDialog.Builder(this)
                .setMessage(getString(R.string.camera_permission_is_required_to_take_photos_please_enable_it_in_the_app_settings))
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    activityResultLauncher.launch(REQUIRED_PERMISSIONS)
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                    Snackbar.make(
                        binding.root,
                        getString(R.string.camera_permission_is_required_to_take_photos_please_enable_it_in_the_app_settings),
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.ok)) {
                        dialog.dismiss()
                        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
                    }.show()
                }
                .show()
        } else {
            openSettings()
        }
    }

    private fun openSettings() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.camera_permission_is_required_to_take_photos_please_enable_it_in_the_app_settings))
            .setPositiveButton(getString(R.string.settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .show()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun playAnimation(rawValue: String) {
        if (!animationPlayed) {
            val anim = binding.lottieAnimationView
            Handler(Looper.getMainLooper()).postDelayed({
                anim.visible()
                anim.playAnimation()

                animationPlayed = true

                Handler(Looper.getMainLooper()).postDelayed({
                    showAlertDialog(rawValue)
                }, 3000) // Add a delay after animation before showing the AlertDialog
            }, 1000)
        }
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
                binding.lottieAnimationView.gone()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onResume() {
        super.onResume()
        if (!allPermissionsGranted()) {
            requestPermissions()
        } else {
            startCamera()
        }
    }

    companion object {
        const val TAG = "CameraXApp"
        private val REQUIRED_PERMISSIONS = mutableListOf(
            Manifest.permission.CAMERA
        ).toTypedArray()
    }
}