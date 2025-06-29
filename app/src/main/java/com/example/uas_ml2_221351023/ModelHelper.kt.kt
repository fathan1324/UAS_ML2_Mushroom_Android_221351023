package com.example.uas_ml2_221351023

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class ModelHelper(private val context: Context) {

    private var interpreter: Interpreter? = null

    fun loadModel(modelName: String = "mushroom_ann.tflite") {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        val modelBuffer: MappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        interpreter = Interpreter(modelBuffer)
    }

    fun predict(inputData: FloatArray): Float {
        val input = arrayOf(inputData)
        val output = Array(1) { FloatArray(1) }
        interpreter?.run(input, output)
        return output[0][0]
    }

    fun close() {
        interpreter?.close()
    }
}
