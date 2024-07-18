package com.example.agenda;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class CadastroUsuario extends Fragment {
    private EditText edtNomeUsuario;
    private EditText edtSenha;
    private EditText edtRSenha;
    private View v;

    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.cadastro_usuario, container, false);

        BaseDAO helper = new BaseDAO(getContext());
        db = helper.getWritableDatabase();

        Button btnSair = v.findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                getActivity().finish();
            }
        });

        Button btnSalvar = v.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                edtNomeUsuario = v.findViewById(R.id.edtUsuarioCad);
                edtSenha = v.findViewById(R.id.edtSenhaCad);
                edtRSenha = v.findViewById(R.id.edtRSenhaCad);

                String nomeUsuario = edtNomeUsuario.getText().toString().trim();
                String senha = edtSenha.getText().toString();
                String rSenha = edtRSenha.getText().toString();

                if (nomeUsuario.length() > 0 && senha.length() > 0 && rSenha.length() > 0) {
                    // Se as senhas forem iguais
                    if (senha.compareTo(rSenha) == 0) {
                        String consulta = String.format("SELECT 1 FROM " + BaseDAO.USUARIO_TB + " WHERE " + BaseDAO.NOME_USUARIO + " = ?");
                        Cursor cursor = db.rawQuery(consulta, new String[]{nomeUsuario});

                        // Se o nome de usuário não existir
                        if (!cursor.moveToFirst()) {
                            ContentValues values = new ContentValues();
                            values.put(BaseDAO.NOME_USUARIO, nomeUsuario);
                            String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt(12));
                            values.put(BaseDAO.SENHA_HASH, senhaHash);

                            db.insert("UsuarioTb", null, values);
                            edtNomeUsuario.setText("");
                            edtSenha.setText("");
                            edtRSenha.setText("");
                            Toast.makeText(getContext(), "Cadastro realizado!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Este usuário já existe!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "As senhas não batem!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Nenhum campo pode ser vazio!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}
