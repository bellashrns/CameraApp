package com.bella.camera_w7.ui.pages.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bella.camera_w7.data.local.room.entity.ImageEntity
import com.bella.camera_w7.databinding.FragmentGalleryBinding
import com.bella.camera_w7.ui.core.BaseFragment
import com.bella.camera_w7.ui.extension.gone
import com.bella.camera_w7.ui.extension.visible
import com.bella.camera_w7.ui.pages.gallery.adapter.GalleryAdapter
import kotlinx.coroutines.launch

class GalleryFragment : BaseFragment() {

    override val viewModel: GalleryViewModel by viewModels()
    private lateinit var binding: FragmentGalleryBinding

    private val adapter: GalleryAdapter by lazy {
        GalleryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.galleryRecyclerView.also {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(context, 2)
        }

        lifecycleScope.launch {
            val images = viewModel.getImages()
            showImages(images)
        }
    }

    private fun showImages(images: List<ImageEntity>) {
        if (images.isEmpty()) {
            binding.galleryRecyclerView.gone()
            binding.tvNoImages.visible()
        } else {
            adapter.submitList(images)
            binding.galleryRecyclerView.visible()
            binding.tvNoImages.gone()
        }
    }
}