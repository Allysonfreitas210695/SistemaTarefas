package com.example.sistemaTarefa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemaTarefa.database.TarefaDAO
import com.example.sistemaTarefa.databinding.ActivityMainBinding
import com.example.sistemaTarefa.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listaTarefa = mutableListOf<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionar.setOnClickListener{
            val intent = Intent(this, AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }

        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id) },
            { tarefa -> atualizarTarefa(tarefa) }
        )

        binding.rvTarefas.adapter = tarefaAdapter

        binding.rvTarefas.layoutManager = LinearLayoutManager(this)
    }

    private fun atualizarTarefa(tarefa: Tarefa) {
        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)
    }

    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle("Confirmar exlusão")
        alertBuilder.setMessage("Deseja realmente excluir a tarefa?")

        alertBuilder.setPositiveButton("Sim"){ _, _ ->
            val tarefaDAO = TarefaDAO(this)
            if( tarefaDAO.remover(id) ) {
                atualizarListaTarefa()
                Toast.makeText(this, "Tarefa removida com sucesso", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Error ao remover tarefa!", Toast.LENGTH_SHORT).show()
            }
        }

        alertBuilder.setNegativeButton("Não"){ _, _ ->  }

        alertBuilder.create().show()
    }

    private fun atualizarListaTarefa() {
        listaTarefa = TarefaDAO(this).listar()
        tarefaAdapter?.adicionarLista( listaTarefa )
    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefa()
    }

}