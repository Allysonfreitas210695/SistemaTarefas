package com.example.sistemaTarefa.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.sistemaTarefa.interfaces.ITarefaDAO
import com.example.sistemaTarefa.model.Tarefa

class TarefaDAO(context: Context) : ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase


    override fun salvar(tarefa: Tarefa): Boolean {

        val conteudos = ContentValues()
        conteudos.put("${DatabaseHelper.COLUNA_DESCRICAO}", tarefa.descricao)

        try {
            escrita.insert(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                null,
                conteudos
            )

            Log.i("info_db", "Sucesso ao inserir tarefa!")
            return  true
        }catch (e: Exception){
            Log.i("info_db", "Error ao inserir tarefa!")
            return  false
        }
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val args = arrayOf( tarefa.idTarefa.toString() )

        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.COLUNA_DESCRICAO}", tarefa.descricao)

        try {
            escrita.update(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                conteudo,
                "${DatabaseHelper.COLUNA_ID_TAREFA} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao atualiza tarefa!")
            return  true
        }catch (e: Exception){
            Log.i("info_db", "Error ao atualizar tarefa!")
            return  false
        }
    }

    override fun listar(): MutableList<Tarefa> {
        val listaTarefa = mutableListOf<Tarefa>()

        val sql = "SELECT ${DatabaseHelper.COLUNA_ID_TAREFA}," +
                "${DatabaseHelper.COLUNA_DESCRICAO}," +
                "strftime('%d/%m/%Y %H:%M',${DatabaseHelper.COLUNA_DATA_CADASTRO}) AS ${DatabaseHelper.COLUNA_DATA_CADASTRO} " +
                "FROM ${DatabaseHelper.NOME_TABELA_TAREFAS}"

        var cursor = leitura.rawQuery( sql, null )

        val indiceId = cursor.getColumnIndex(DatabaseHelper.COLUNA_ID_TAREFA)
        val indiceDiscricao = cursor.getColumnIndex(DatabaseHelper.COLUNA_DESCRICAO)
        val indiceDataCadastro = cursor.getColumnIndex(DatabaseHelper.COLUNA_DATA_CADASTRO)


        while( cursor.moveToNext() ) {

            listaTarefa.add( Tarefa( cursor.getInt(indiceId), cursor.getString(indiceDiscricao), cursor.getString(indiceDataCadastro)) )
        }
        return  listaTarefa
    }

    override fun remover(idTarefa: Int): Boolean {
        val args = arrayOf( idTarefa.toString() )
        try {
            escrita.delete(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                "${DatabaseHelper.COLUNA_ID_TAREFA} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao remover tarefa!")
            return  true
        }catch (e: Exception){
            Log.i("info_db", "Error ao remover tarefa!")
            return  false
        }
    }
}