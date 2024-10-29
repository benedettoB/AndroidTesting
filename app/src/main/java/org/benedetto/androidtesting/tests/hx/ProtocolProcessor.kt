package org.benedetto.androidtesting.tests.hx

import android.util.Log
import org.benedetto.androidtesting.util.log

object ProtocolProcessor {

    private val TAG = "ProtocolProcessor"

    fun processResponse(rawResponseHex: String){
        //Convert the hex string to a byte array
        val rawResponse = hexStringToByteArray(rawResponseHex)

        //Extract the first 6 bytes as the header
        val headerBytes = rawResponse.sliceArray(0 until 6)

        //Extract the rest as the encrypted payload
        val encryptedPayload = rawResponse.sliceArray(6 until rawResponse.size)

        //Parse the header using the Header class
        val header = Header(headerBytes)
        log(TAG, "Parsed Header:")
        log(TAG, header.toString())

        //Forward the encrypted payload
        forwardEncryptedPayload(encryptedPayload)

    }

    private fun forwardEncryptedPayload(encryptedPayload: ByteArray){
        //Implement the logic to forward the encrypted payload to the cloud/case
        log(TAG, "Forwarding encrypted payload: ${byteArrayToHexString(encryptedPayload)}")
    }



    private fun hexStringToByteArray(s: String): ByteArray{
        val len = s.length
        val data = ByteArray(len / 2)
        for(i in 0 until len step 2){
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) +
                          Character.digit(s[i + 1], 16)).toByte()
        }
        return data
    }

    private fun byteArrayToHexString(bytes: ByteArray): String{
        val sb = StringBuilder()
        for(b in bytes){
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }
}