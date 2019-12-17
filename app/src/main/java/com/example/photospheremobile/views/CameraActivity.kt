package com.example.photospheremobile.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.photospheremobile.R
import com.example.photospheremobile.service.ImageSetServiceImpl
import com.example.photospheremobile.views.fragments.DialogNewImageSet
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.result.WhenDoneListener
import io.fotoapparat.selector.*
import io.fotoapparat.view.CameraView
import kotlinx.android.synthetic.main.content_camera.*
import java.io.File
import java.util.*


class CameraActivity : AppCompatActivity() {

    private var fotoapparat: Fotoapparat? = null
    private val filename = "test"


    private var fotoapparatState: FotoapparatState? = null
    var cameraStatus: CameraState? = null
    var flashState: FlashState? = null
    var exposure: ExposureSelector? = null


    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_camera)
        createFotoapparat()

        cameraStatus = CameraState.BACK
        flashState = FlashState.OFF
        fotoapparatState = FotoapparatState.OFF


        fab_camera.setOnClickListener {
            takePhoto()
            Log.i("Terminou", "SIM")
        }

        back_home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createFotoapparat() {
        val cameraView = findViewById<CameraView>(R.id.camera_view)

        fotoapparat = Fotoapparat(
            context = this,
            view = cameraView,
            scaleType = ScaleType.CenterCrop,
            lensPosition = back(),
            logger = loggers(
                logcat().also { highestExposure() }.also { lowestExposure() }
            ),
            cameraErrorCallback = { error ->
                println("Recorder errors: $error")
            }
        )

        fotoapparat?.updateConfiguration(
            CameraConfiguration(
                pictureResolution = highestResolution()
            )
        )
        fotoapparat?.updateConfiguration(
            CameraConfiguration(
                jpegQuality = highestQuality()
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encoder(filePath: String): String {
        val bytes = File(filePath).readBytes()
        return Base64.getEncoder().encodeToString(bytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendPhoto(filePath: String, uuid: UUID, imageName: String) {
        ImageSetServiceImpl().uploadImage(
            imageName,
            uuid.toString(),
            encoder(filePath)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun takePhoto() {
        if (hasNoPermissions()) {
            requestPermission()
        } else {
            var count = 0
            val root = Environment.getExternalStorageDirectory().toString()
            val uuid = UUID.randomUUID()
//            val dirs = mutableMapOf<String, String>()
            var exposure = -3
            while (count < 7) {
                var myDir = File("$root/PhotoAppPicturesIMD")
                var imageName = filename + "_" + uuid + "_" + count + ".jpeg"

                if (!myDir.exists()) {
                    myDir.mkdirs()
                }
                myDir = File(myDir, imageName)

                Log.i("PATH: ", myDir.absolutePath)
                fotoapparat?.updateConfiguration(
                    CameraConfiguration(
                        exposureCompensation = manualExposure(exposure)
                    )
                )

                fotoapparat
                    ?.takePicture()
                    ?.saveToFile(myDir)?.whenDone(object : WhenDoneListener<Unit> {
                        override fun whenDone(@Nullable unit: Unit?) {
                            if (unit != null) {
                                Log.i("Done: ", "Done")
//                                sendPhoto(myDir.absolutePath, uuid, imageName)
                                if (count == 7)
                                    showDialogNewImageSet()
                            }
                        }
                    })


                count++
                exposure++
                Log.i("exposure: ", exposure.toString())
            }

            fotoapparat?.updateConfiguration(
                CameraConfiguration(
                    exposureCompensation = manualExposure(0)
                )
            )
        }
    }

    private fun showDialogNewImageSet() {
        val myFragment = DialogNewImageSet()
        myFragment.show(supportFragmentManager, "DialogNewImageSet")
    }

    override fun onStart() {
        super.onStart()
        if (hasNoPermissions()) {
            requestPermission()
        } else {
            fotoapparat?.start()
            fotoapparatState = FotoapparatState.ON
        }
    }

    private fun hasNoPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 0)
    }

    override fun onStop() {
        super.onStop()
        fotoapparat?.stop()
        FotoapparatState.OFF
    }

    override fun onResume() {
        super.onResume()
        if (!hasNoPermissions() && fotoapparatState == FotoapparatState.OFF) {
            val intent = Intent(baseContext, CameraActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}

enum class CameraState {
    FRONT, BACK
}

enum class FlashState {
    TORCH, OFF
}

enum class FotoapparatState {
    ON, OFF
}