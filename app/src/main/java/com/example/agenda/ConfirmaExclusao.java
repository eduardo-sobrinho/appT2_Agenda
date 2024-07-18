package com.example.agenda;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmaExclusao extends AppCompatActivity {
    private String usuarioId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_exclusao);

        final BaseDAO helper = new BaseDAO(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Intent itRecebedora = getIntent();
        Bundle param = itRecebedora.getExtras();
        if (param != null) {
            usuarioId = param.getString("usuarioId");
        }

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnExcluir = findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.excluirConta(usuarioId);
                helper.close();
                Intent intent = new Intent(getApplicationContext(), Despedida.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
