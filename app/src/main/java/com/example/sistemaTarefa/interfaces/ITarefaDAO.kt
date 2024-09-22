package com.example.sistemaTarefa.interfaces

import com.example.sistemaTarefa.model.Tarefa

interface ITarefaDAO {
    fun salvar(tarefa: Tarefa) : Boolean
    fun atualizar(tarefa: Tarefa) : Boolean
    fun listar() : MutableList<Tarefa>
    fun remover(idTarefa: Int) : Boolean
}