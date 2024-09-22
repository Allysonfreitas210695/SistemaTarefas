package com.example.sistemaTarefa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemaTarefa.databinding.ItemTarefaBinding
import com.example.sistemaTarefa.model.Tarefa

class TarefaAdapter(
    val onClickExcluir: (Int) -> Unit,
    val onClickAtualizar: (Tarefa) -> Unit
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    private var listaTarefas: List<Tarefa> = listOf()

    fun adicionarLista(lista: List<Tarefa>){
        this.listaTarefas = lista
        notifyDataSetChanged()
    }

    inner class TarefaViewHolder(itemBinding: ItemTarefaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

        private val binding: ItemTarefaBinding

        init {
            binding = itemBinding
        }

        fun bind(tarefa: Tarefa){
            binding.textDescricao.text = tarefa.descricao
            binding.textData.text = tarefa.dataCadastro

            binding.btnEditar.setOnClickListener{
                onClickAtualizar( tarefa )
            }

            binding.btnExcluir.setOnClickListener{
                onClickExcluir( tarefa.idTarefa )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val itemTarefaBinding = ItemTarefaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TarefaViewHolder(itemTarefaBinding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]
        holder.bind( tarefa )
    }

    override fun getItemCount(): Int {
        return listaTarefas.size
    }

}