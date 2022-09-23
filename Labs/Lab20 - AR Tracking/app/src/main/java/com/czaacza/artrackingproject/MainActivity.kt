package com.czaacza.artrackingproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.czaacza.artrackingproject.databinding.ActivityMainBinding
import com.google.ar.core.AugmentedImage
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    private lateinit var arFrag: ArFragment
    private var viewRenderable: ViewRenderable? = null

    private lateinit var binding: ActivityMainBinding

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

    override fun onResume() {
        super.onResume()
        arFrag.arSceneView.scene.addOnUpdateListener {
            frameUpdate()
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun frameUpdate() {
        Log.d("DBG", "frameUpdate()")
        val arFrame = arFrag.arSceneView.arFrame
        if (arFrame == null || arFrame.camera.trackingState != TrackingState.TRACKING)
            return
        val updatedAugmentedImages = arFrame.getUpdatedTrackables(AugmentedImage::class.java)

        updatedAugmentedImages.forEach {
            when (it.trackingState) {
                null -> return@forEach

                TrackingState.PAUSED -> {
                    val text = getString(R.string.detected_img_need_more_info, it.name)
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                }

                TrackingState.STOPPED -> {
                    val text = getString(
                        R.string.track_stop,
                        it.name
                    )
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                }

                TrackingState.TRACKING -> {
                    val anchors = it.anchors

                    if (anchors.isEmpty()) {
                        findViewById<ImageView>(R.id.fitToScanImg).visibility = View.GONE

                        val pose = it.centerPose
                        val anchor = it.createAnchor(pose)
                        val anchorNode = AnchorNode(anchor)

                        anchorNode.parent = arFrag.arSceneView.scene

                        val imgNode = TransformableNode(arFrag.transformationSystem)
                        imgNode.parent = anchorNode

                        viewRenderable?.view?.findViewById<TextView>(
                            R.id.txtImgTrack
                        )?.text = it.name

                        imgNode.localRotation = Quaternion.axisAngle(
                            Vector3(
                                1f, 0f,
                                0f
                            ), -90f
                        )

                        imgNode.renderable = viewRenderable
                    }
                }
            }
        }
    }
}