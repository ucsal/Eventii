package br.edu.ucsal.eventii.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;

import br.edu.ucsal.eventii.R;
import br.edu.ucsal.eventii.domain.EventBuilder;

public class HomeFragment extends Fragment{

    private SwipeRefreshLayout swipeRefreshLayout;

    private static ArrayList<ParseObject> listaEventos;

    private TextView textViewNoEvents;
    private ListView listViewEvents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home,container,false);

        listaEventos = new ArrayList<>();

        findViewsById(rootView);

        //EventBuilder.fetchEventList(listaEventos);

        if(listaEventos.isEmpty()){

            listViewEvents.setVisibility(View.INVISIBLE);
            textViewNoEvents.setVisibility(View.VISIBLE);

        }

        return rootView;
    }

    private void findViewsById(ViewGroup rootView) {

        textViewNoEvents = rootView.findViewById(R.id.fragment_home_textViewNoEvents);
        listViewEvents = rootView.findViewById(R.id.fragment_home_listaEventos);
        swipeRefreshLayout = rootView.findViewById(R.id.fragment_home_swiperefresh);

    }
}
