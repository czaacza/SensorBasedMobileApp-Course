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
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var arFrag: ArFragment
    private var modelRenderable: ModelRenderable? = null

    private fun getScreenCenter(): Point {
        val vw = findViewById<View>(android.R.id.content)
        return Point(vw.width / 2, vw.height / 2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        arFrag = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment

        findViewById<Button>(R.id.btn_add_shoe).setOnClickListener {
            add3dObject()
        }
        arFrag = supportFragmentManager.findFragmentById(R.id.sceneform_fragment) as ArFragment

        ModelRenderable.builder()
            .setSource(this, Uri.parse("climbingshoe.glb")).setIsFilamentGltf(true)
            .setAsyncLoadEnabled(true).setRegistryId("climbingshoe")
            .build()
            .thenAccept { modelRenderable = it }.exceptionally {
                Log.e("DBG", "something went wrong ${it.localizedMessage}")
                null
            }

    }

    private fun add3dObject() {
        val frame = arFrag.arSceneView.arFrame
        if (frame != null && modelRenderable != null) {
            val pt = getScreenCenter()

            val hits = frame.hitTest(pt.x.toFloat(), pt.y.toFloat())
            for (hit in hits) {
                val trackable = hit.trackable
                if (trackable is Plane) {
                    val anchorNode = AnchorNode(hit!!.createAnchor())
                    anchorNode.parent = arFrag.arSceneView.scene

                    val mNode = TransformableNode(arFrag.transformationSystem)
                    mNode.renderable = modelRenderable
                    mNode.scaleController.minScale = 0.05f
                    mNode.scaleController.maxScale = 2.0f
                    mNode.localScale = Vector3(0.05f, 0.05f, 0.05f)
                    mNode.parent = anchorNode

                    mNode.setOnTapListener { hitTestResult, motionEvent_ ->
                        Log.d("DBG", "Touched")
                        findViewById<Button>(R.id.btn_add_shoe).isVisible = false
                    }

                    mNode.select()
                    break
                }
            }
        }
    }

}