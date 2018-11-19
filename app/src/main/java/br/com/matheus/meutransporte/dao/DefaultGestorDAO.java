package br.com.matheus.meutransporte.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.matheus.meutransporte.modelo.Gestor;

public class DefaultGestorDAO extends SQLiteOpenHelper{
    public DefaultGestorDAO(Context context, Class<Gestor> Gestor) {
        super(context, "Meu Transporte", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE Gestor(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";

        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion){
//           case 3:
//               sql = "CREATE TABLE Gestor(id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
//               db.execSQL(sql);
        }

    }

    public void insereGestor(Gestor gestor) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoGestor(gestor);

        db.insert("Gestor", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoGestor(Gestor gestor) {
        ContentValues dados = new ContentValues();
        dados.put("nome", gestor.getNome());
        dados.put("endereco", gestor.getEndereco());
        dados.put("telefone", gestor.getTelefone());
        dados.put("site", gestor.getSite());
        dados.put("nota", gestor.getNota());
        dados.put("caminhoFoto", gestor.getCaminhoFoto());
        return dados;
    }

    public List<Gestor> buscaGestor() {
        String sql = "SELECT * FROM Gestor";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        List<Gestor> gestores = new ArrayList<Gestor>();
        while (c.moveToNext()){
            Gestor gestor = new Gestor();
            gestor.setId(c.getLong(c.getColumnIndex("id")));
            gestor.setNome(c.getString(c.getColumnIndex("nome")));
            gestor.setEndereco(c.getString(c.getColumnIndex("endereco")));
            gestor.setTelefone(c.getString(c.getColumnIndex("telefone")));
            gestor.setSite(c.getString(c.getColumnIndex("site")));
            gestor.setNota(c.getDouble(c.getColumnIndex("nota")));
            gestor.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            gestores.add(gestor);

        }
        c.close();


        return gestores;
    }

    public void deleta(Gestor gestor) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {gestor.getId().toString()};
        db.delete("Gestor", "id = ?", params);
    }

    public void altera(Gestor gestor) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoGestor(gestor);

        String[] params = {gestor.getId().toString()};
        db.update("Gestor", dados, "id = ?" , params);
    }
}
