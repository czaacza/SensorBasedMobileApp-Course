package com.czaacza.arcoreproject

import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import com.czaacza.arcoreproject.databinding.ActivityMainBinding
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var arFrag: ArFragment
    private var viewRenderable: ViewRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arFrag = supportFragmentManager.findFragmentById(R.id.fragArImg) as ArFragment


        ViewRenderable.builder()
            .setView(this, R.layout.rend_text)
            .build()
            .thenAccept { viewRenderable = it }

    }


}