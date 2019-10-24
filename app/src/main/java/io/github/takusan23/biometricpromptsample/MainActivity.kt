package io.github.takusan23.biometricpromptsample

import android.content.Context
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var cancellationSignal: CancellationSignal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cancellationSignal = CancellationSignal()

        val biometricManager = getSystemService(Context.BIOMETRIC_SERVICE) as BiometricManager

        biometric_button.setOnClickListener {

            //生体認証のチェック
            when (biometricManager.canAuthenticate()) {
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    //ユーザーが登録されていない
                    Toast.makeText(this,"登録されていません",Toast.LENGTH_SHORT).show()
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    //サポート/有効されてない
                    Toast.makeText(this,"サポート/有効化されていません",Toast.LENGTH_SHORT).show()
                }
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    //生体認証が登録されていて利用可能

                    val biometricPrompt = BiometricPrompt.Builder(this)
                        .setTitle("生体認証のてすと")
                        .setSubtitle("テスト")
                        .build()
                    biometricPrompt.authenticate(cancellationSignal, {}, object :
                        BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                            println("成功")
                            //成功したらここ
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            println("失敗")
                        }

                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                            super.onAuthenticationError(errorCode, errString)
                            println("エラー")
                        }
                    })
                }
            }
        }
    }
}
