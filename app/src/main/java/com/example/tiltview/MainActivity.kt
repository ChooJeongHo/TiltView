package com.example.tiltview

import android.content.ContentValues
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener {

    // 늦은 초기화를 통해 sensorManager 변수를 처음으로 사용할 때 getSystemService() 메서드로 SensorManager 객체를 생성한다.
    // 객체를 얻어온다
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private lateinit var tiltView: TiltView // 늦 초기화 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        //화면이 꺼지지 않게 하기 위한 설정
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //화면이 가로모드로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        tiltView = TiltView(this) // 늦 초기화
        setContentView(tiltView)
    }

    // 센서 등록 (액티비티가 동작할 때만 센서가 가동하게 하여 배터리 소모를 줄인다)
    // onResume()는 센서의 사용 등록을 담당하는 메서드
    override fun onResume() {
        super.onResume()
        // 센서의 사용 등록
        sensorManager.registerListener(this, // 센서값을 받을 SensorEventListener
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), // 센서의 종류를 지정하고, 가속도 센서를 지정하였다
            SensorManager.SENSOR_DELAY_NORMAL)  // 센서값을 얼마나 자주 받을 것인지 결정
    }

    // Activity가 동작 중일 때에만 센서를 사용하고, 화면이 꺼지면 센서를 해제한다.
    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this) // 이 메소드를 사용하여 센서 사용 해제
    }

    // 센서값이 변경되면 아래의 메서드가 호출되고 SensorEvent 객체를 통해 센서가 측정한 값들 및 여러 정보가 보내진다.
    override fun onSensorChanged(event: SensorEvent?) {
        // 센서값이 변경되면 호출
        // values[0] : x축 값 : 위로 기울이면 -10~0, 아래로 기울이면 0~10
        // values[1] : y축 값 : 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 0~10
        // values[2] : z축 값 : 사용하지 않는다.

        event?.let {
            Log.d(ContentValues.TAG, "onSensorChanged: " + "x: ${event.values[0]}, y: ${event.values[1]}, z: ${event.values[2]}")
            tiltView.onSensorEvent(event)
        }
    }


    // 센서 정밀도가 변경되면 호출
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}