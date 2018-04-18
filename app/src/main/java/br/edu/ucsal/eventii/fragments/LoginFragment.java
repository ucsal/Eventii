package br.edu.ucsal.eventii.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import br.edu.ucsal.eventii.R;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private final String FADE_OUT = "fadeout";
    private final String FADE_IN = "fadein";
    private TextView logo;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button btnLogin;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView  = (ViewGroup) inflater.inflate(R.layout.fragment_login_slide,container,false);

        logo = rootView.findViewById(R.id.fragment_login_logo);

        editTextEmail = rootView.findViewById(R.id.fragment_login_textEmail);
        editTextSenha = rootView.findViewById(R.id.fragment_login_textPassword);

        btnLogin = rootView.findViewById(R.id.fragment_login_btnLogin);

        progressBar = rootView.findViewById(R.id.fragment_login_progressBar);


        //Animacao logo

        Animation fadeIn = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in_animation);
        logo.setAnimation(fadeIn);


        btnLogin.setOnClickListener(this);

        return rootView;

    }

    public void login(){

        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        ParseUser.logInInBackground(email, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if(user != null){
                    //Logado com sucesso

                    editTextEmail.setText("LOGUEI");

                } else {
                    //Login fail

                    editTextEmail.setText("AINDA N TEM CONTA");
                    progressBar.setVisibility(View.GONE);

                }

            }
        });

    }

    private void animacaoLoading() {

        Animation fadeOut = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out_animation);
        btnLogin.startAnimation(fadeOut);

    }

    @Override
    public void onClick(View v) {

        if(v == btnLogin){

            animacaoLoading();
            progressBar.setVisibility(View.VISIBLE);
            login();

        }

    }
}
