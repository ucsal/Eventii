package br.edu.ucsal.eventii.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONArray;

import java.util.ArrayList;

import br.edu.ucsal.eventii.R;
import br.edu.ucsal.eventii.activity.EventiiActivity;

public class RegisterFragment extends Fragment implements View.OnClickListener, TextWatcher {

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


        editTextEmail.addTextChangedListener(this);

        btnRegister.setOnClickListener(this);

        return rootView;

    }

    public void register(){

        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        String telefone = editTextTelefone.getText().toString();
        String username = editTextNome.getText().toString();

        final ParseUser user = new ParseUser();

        user.setEmail(email);
        user.setPassword(senha);
        user.setUsername(username);
        user.put("telefone", telefone);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if(e == null){
                    //Register OK
                    //TODO:ABRE ACTIVITY REGISTER
                    Toast.makeText(getActivity(),"Registrado com sucesso!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), EventiiActivity.class));
                    getActivity().finish();

                }else{
                    //Register NULL

                    //TODO: REGISTER DEU ERRO
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    //LISTENER CLICK

    @Override
    public void onClick(View v) {

        if(v == btnRegister){

            register();

        }

    }

    //TEXT IMPLEMENTS

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if(!isValidEmail(editTextEmail.getText().toString())){

            editTextEmail.setError("Email inválido");

        }

        if(!isValidPhone(editTextTelefone.getText().toString())){

            editTextTelefone.setError("Telefone inválido");

        }

    }

    //VALIDACOES

    private boolean isValidEmail(String s) {

        if(s == null){
            return false;
        }else{
            return Patterns.EMAIL_ADDRESS.matcher(s).matches();
        }

    }

    private boolean isValidPhone(String s){

        if(s == null){
            return false;
        }else{
            return Patterns.PHONE.matcher(s).matches();
        }

    }
}
