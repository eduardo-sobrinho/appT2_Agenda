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

        // The Activity is being created for the first time, so create and add new fragments.
        if (savedInstanceState == null) {
            Login frag1 = new Login();
            ft.add(R.id.layout_base, frag1);

            ft.commit();
        }

        TextView txtCad = findViewById(R.id.txtCad);
        txtCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroUsuario frag2 = new CadastroUsuario();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.layout_base, frag2, "frag2");
                ft.commit();
            }
        });

        TextView txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login frag1 = new Login();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.layout_base, frag1, "frag1");
                ft.commit();
            }
        });
    }
}
