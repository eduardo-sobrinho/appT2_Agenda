package com.example.agenda;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class AtualizaContato extends AppCompatActivity {
    private EditText edtNome;
    private EditText edtTel;
    private TextView txtAniv;
    protected String currentText_nome;
    protected String currentText_tel;
    protected MaskFormatter formatter;
    private String contatoId;
    private String nome;
    private String tel;
    private String aniv;
    private int dia;
    private int mes;
    private Calendar c;
    private DatePickerDialog dpd;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atualiza_contato);

        BaseDAO helper = new BaseDAO(this);
        db = helper.getReadableDatabase();

        Intent itRecebedora = getIntent();
        Bundle param = itRecebedora.getExtras();
        if (param != null) {
            contatoId = param.getString("contatoId");
            nome = param.getString("nome");
            tel = param.getString("tel");
            aniv = param.getString("aniv");
        }

        edtNome = findViewById(R.id.edtNome);
        edtTel = findViewById(R.id.edtTel);
        txtAniv = findViewById(R.id.txtAniv);

        edtNome.setText(nome);
        edtTel.setText(tel);
        txtAniv.setText(aniv);

        edtNome = findViewById(R.id.edtNome);
        edtNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(currentText_nome)) {
                    formatter = new SimpleMaskFormatter("LLLLLLLLLLLLLLLLLLLLLLLLL");
                    currentText_nome = formatter.format(s.toString());
                    edtNome.setText(currentText_nome);
                    if (edtNome != null) {
                        EditText editText = edtNome;
                        editText.setSelection(currentText_nome.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtTel = findViewById(R.id.edtTel);
        edtTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(currentText_tel)) {
                    formatter = new SimpleMaskFormatter("(NN) NNNN-NNNN");
                    currentText_tel = formatter.format(s.toString());
                    edtTel.setText(currentText_tel);
                    if (edtTel != null) {
                        EditText editText = edtTel;
                        editText.setSelection(currentText_tel.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String[] data = txtAniv.getText().toString().split(" ");

        String[] mesArray = getResources().getStringArray(R.array.meses_do_ano);
        mes = Arrays.asList(mesArray).indexOf(data[2]);
        dia = Integer.parseInt(data[0]);

        txtAniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                dpd = new DatePickerDialog(AtualizaContato.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dia = dayOfMonth;
                        mes = month;
                        String[] meses = getResources().getStringArray(R.array.meses_do_ano);
                        String data = dayOfMonth + " de " + meses[month];
                        txtAniv.setText(data);
                    }
                }, year, mes, dia);

                dpd.show();
            }
        });

        Button btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNome = findViewById(R.id.edtNome);
                edtTel = findViewById(R.id.edtTel);
                txtAniv = findViewById(R.id.txtAniv);

                String nome = edtNome.getText().toString().trim();
                String tel = edtTel.getText().toString();
                String aniv = txtAniv.getText().toString();

                if (nome.length() > 0 && tel.length() > 0 && aniv.length() > 0) {
                    ContentValues values = new ContentValues();
                    values.put(BaseDAO.NOME_CONTATO, nome);
                    values.put(BaseDAO.TEL_CONTATO, tel);
                    values.put(BaseDAO.ANIV_CONTATO, aniv);
                    db.update(BaseDAO.CONTATO_TB, values, "contatoId=?", new String[]{contatoId});

                    Toast.makeText(AtualizaContato.this, "Atualizado: " + nome + ", " + tel + ", " + aniv, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AtualizaContato.this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}