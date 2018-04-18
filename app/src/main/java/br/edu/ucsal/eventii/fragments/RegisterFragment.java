package br.edu.ucsal.eventii.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import br.edu.ucsal.eventii.R;

public class RegisterFragment extends Fragment implements View.OnClickListener{

    private ViewPager viewPager;

    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextTelefone;
    private EditText editTextNome;
    private Button btnRegister;

    @SuppressLint("ValidFragment")
    public RegisterFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public RegisterFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView  = (ViewGroup) inflater.inflate(R.layout.fragment_register_slide,container,false);

        editTextEmail = rootView.findViewById(R.id.fragment_register_textEmail);
        editTextSenha = rootView.findViewById(R.id.fragment_register_textPassword);
        editTextNome = rootView.findViewById(R.id.fragment_register_textNome);
        editTextTelefone = rootView.findViewById(R.id.fragment_register_textTelefone);

        btnRegister = rootView.findViewById(R.id.fragment_register_btnRegister);


        btnRegister.setOnClickListener(this);

        return rootView;

    }

    public void register(){

        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        String telefone = editTextTelefone.getText().toString();
        String username = editTextNome.getText().toString();

        ParseUser user = new ParseUser();

        user.setEmail(email);
        user.setPassword(senha);
        user.setUsername(username);
        user.put("telefone", telefone);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if(e == null){
                    //Register OK

                    editTextEmail.setText("Registrado com sucsso");


                }else{
                    //Register NULL

                    editTextEmail.setText("AZEDOU");

                }

            }
        });


    }

    @Override
    public void onClick(View v) {

        if(v == btnRegister){

            register();

        }

    }
}
