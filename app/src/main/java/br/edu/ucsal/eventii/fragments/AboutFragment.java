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

import br.edu.ucsal.eventii.R;


public class AboutFragment extends Fragment{

    private Button btnLogin;
    private ViewPager viewPager;

    private final int TELA_LOGIN = 2;

    @SuppressLint("ValidFragment")
    public AboutFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public AboutFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView  = (ViewGroup) inflater.inflate(R.layout.fragment_about_slide,container,false);

        btnLogin = rootView.findViewById(R.id.fragment_about_btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v == btnLogin){

                   viewPager.setCurrentItem(TELA_LOGIN);

                }

            }
        });

        return rootView;

    }
}
