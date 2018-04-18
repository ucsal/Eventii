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

public class InviteFragment extends Fragment{

    private ViewPager viewPager;
    private final int TELA_REGISTER = 3;

    private Button btnRegister;

    @SuppressLint("ValidFragment")
    public InviteFragment(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public InviteFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView  = (ViewGroup) inflater.inflate(R.layout.fragment_invite_slide,container,false);

        btnRegister = rootView.findViewById(R.id.fragment_invite_bntRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v == btnRegister){

                    viewPager.setCurrentItem(TELA_REGISTER);

                }

            }
        });

        return rootView;

    }
}
