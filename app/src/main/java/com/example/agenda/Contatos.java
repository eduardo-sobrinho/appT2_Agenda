package com.example.agenda;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;

public class Contatos extends AppCompatActivity {
    ListView listView;
    static Contato contatoSelecionado;
    ArrayList<Contato> contatoList;
    Contato contato;
    private int usuarioId;
    private SQLiteDatabase db;
    private BaseDAO helper;

    static float font_size = 1;
    static int size = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration(), font_size);
        setContentView(R.layout.activity_contatos);

        helper = new BaseDAO(this);
        db = helper.getReadableDatabase();

        Intent itRecebedora = getIntent();
        Bundle param = itRecebedora.getExtras();

        // Recebe o id do usuário logado no momento
        if (param != null) {
            usuarioId = param.getInt("usuarioId");
        }

        Button btnCadContato = findViewById(R.id.btnCadContato);
        btnCadContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it;
                it = new Intent(Contatos.this, CadastroContato.class);
                Bundle param = new Bundle();
                param.putInt("usuarioId", usuarioId);
                it.putExtras(param);
                startActivityForResult(it, 1);
            }
        });

        listView = findViewById(R.id.listView2);
        listarContatos(usuarioId, size);
    }

    // Lista os contatos do usuário: recebe como parâmetro o id do usuário
    private void listarContatos(int usuarioId, int size) {
        contatoList = new ArrayList<>();
        Cursor data = helper.getContatos(usuarioId);
        int numRows = data.getCount();
        if (numRows != 0) {
            while (data.moveToNext()) {
                contato = new Contato(data.getString(0), data.getString(1), data.getString(2), data.getInt(3));
                contatoList.add(contato);
            }

            listView.setOnItemLongClickListener(listClick);
            registerForContextMenu(listView);
        }

        FourColumn_ListAdapter adapter;
        switch (size) {
            case 1:
                adapter = new FourColumn_ListAdapter(Contatos.this, R.layout.list_adapter_view_menor, contatoList);
                break;
            case 3:
                adapter = new FourColumn_ListAdapter(Contatos.this, R.layout.list_adapter_view_maior, contatoList);
                break;
            default:
                adapter = new FourColumn_ListAdapter(Contatos.this, R.layout.list_adapter_view, contatoList);
                break;
        }
        listView = findViewById(R.id.listView2);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listarContatos(usuarioId, size);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.op_small:
                font_size = 0.8f;
                size = 1;
                listarContatos(usuarioId, size);
                recreate();
                return true;
            case R.id.op_medium:
                font_size = 1f;
                size = 2;
                listarContatos(usuarioId, size);
                recreate();
                return true;
            case R.id.op_large:
                font_size = 1.2f;
                size = 3;
                listarContatos(usuarioId, size);
                recreate();
                return true;
            case R.id.op_tema:
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                return true;
            case R.id.op_sair:
                finish();
                return true;
            case R.id.op_confirma:
                Intent it;
                it = new Intent(Contatos.this, ConfirmaExclusao.class);
                Bundle param = new Bundle();
                param.putString("usuarioId", String.valueOf(usuarioId));
                it.putExtras(param);
                startActivityForResult(it, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void adjustFontScale(Configuration configuration, float scale) {
        configuration.fontScale = scale;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getResources().updateConfiguration(configuration, metrics);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.update) {
            Intent it;
            it = new Intent(Contatos.this, AtualizaContato.class);
            Bundle param = new Bundle();
            param.putString("contatoId", String.valueOf(contatoSelecionado.getContatoId()));
            param.putString("nome", contatoSelecionado.getNome());
            param.putString("tel", contatoSelecionado.getTelefone());
            param.putString("aniv", contatoSelecionado.getNascimento());
            it.putExtras(param);
            startActivityForResult(it, 1);

            return true;
        } else if (item.getItemId() == R.id.delete) {
            db.delete(BaseDAO.CONTATO_TB ,BaseDAO.CONTATO_ID + " =? ", new String[]{String.valueOf(contatoSelecionado.getContatoId())});

            Toast.makeText(this, "Contato removido: " + contatoSelecionado.getNome(), Toast.LENGTH_LONG).show();
            listarContatos(usuarioId, size);
            return true;
        }
        return false;
    }

    public AdapterView.OnItemLongClickListener listClick = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            contatoSelecionado = (Contato) listView.getItemAtPosition(position);
            return false;
        }
    };
}
