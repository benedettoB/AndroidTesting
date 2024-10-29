package org.benedetto.androidtesting.tests.hx

import java.nio.ByteBuffer
import java.nio.ByteOrder

class Header(rawHeader: ByteArray) {

    val version: Byte = rawHeader[0]
    val flags: Byte = rawHeader[1]
    val source: Byte = rawHeader[2]
    val destination: Byte = rawHeader[3]
    val payloadSize: Short

    init {
        val buffer = ByteBuffer.wrap(rawHeader).order(ByteOrder.BIG_ENDIAN)
        buffer.position(4) //Move to the payload size section (bytes 5-6)
        payloadSize = buffer.short
    }

    fun interpretFlags(): String{
        val flagInfo = StringBuilder()

        if(flags.toInt() and 0x01 != 0){
            flagInfo.append("Do not store DNS\n")
        }
        if(flags.toInt() and 0x02 !=0){
            flagInfo.append("Unencrypted message\n")
        }else{
            flagInfo.append("Encrypted message\n")
        }
        if(flags.toInt() and 0x04 != 0){
            flagInfo.append("Issuer certificate request on session formation\n")
        }
        if(flags.toInt() and 0x08 != 0){
            flagInfo.append("PPS Support: Source handles provisioning messaging\n")
        }

        return flagInfo.toString()
    }

    fun getSourceName(): String{
        return when(source){
            0x01.toByte()-> "Cloud"
            0x02.toByte()-> "Phone"
            0x03.toByte()-> "Case"
            0x20.toByte()-> "Final Test SBC"
            else -> "Unknown"
        }
    }

    fun getDestinationName():String{
        return when(destination){
            0x01.toByte()-> "Cloud"
            0x02.toByte()-> "Phone"
            0x03.toByte()-> "Case"
            0x20.toByte()-> "Final Test SBC"
            else -> "Unknown"
        }
    }

    override fun toString(): String {
        return "Version: $version\n" +
                "Flags: ${interpretFlags()}\n" +
                "Source: ${getSourceName()}\n" +
                "Destination: ${getDestinationName()}\n" +
                "Payload Size: $payloadSize bytes"
    }

}