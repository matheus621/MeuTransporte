package br.com.matheus.meutransporte.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.matheus.meutransporte.modelo.Motorista;

public class DefaultMotoristaDAO extends SQLiteOpenHelper{
    public DefaultMotoristaDAO(Context context, Class<Motorista> Motorista) {
        super(context, "Meu Transporte", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Motoristas (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        String sql1 = "CREATE TABLE Gestor(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql1);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
//            case 3:
//                sql = "CREATE TABLE Gestor(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
//                db.execSQL(sql);

        }

    }

    public void insereMotorista(Motorista motorista) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoMotorista(motorista);

        db.insert("Motoristas", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoMotorista(Motorista motorista) {
        ContentValues dados = new ContentValues();
        dados.put("nome", motorista.getNome());
        dados.put("endereco", motorista.getEndereco());
        dados.put("telefone", motorista.getTelefone());
        dados.put("site", motorista.getSite());
        dados.put("nota", motorista.getNota());
        dados.put("caminhoFoto", motorista.getCaminhoFoto());
        return dados;
    }

    public List<Motorista> buscaMotoristas() {
        String sql = "SELECT * FROM Motoristas;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Motorista> motoristas = new ArrayList<Motorista>();
        while (c.moveToNext()){
            Motorista motorista = new Motorista();
            motorista.setId(c.getLong(c.getColumnIndex("id")));
            motorista.setNome(c.getString(c.getColumnIndex("nome")));
            motorista.setEndereco(c.getString(c.getColumnIndex("endereco")));
            motorista.setTelefone(c.getString(c.getColumnIndex("telefone")));
            motorista.setSite(c.getString(c.getColumnIndex("site")));
            motorista.setNota(c.getDouble(c.getColumnIndex("nota")));
            motorista.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            motoristas.add(motorista);
        }
        c.close();


        return motoristas;
    }


    public void deleta(Motorista motorista) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {motorista.getId().toString()};
        db.delete("Motoristas", "id = ?", params);
    }

    public void altera(Motorista motorista) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoMotorista(motorista);

        String[] params = {motorista.getId().toString()};
        db.update("Motoristas", dados, "id = ?" , params);
    }

}
