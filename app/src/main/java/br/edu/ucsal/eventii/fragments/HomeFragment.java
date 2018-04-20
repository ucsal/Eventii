package br.edu.ucsal.eventii.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import br.edu.ucsal.eventii.R;
import br.edu.ucsal.eventii.adapters.HomeAdapter;

public class HomeFragment extends Fragment{

    private SwipeRefreshLayout swipeRefreshLayout;

    private ParseQuery<ParseObject> query;
    private ArrayList<ParseObject> listaEventos;
    private ArrayAdapter<ParseObject> adapter;

    private TextView textViewNoEvents;
    private ListView listViewEvents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home,container,false);

        listaEventos = new ArrayList<>();

        findViewsById(rootView);

        adapter = new HomeAdapter(getActivity(), listaEventos);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getEventos();

                    }
                }
        );

        listViewEvents.setAdapter(adapter);

        //Recuperar Eventos

        getEventos();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        getEventos();
    }

    private void getEventos() {


        swipeRefreshLayout.setRefreshing(true);

        query = ParseQuery.getQuery("Eventos");
        query.whereEqualTo("creatorId", ParseUser.getCurrentUser().getObjectId());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null){

                    if(objects.size() > 0){

                        listaEventos.clear();

                        for(ParseObject parseObject: objects){

                            listaEventos.add(parseObject);

                        }

                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                        listViewEvents.setVisibility(View.VISIBLE);
                        textViewNoEvents.setVisibility(View.INVISIBLE);

                    }

                }

            }
        });




    }


    private void findViewsById(ViewGroup rootView) {

        textViewNoEvents = rootView.findViewById(R.id.fragment_home_textViewNoEvents);
        listViewEvents = rootView.findViewById(R.id.fragment_home_listaEventos);
        swipeRefreshLayout = rootView.findViewById(R.id.fragment_home_swiperefresh);

    }
}
