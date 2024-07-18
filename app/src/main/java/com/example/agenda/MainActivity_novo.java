/*
 * Este arquivo foi modificado alguns dias depois de ter enviado o anterior pro git, e portanto, o do git está desatualizado.
 * MainActivity.java é o que está no git.
 * Abra os dois que estão nesta pasta e veja a diferença no código.
 * Execute ora com um no Manifest, ora com outro no Manifest e veja a diferença na execução
 *
 */

package com.example.agenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView txtCad = findViewById(R.id.txtCad);
        final TextView txtLogin = findViewById(R.id.txtLogin);

        // The Activity is being created for the first time, so create and add new fragments.
        if (savedInstanceState == null) {
            Login frag1 = new Login();
            ft.add(R.id.layout_base, frag1);
            txtLogin.setFocusable(false);

            txtLogin.setText("");
            ft.commit();
        }

        txtCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroUsuario frag2 = new CadastroUsuario();
                FragmentTransaction ft = fm.beginTransaction();
                txtLogin.setClickable(true);
                txtCad.setClickable(false);
                txtLogin.setFocusable(true);
                txtCad.setFocusable(false);

                txtCad.setText("");
                txtLogin.setText("login");
                ft.replace(R.id.layout_base, frag2, "frag2");
                ft.commit();
            }
        });


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login frag1 = new Login();
                FragmentTransaction ft = fm.beginTransaction();
                txtCad.setClickable(true);
                txtLogin.setClickable(false);
                txtCad.setFocusable(true);
                txtLogin.setFocusable(false);

                txtLogin.setText("");
                txtCad.setText("cadastro");
                ft.replace(R.id.layout_base, frag1, "frag1");
                ft.commit();
            }
        });
    }
}
