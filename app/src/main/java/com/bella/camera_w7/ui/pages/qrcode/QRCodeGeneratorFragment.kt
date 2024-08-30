package com.bella.camera_w7.ui.pages.qrcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bella.camera_w7.databinding.FragmentQrcodeGeneratorBinding
import com.bella.camera_w7.ui.core.BaseFragment
import com.bella.camera_w7.ui.core.BaseViewModel
import com.bella.camera_w7.ui.pages.qrcode.util.generateQRCode
import com.google.zxing.WriterException

class QRCodeGeneratorFragment : BaseFragment() {

    override val viewModel: BaseViewModel = BaseViewModel()
    private lateinit var binding: FragmentQrcodeGeneratorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrcodeGeneratorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textEt = binding.etQrcode
        val generateBtn = binding.btnQrcodeGenerator
        val qrCodeIv = binding.ivQrcodeResult

        generateBtn.setOnClickListener {
            try {
                val bitmap = generateQRCode(textEt)

                qrCodeIv.setImageBitmap(bitmap)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
    }
}