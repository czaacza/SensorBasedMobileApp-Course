package com.czaacza.artrackingproject

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment

class TackImgFragment : ArFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(
            inflater, container,
            savedInstanceState
        )
        arSceneView.planeRenderer.isVisible = false
        arSceneView.planeRenderer.isEnabled = false
        instructionsController.isEnabled = false
        instructionsController.isVisible = false
        return view
    }

    override fun onCreateSessionConfig(session: Session?): Config {
        val config = super.onCreateSessionConfig(session)
        setupAugmentedImageDatabase(config, session)
        return config
    }

    private fun setupAugmentedImageDatabase(config: Config, session: Session?) {
        val augmentedImageDb = AugmentedImageDatabase(session)
        val assetManager = requireContext().assets
        listOf("green", "red", "sofa").forEach {
            val inputStream = assetManager.open("$it.jpg")
            val augmentedImageBitmap = BitmapFactory.decodeStream(inputStream)
            augmentedImageDb.addImage(it, augmentedImageBitmap, 0.1f)
        }
        config.augmentedImageDatabase = augmentedImageDb
    }

}