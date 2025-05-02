package com.example.ppt.sensor_enums

import java.util.UUID

enum class PPT_LL(val serviceUUID: UUID, val characteristicUUID: UUID) {
    ACCELEROMETER(
        UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"),
        UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1210")
    ),
    GYROSCOPE(
        UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"),
        UUID.fromString("19b10000-e8f2-537e-4f6c-d104768a1211")
    ),
    VIBRATION(
        UUID.fromString("19B10000-E8F2-537E-4F6C-D104768A1214"),
        UUID.fromString("19B10001-E8F2-537E-4F6C-D104768A1214")
    );

    companion object {
        const val name = "PPT_LL"
    }


}