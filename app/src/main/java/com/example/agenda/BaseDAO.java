package com.example.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDAO extends SQLiteOpenHelper {

    protected static final String USUARIO_TB = "UsuarioTb";
    protected static final String USUARIO_ID = "usuarioId";
    protected static final String NOME_USUARIO = "nomeUsuario";
    protected static final String CONTATO_TB = "ContatoTb";
    protected static final String CONTATO_ID = "contatoId";
    protected static final String NOME_CONTATO = "nomeContato";
    protected static final String TEL_CONTATO = "telContato";
    protected static final String ANIV_CONTATO = "anivContato";
    protected static final String SENHA_HASH = "senhaHash";

    private static final String DATABASE_NAME = "Agenda_8";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    BaseDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TBL_USUARIO = "CREATE TABLE " +
                USUARIO_TB + "( " + USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME_USUARIO + " TEXT NOT NULL, " +
                SENHA_HASH + " TEXT NOT NULL);";

        String CREATE_TBL_CONTATO = "CREATE TABLE " +
                CONTATO_TB + "(" + CONTATO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOME_CONTATO + " TEXT NOT NULL, " +
                TEL_CONTATO + " TEXT NOT NULL, " +
                ANIV_CONTATO + " TEXT NOT NULL, " +
                USUARIO_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + USUARIO_ID + ") REFERENCES " + USUARIO_TB + "(" + USUARIO_ID + ") ON DELETE CASCADE);";

        database.execSQL(CREATE_TBL_USUARIO);
        database.execSQL(CREATE_TBL_CONTATO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USUARIO_TB);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + CONTATO_TB);
        onCreate(db);
    }

    Cursor getContatos(int usuarioId) {
        ContentValues values = new ContentValues();
        values.put(BaseDAO.USUARIO_ID, usuarioId);

        Cursor data = db.rawQuery("SELECT " + BaseDAO.NOME_CONTATO + ", " + BaseDAO.TEL_CONTATO + ", " +
                BaseDAO.ANIV_CONTATO + ", " + BaseDAO.CONTATO_TB + "." + BaseDAO.CONTATO_ID +
                " FROM " + BaseDAO.CONTATO_TB +
                " WHERE " + BaseDAO.USUARIO_ID + " = " + usuarioId + " ORDER BY " + BaseDAO.NOME_CONTATO, null);
        return  data;
    }

    void excluirConta(String usuarioId) {
        db.delete(BaseDAO.USUARIO_TB ,BaseDAO.USUARIO_ID + " =? ", new String[]{String.valueOf(usuarioId)});
    }
}


