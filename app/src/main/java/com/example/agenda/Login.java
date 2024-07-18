package com.example.agenda;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import org.mindrot.jbcrypt.BCrypt;

public class Login extends Fragment {
    private EditText edtUsuario;
    private EditText edtSenha;
    private View v;

    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.layout_login, container, false);
        Button btnLogar = v.findViewById(R.id.btnLogin);

        BaseDAO helper = new BaseDAO(getActivity());
        db = helper.getReadableDatabase();

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                edtUsuario = v.findViewById(R.id.edtUsuario);
                edtSenha = v.findViewById(R.id.edtSenha);

                String nome = edtUsuario.getText().toString().trim();
                String senha = edtSenha.getText().toString();

                if (nome.compareTo("") != 0 && senha.compareTo("") != 0) {
                    String consulta = String.format("SELECT " + BaseDAO.USUARIO_ID + ", " + BaseDAO.SENHA_HASH +
                            " FROM " + BaseDAO.USUARIO_TB + " WHERE " +
                            BaseDAO.NOME_USUARIO + " = ?");
                    Cursor cursor = db.rawQuery(consulta, new String[]{nome});

                    // Se o nome de usuário existe
                    if (cursor.moveToFirst()) {
                        String senhaHash = cursor.getString(1);
                        boolean senhaCorreta = BCrypt.checkpw(senha, senhaHash);
                        if (senhaCorreta) {
                            edtUsuario.setText("");
                            edtSenha.setText("");

                            int usuarioId = cursor.getInt(0);

                            Intent it;
                            it = new Intent(getActivity(), Contatos.class);
                            Bundle param = new Bundle();
                            param.putInt("usuarioId", usuarioId);

                            cursor.close();
                            it.putExtras(param);
                            startActivity(it);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Usuário não existe!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Preencha o nome de usuário e a senha!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnSair = v.findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // A tela de login sempre terá o tema padrão
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}