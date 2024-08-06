package br.edu.ifsp.dmo.calculamediadois.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.dmo.calculamediadois.R
import br.edu.ifsp.dmo.calculamediadois.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    configSpinner()
    configurarListener()
}

    private fun configurarListener() {
        binding.buttonCalc.setOnClickListener{calculaMedia()}
        binding.buttonHelp.setOnClickListener{pedeAjuda()}
    }

    private fun calculaMedia() {
        val tipMedia:Int = binding.spinnerMedias.selectedItemPosition

//        when(tipMedia){
//            0-> mediaAritmetica()
//            1-> mediaHarmonica()
//            2-> mediaPonderada()
//        }

        val mIntent = Intent(this, MediaActivity::class.java)
        mIntent.putExtra("tipoMedia", tipMedia)
        startActivity(mIntent)

    }

    private fun pedeAjuda() {
        val mIntent = Intent(this, AjudaActivity::class.java)
        startActivity(mIntent)
    }

    private fun configSpinner() {
        val adapter = ArrayAdapter<String>(
            this,android.R.layout.simple_spinner_dropdown_item,resources.getStringArray(R.array.medias)
        )
        binding.spinnerMedias.adapter= adapter
    }
}