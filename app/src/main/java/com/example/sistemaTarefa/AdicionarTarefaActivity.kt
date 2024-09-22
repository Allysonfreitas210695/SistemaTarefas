package com.example.sistemaTarefa

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sistemaTarefa.databinding.ActivityAdicionarTarefaBinding
import com.example.sistemaTarefa.model.Tarefa
import com.example.sistemaTarefa.database.TarefaDAO

class AdicionarTarefaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //recuperar tarefa passada
        var tarefa: Tarefa? = null
        var bundle = intent.extras

        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                tarefa = bundle.getSerializable("tarefa", Tarefa::class.java) ?: return
            } else {
                tarefa = bundle.getSerializable("tarefa") as Tarefa
            }
            binding.editTarefa.setText(tarefa.descricao)
        }

        binding.btnSalvar.setOnClickListener {
            if (binding.editTarefa.text.isNotEmpty()) {
                if (tarefa != null) {
                    editar( tarefa )
                } else {
                    salvar()
                }
            }else {
                Toast.makeText(
                    this,
                    "Preenchar o campo de descricao!",
                    Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun editar( tarefa: Tarefa ) {
        val descricao = binding.editTarefa.text.toString()

        var tarefaAtualizar = Tarefa( tarefa.idTarefa, descricao, null)

        var tarefaDAO = TarefaDAO(this)

        if (tarefaDAO.atualizar( tarefaAtualizar )) {
            Toast.makeText(this, "Sucesso ao atualizar tarefa!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao atualizar tarefa!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun salvar() {
        val descricao = binding.editTarefa.text.toString()
        var tarefaDAO = TarefaDAO(this)
        if (tarefaDAO.salvar(Tarefa(-1, descricao, null))) {
            Toast.makeText(this, "Sucesso ao salvar tarefa!", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Erro ao salvar tarefa!", Toast.LENGTH_SHORT).show()
        }
    }

}