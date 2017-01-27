package br.com.baceiredo.agenda.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.baceiredo.agenda.model.entity.Contato;

/**
 * Created by ur5l on 29/07/2016.
 */
public class ContatoDAO extends SQLiteOpenHelper{


    public ContatoDAO(Context context) {
        super(context, "Agenda", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table Contato (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, celular TEXT, email TEXT, site TEXT, notas TEXT, nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";

        switch(oldVersion) {
            case 4:
                //sql = "alter table Contato add column celular TEXT;";
                //db.execSQL(sql);
        }
    }

    public boolean insert(Contato contato) {
        boolean salvo = true;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValues(contato);
        try {
            db.insert("Contato", null, dados);

        } catch (Throwable e){
            salvo = false;
        }
       return salvo;
    }

    public boolean update(Contato contato) {
        boolean salvo = true;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getContentValues(contato);
        String[] params = {contato.getId().toString()};
        try {
            db.update("Contato", dados, "id = ?", params);

        } catch (Throwable e){
            salvo = false;
        }
        return salvo;
    }

    public List<Contato> list() {
        String sql = "select * from Contato order by nota desc, nome;";
        Contato contato = null;
        List<Contato> contatos = new ArrayList<Contato>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            contato = new Contato();

            contato.setId(cursor.getLong(cursor.getColumnIndex("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            contato.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            contato.setCelular(cursor.getString(cursor.getColumnIndex("celular")));
            contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contato.setSite(cursor.getString(cursor.getColumnIndex("site")));
            contato.setNotas(cursor.getString(cursor.getColumnIndex("notas")));
            contato.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            contato.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));

            contatos.add(contato);
        }
        cursor.close();

        return contatos;
    }

    public void delete(Contato contato) {
       SQLiteDatabase db = getWritableDatabase();
        String[] params = {contato.getId().toString()};
        db.delete("Contato", "id = ?", params);
    }

    public String retornaNomeContatoDeCelular(String celular){
        String nome = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Contato where celular = ?", new String[]{celular});
        if(cursor.moveToNext()){
            nome = cursor.getString(cursor.getColumnIndex("nome"));
        }
        cursor.close();
        return nome;
    }

    @NonNull
    private ContentValues getContentValues(Contato contato) {
        ContentValues dados = new ContentValues();
        dados.put("nome", contato.getNome());
        dados.put("endereco", contato.getEndereco());
        dados.put("telefone", contato.getTelefone());
        dados.put("celular", contato.getCelular());
        dados.put("email", contato.getEmail());
        dados.put("site", contato.getSite());
        dados.put("notas", contato.getNotas());
        dados.put("nota", contato.getNota());
        dados.put("caminhoFoto", contato.getCaminhoFoto());
        return dados;
    }

}
