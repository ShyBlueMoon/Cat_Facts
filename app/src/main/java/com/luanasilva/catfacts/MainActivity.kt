package com.luanasilva.catfacts

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luanasilva.catfacts.api.RetrofitHelper
import com.luanasilva.catfacts.databinding.ActivityMainBinding
import com.luanasilva.catfacts.model.CatFact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val retrofitAPI by lazy {
        RetrofitHelper.retrofitAPI
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnShow.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                listCatFact()
            }
        }




    }

    private suspend fun listCatFact() {
        var retorno:Response<CatFact>? = null

        try {
            retorno = retrofitAPI?.recoveryCatFact()
        } catch (e: Exception) {

        }



        if (retorno != null) {
            if(retorno.isSuccessful) {
                val catFact = retorno.body()
                val text = "${catFact?.fact}"


                withContext(Dispatchers.Main) {
                    binding.textCatFact.text  = text
                }
            }

        }
    }

}