package br.edu.ucsal.eventii.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.ParseObject;

import java.util.ArrayList;

import br.edu.ucsal.eventii.R;

public class HomeAdapter  extends ArrayAdapter<ParseObject>{

    private Context context;
    private ArrayList<ParseObject> eventos;

    private AppCompatTextView nomeEvento;
    private AppCompatTextView localEvento;
    private AppCompatTextView dataEvento;

    public HomeAdapter(@NonNull Context context, ArrayList<ParseObject> objects) {
        super(context,0, objects);

        this.context = context;
        this.eventos = objects;

    }

    @SuppressLint("WrongViewCast")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_eventos_layout, parent, false);

        }

        if(eventos.size() > 0){


            nomeEvento = view.findViewById(R.id.lista_eventos_nome);
            localEvento = view.findViewById(R.id.lista_eventos_local);
            dataEvento = view.findViewById(R.id.lista_eventos_data);

            ParseObject parseObject = eventos.get(position);

            String nomeObject = (String) parseObject.get("nomeEvento");
            String localObject = (String) parseObject.get("localEvento");
            String dataObject = (String) parseObject.get("dataEvento");

            nomeEvento.setText(nomeObject);
            localEvento.setText(localObject);
            dataEvento.setText(dataObject);

        }


        return view;

    }
}
