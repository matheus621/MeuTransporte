package br.com.matheus.meutransporte.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.matheus.meutransporte.modelo.Pedido;

public class DefaultPedidoDAO extends SQLiteOpenHelper{
    public DefaultPedidoDAO(Context context, Class<Pedido> Pedido) {
        super(context, "Meu Transporte", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Pedidos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT);";
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

    public void inserePedido(Pedido pedido) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoPedido(pedido);

        db.insert("Pedidos", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoPedido(Pedido pedido) {
        ContentValues dados = new ContentValues();
        dados.put("nome", pedido.getNome());
        dados.put("endereco", pedido.getEndereco());
        return dados;
    }

    public List<Pedido> buscaPedido() {
        String sql = "SELECT * FROM Pedidos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Pedido> pedidos = new ArrayList<Pedido>();
        while (c.moveToNext()){
            Pedido pedido = new Pedido();
            pedido.setId(c.getLong(c.getColumnIndex("id")));
            pedido.setNome(c.getString(c.getColumnIndex("nome")));
            pedido.setEndereco(c.getString(c.getColumnIndex("endereco")));

            pedidos.add(pedido);
        }
        c.close();


        return pedidos;
    }


    public void deleta(Pedido pedido) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {pedido.getId().toString()};
        db.delete("Pedidos", "id = ?", params);
    }

    public void altera(Pedido pedido) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoPedido(pedido);

        String[] params = {pedido.getId().toString()};
        db.update("Pedidos", dados, "id = ?" , params);
    }

}
