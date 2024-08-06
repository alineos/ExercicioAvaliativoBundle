package br.edu.ifsp.dmo.calculamediadois.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo.calculamediadois.databinding.ActivityMediaBinding
import br.edu.ifsp.dmo.calculamediadois.model.Termo
import br.edu.ifsp.dmo.calculamediadois.view.adapters.TermoAdapter
import br.edu.ifsp.dmo.calculamediadois.view.listeners.TermoItemClickListener
import br.edu.ifsp.dmo.calculamediadois.R
import br.edu.ifsp.dmo.calculamediadois.databinding.TermoDialogMediaBinding
import br.edu.ifsp.dmo.calculamediadois.databinding.TermoDialogPonderadaBinding

class MediaActivity: AppCompatActivity(), TermoItemClickListener {
    private lateinit var binding:ActivityMediaBinding
    private val dataSource = ArrayList<Termo>()
    private var tipoMedia = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openBundle()
        configListeners()
        configReciclerView()

    }

    private fun openBundle() {
        val extras = intent.extras
        if (extras !=null){
            tipoMedia = extras.getInt("tipoMedia")
        }
    }

    private fun configListeners() {
        binding.buttonCalc.setOnClickListener{calculaMedia()}
        binding.buttonAdd.setOnClickListener{adicionaTermo()}

    }


    private fun configReciclerView() {
        val adapter = TermoAdapter(this,dataSource,this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

    }

    private fun calculaMedia() {
     when (tipoMedia){
         0-> calculaMediaArit()
         1-> calculaMediaHarmonica()
         2-> calculaMediaPonderada()
         }
       }

    private fun calculaMediaPonderada() {

        if (dataSource.size > 0 ) {
            var result = 0.0
            var peso = 0.0
            dataSource.forEach { result += it.valor * it.peso
                peso +=it.peso }

            result /= peso

            binding.labelResult.setText("Resultado: "+result)
        }

    }

    private fun calculaMediaHarmonica() {
        if (dataSource.size > 0 ) {
            var result = 0.0
            dataSource.forEach { result += (1/it.valor) }

            result = dataSource.size/result

            binding.labelResult.setText("Resultado: "+result)
        }
    }

    private fun calculaMediaArit() {
        if (dataSource.size > 0 ) {
            var result = 0.0

            dataSource.forEach { result += it.valor }

            result /= dataSource.size
            binding.labelResult.setText("Resultado: "+result)
        }
    }

    private fun adicionaTermo() {
        if (tipoMedia == 2){
            mediaPonderada()
        }else
            mediaNaoPonderada()

    }


    private fun mediaPonderada(){

        var  tela = layoutInflater.inflate(R.layout.termo_dialog_ponderada, null)
        val bindingDialog: TermoDialogPonderadaBinding =TermoDialogPonderadaBinding.bind(tela)

        // Configuração do AlertDialog
        val builder = AlertDialog.Builder(this)
            .setView(tela)
            .setTitle(R.string.new_term)
            .setPositiveButton(R.string.save,
                DialogInterface.OnClickListener { dialog, which ->
                    // Salvar um site é incluir um objeto na lista,
                    // e notificar o adapter que existe atualização.
                    dataSource.add(Termo(bindingDialog.edittextTerm.text.toString().toDouble(),bindingDialog.edittextWeight.text.toString().toInt()))
                    notifyAdapter()
                    dialog.dismiss()
                })
            .setNegativeButton(R.string.cancelar,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
        val dialog = builder.create()
        dialog.show()

    }
    private fun mediaNaoPonderada(){

        var  tela = layoutInflater.inflate(R.layout.termo_dialog_media, null)
        val bindingDialog: TermoDialogMediaBinding =TermoDialogMediaBinding.bind(tela)

        // Configuração do AlertDialog
        val builder = AlertDialog.Builder(this)
            .setView(tela)
            .setTitle(R.string.new_term)
            .setPositiveButton(R.string.save,
                DialogInterface.OnClickListener { dialog, which ->
                    // Salvar um site é incluir um objeto na lista,
                    // e notificar o adapter que existe atualização.
                    dataSource.add(Termo(bindingDialog.edittextTerm.text.toString().toDouble(),0))
                    notifyAdapter()
                    dialog.dismiss()
                })
            .setNegativeButton(R.string.cancelar,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
        val dialog = builder.create()
        dialog.show()

    }






    override fun clickTermoItem(position: Int) {

    }

    override fun clickDeletetTermoItem(position: Int) {
        dataSource.remove(dataSource[position])
        notifyAdapter()
    }

    private fun notifyAdapter() {
        val adapter = binding.recyclerView.adapter
        adapter?.notifyDataSetChanged()
    }

}