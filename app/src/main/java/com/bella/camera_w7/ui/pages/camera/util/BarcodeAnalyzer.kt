package com.bella.camera_w7.ui.pages.camera.util

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.bella.camera_w7.ui.pages.camera.CameraActivity
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

class BarcodeAnalyzer(
    private val onBarcodeDetected: (String) -> Unit,
) : ImageAnalysis.Analyzer {

    private var barcodeDetected = false

    private val barcodeScanner by lazy {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()

        BarcodeScanning.getClient(options)
    }

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage == null) {
            onBarcodeDetected(imageIsNull)
            return
        }

        if (!barcodeDetected) {
            val inputImage = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )
            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue
                        barcodeDetected = true
                        if (rawValue != null) {
                            onBarcodeDetected(rawValue)
                        }
                        break // Stop processing after the first barcode is detected
                    }
                }
                .addOnFailureListener { e ->
                    Timber.tag(CameraActivity.TAG).e(e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    companion object {
        const val imageIsNull = "Image is null"
    }
}