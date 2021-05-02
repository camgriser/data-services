package org.acg.db.data.generator

import java.io.OutputStream
import java.util.*
import kotlin.random.Random
import kotlin.system.exitProcess
import kotlin.test.currentStackTrace

class DataGenerator {

    companion object {

        private const val stringLength = 50
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        fun generateData(struct: Map<String, Any?>, rowSize: Long, os: OutputStream, delim: String) {
            val line = StringBuilder()
            LongRange(1L, rowSize).forEach {
                struct.keys.withIndex().forEach { key ->
                    when {
                        struct[key.value] is String -> line.append(generateRandomString())
                        struct[key.value] is Date -> line.append(generateRandomDate())
                        struct[key.value] is Int -> line.append(generateRandomInt())
                        struct[key.value] is Long -> line.append(generateRandomLong())
                        struct[key.value] is Double -> line.append(generateRandomDouble())
                        struct[key.value] is Float -> line.append(generateRandomFloat())
                        struct[key.value] is Boolean -> line.append(generateRandomBoolean())
                        else -> {
                            println("ERROR: Unsupported Type! ${struct[key.value]?.javaClass}")
                            currentStackTrace()
                            exitProcess(1)
                        }
                    }
                    if (key.index + 1 != struct.keys.size)
                        line.append(delim)
                    else
                        line.append("\n")
                }
                if (line.isNotEmpty())
                    os.write(line.toString().toByteArray())
                line.clear()
                os.flush()
            }
        }

        private fun generateRandomString(): String =
            (1..stringLength)
                .map { Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")

        private fun generateRandomDate(): String =
            Date(System.currentTimeMillis()).toString()

        private fun generateRandomInt(): Int =
            Random.nextInt()

        private fun generateRandomLong(): Long =
            Random.nextLong()

        private fun generateRandomDouble(): Double =
            Random.nextDouble()

        private fun generateRandomFloat(): Float =
            Random.nextFloat()

        private fun generateRandomBoolean(): Boolean =
            Random.nextBoolean()
    }
}