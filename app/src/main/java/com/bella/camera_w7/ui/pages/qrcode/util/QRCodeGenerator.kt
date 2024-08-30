package com.bella.camera_w7.ui.pages.qrcode.util

import android.graphics.Bitmap
import android.widget.EditText
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

fun generateQRCode(textEt: EditText): Bitmap {
    val multiFormatWriter = MultiFormatWriter()
    val bitMatrix = multiFormatWriter.encode(
        textEt.text.toString(),
        BarcodeFormat.QR_CODE,
        300,
        300
    )
    val barcodeEncoder = BarcodeEncoder()
    val bitmap = barcodeEncoder.createBitmap(bitMatrix)

    return bitmap
}