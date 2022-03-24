package com.applaudostudios.karcore.Activities

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.applaudostudios.karcore.R
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment

class PreviewActivity : AppCompatActivity() {
    lateinit var scene: Scene
    lateinit var sceneView: SceneView

    lateinit var cupCakeNode: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        sceneView = findViewById(R.id.scene_view)
        scene = sceneView.scene // get current scene
        renderObject(Uri.parse("model.sfb")) // Render the object
    }

    private fun renderObject(parse: Uri) {
        ModelRenderable.builder()
            .setSource(this, parse)
            .build()
            .thenAccept {
                addNodeToScene(it)
            }
            .exceptionally {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(it.message)
                    .setTitle("error!")
                val dialog = builder.create()
                dialog.show()
                return@exceptionally null
            }

    }

    private fun addNodeToScene(model: ModelRenderable?) {
        model?.let {
            cupCakeNode = Node().apply {
                setParent(scene)
                localPosition = Vector3(0f, -1f, -1f)
                localScale = Vector3(3f, 3f, 3f)
                name = "model"
                renderable = it
            }

            scene.addChild(cupCakeNode)
        }
    }

    override fun onPause() {
        super.onPause()
        sceneView.pause()
    }

    override fun onResume() {
        super.onResume()
        sceneView.resume()
    }

}