package com.learning.helloworld

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher

class LoginActivity : AppCompatActivity() {

    var realLogin : String = ""
    var realPassword : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginEdit = findViewById<EditText>(R.id.loginEditText)
        val passwordEdit = findViewById<EditText>(R.id.passwordEditText)
        val confirmButton = findViewById<Button>(R.id.confirmButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        val preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        realLogin = preferences.getString(KEY_LOGIN, "")!!
        realPassword = preferences.getString(KEY_PASSWORD, "")!!

        val keyPairGen: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGen.initialize(1024)

        val keyPair: KeyPair = keyPairGen.genKeyPair()
        val publicKey: Key = keyPair.public
        val privateKey: Key = keyPair.private

        val cipher: Cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)

        if (realPassword.isEmpty()) { // kostyli i govnokod
            realPassword = Base64.encodeToString(
                cipher.doFinal(realPassword.toByteArray()),
                Base64.DEFAULT
            )
        }

        registerButton.setOnClickListener {
            val login = loginEdit.text.toString()
            val password = passwordEdit.text.toString()
            val encodedPassword: String = Base64.encodeToString(
                cipher.doFinal(password.toByteArray()),
                Base64.DEFAULT
            )

            val preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(KEY_LOGIN, login)
            editor.putString(KEY_PASSWORD, encodedPassword)
            editor.apply()
        }

        confirmButton.setOnClickListener {
            val login = loginEdit.text.toString()
            val password = passwordEdit.text.toString()
            val encodedPassword: String = Base64.encodeToString(
                cipher.doFinal(password.toByteArray()),
                Base64.DEFAULT
            )

            if (login == realLogin) {
                if (encodedPassword == realPassword) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(KEY_LOGIN, login)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Wrong password", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Wrong login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val PREF_NAME = "Preferences"
        const val KEY_LOGIN = "login"
        const val KEY_PASSWORD = "password"
    }
}
