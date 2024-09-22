package com.example.sistemaTarefa.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, NOME_BANCO_DADOS, null, VERSAO) {

    companion object{
        const val NOME_BANCO_DADOS = "ListaTarefas.db"
        const val NOME_TABELA_TAREFAS = "tarefas"
        const val VERSAO = 1
        const val COLUNA_ID_TAREFA = "id_tarefa"
        const val COLUNA_DESCRICAO = "descricao"
        const val COLUNA_DATA_CADASTRO = "dataCadastro"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS $NOME_TABELA_TAREFAS(" +
                "$COLUNA_ID_TAREFA integer NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$COLUNA_DESCRICAO varchar(70)," +
                "$COLUNA_DATA_CADASTRO DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"

        try {
            db?.execSQL( sql )
            Log.i("tag_db", "Sucesso ao criar tabela!")
        }catch (e: Exception){
            e.printStackTrace()
            Log.i("tag_db", "Erro ao criar tabela!")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}