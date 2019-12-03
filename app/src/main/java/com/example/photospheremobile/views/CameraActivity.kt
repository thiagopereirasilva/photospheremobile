package com.example.photospheremobile.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.photospheremobile.R
import com.example.photospheremobile.service.ImageSetServiceImpl
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.result.PhotoResult
import io.fotoapparat.result.WhenDoneListener
import io.fotoapparat.selector.*
import io.fotoapparat.view.CameraView
import kotlinx.android.synthetic.main.content_camera.*
import java.io.File
import java.util.*

class CameraActivity : AppCompatActivity() {

    var fotoapparat: Fotoapparat? = null
    val filename = "test"
    val sd = Environment.getDataDirectory()

    var fotoapparatState: FotoapparatState? = null
    var cameraStatus: CameraState? = null
    lateinit var cameraExpo: android.hardware.Camera.Parameters

    var flashState: FlashState? = null
    var exposure: ExposureSelector? = null


    val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        var value = exposure.let { highestExposure() }
        createFotoapparat()

        cameraStatus = CameraState.BACK
        flashState = FlashState.OFF
        fotoapparatState = FotoapparatState.OFF


        fab_camera.setOnClickListener {
            takePhoto()
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

    private fun changeFlashState() {
        fotoapparat?.updateConfiguration(
            CameraConfiguration(
                exposureCompensation = manualExposure(10)
            )
        )
    }

    private fun switchCamera() {
        fotoapparat?.switchTo(
            lensPosition = if (cameraStatus == CameraState.BACK) front() else back(),
            cameraConfiguration = CameraConfiguration()
        )

        if (cameraStatus == CameraState.BACK) cameraStatus =
            CameraState.FRONT
        else cameraStatus = CameraState.BACK
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun takePhoto() {
        if (hasNoPermissions()) {
            requestPermission()
        } else {
            var count = 0
            var countSendPhoto = 0
            val root = Environment.getExternalStorageDirectory().toString()
            val uuid = UUID.randomUUID()
            while (count < 7) {
                var myDir = File("$root/Codility Pictures")
                if (!myDir.exists()) {
                    myDir.mkdirs()
                }
                myDir = File(myDir, "$filename$count.jpeg")

                Log.i("PATH: ", myDir.absolutePath)
                fotoapparat?.updateConfiguration(
                    CameraConfiguration(
//                        exposureCompensation = manualExposure(count * 2)
                        exposureCompensation = lowestExposure()
                    )
                )
                fotoapparat?.takePicture()
                    ?.saveToFile(myDir)
                    ?.whenAvailable { bitmap ->
                        sendPhoto(myDir.absolutePath, uuid, "$filename$count.jpeg")
                        countSendPhoto++
                    }
                count++
            }
            fotoapparat?.updateConfiguration(
                CameraConfiguration(
                    exposureCompensation = manualExposure(0)

                )
            )
        }
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

    fun requestPermission() {
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