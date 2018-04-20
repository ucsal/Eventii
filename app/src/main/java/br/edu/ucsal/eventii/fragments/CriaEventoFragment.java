package br.edu.ucsal.eventii.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;

import br.edu.ucsal.eventii.R;
import br.edu.ucsal.eventii.activity.EventiiActivity;
import br.edu.ucsal.eventii.domain.EventBuilder;
import br.edu.ucsal.eventii.domain.Produto;

import static android.app.Activity.RESULT_OK;

public class CriaEventoFragment extends Fragment implements View.OnClickListener{

    private ProgressDialog progressDialog;

    private EditText nomeEvento;
    private TextView localEvento;
    private TextView dataEvento;

    private CheckBox listaCompras;
    private CheckBox playlist;

    private Button btnCriaEvento;

    private DatePickerDialog datePickerDialog;
    private Calendar currentDate;
    private int dia,mes,ano;
    private Place place;

    private AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cria_evento, container, false);

        findViewsById(rootView);

        progressDialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);

        currentDate = Calendar.getInstance();

        localEvento.setOnClickListener(this);
        dataEvento.setOnClickListener(this);
        nomeEvento.setOnClickListener(this);
        btnCriaEvento.setOnClickListener(this);


        return rootView;

    }

    private void findViewsById(ViewGroup rootView) {

        nomeEvento = rootView.findViewById(R.id.fragment_cria_editTextNomeEvento);
        localEvento = rootView.findViewById(R.id.fragment_cria_editTextlocal);
        dataEvento = rootView.findViewById(R.id.fragment_cria_editTextData);

        listaCompras = rootView.findViewById(R.id.fragment_cria_listaCompras);
        playlist = rootView.findViewById(R.id.fragment_cria_playlist);

        btnCriaEvento = rootView.findViewById(R.id.fragment_cria_btnCriaEvento);



    }

    @Override
    public void onClick(View v) {

        if(v == btnCriaEvento){

            progressDialog.setMessage("Validando evento");
            progressDialog.show();
            validateInputs();

        }

        if(v == nomeEvento){

            nomeEvento.requestFocus();

        }

        if(v == localEvento){

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(getActivity()), 2);

            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();

            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }



        }

        if(v == dataEvento){

            dia = currentDate.get(Calendar.DAY_OF_MONTH);
            mes = currentDate.get(Calendar.MONTH);
            ano = currentDate.get(Calendar.YEAR);

            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month++;
                    String data = dayOfMonth + "/" + month + "/" + year;

                    dataEvento.setText(data);

                }

            },ano,mes,dia);

            datePickerDialog.setTitle("Escolha a data do evento!");

            datePickerDialog.show();

        }

    }

    private void validateInputs() {

        String nomeEvento = this.nomeEvento.getText().toString();
        String dataEvento = this.dataEvento.getText().toString();
        String localEvento = this.localEvento.getText().toString();

        Boolean listaCompras = this.listaCompras.isChecked();
        Boolean playlist = this.playlist.isChecked();

        if(nomeEvento.isEmpty()){

            this.nomeEvento.setError("Seu evento precisa de um nome!");
            progressDialog.dismiss();

        }else if(dataEvento.isEmpty()){

            this.dataEvento.setError("Seu evento precisa de uma data!");
            progressDialog.dismiss();

        }else if(localEvento.isEmpty()){

            this.localEvento.setError("Seu evento precisa de um local!");
            progressDialog.dismiss();

        }else{

           progressDialog.setMessage("Cadastrando evento");
           registerEvent(nomeEvento,dataEvento,localEvento,listaCompras,playlist);

        }

    }

    private void registerEvent(String nomeEvento, String dataEvento, String localEvento, Boolean listaCompras, Boolean playlist) {

        buildEvento(nomeEvento,dataEvento,localEvento,listaCompras,playlist);



    }

    private void buildEvento(String nomeEvento, String dataEvento, String localEvento, Boolean listaCompras, Boolean playlist) {

        //Sem lista e playlist

            if(!listaCompras && !playlist){

                ParseObject evento = new ParseObject("Eventos");

                evento.put("nomeEvento", nomeEvento);
                evento.put("dataEvento", dataEvento);
                evento.put("localEvento", localEvento);
                evento.put("creatorId", ParseUser.getCurrentUser().getObjectId());

                evento.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){

                            eventoFetch();
                            progressDialog.dismiss();

                        }else{

                            eventoDidntFetch(e);
                            progressDialog.dismiss();

                        }

                    }
                });

            }else if(listaCompras && !playlist){ //Sem playlist

                ParseObject produto = new ParseObject("Produtos");

                ParseObject evento = new ParseObject("Eventos");

                evento.put("nomeEvento", nomeEvento);
                evento.put("dataEvento", dataEvento);
                evento.put("localEvento", localEvento);
                evento.put("creatorId", ParseUser.getCurrentUser().getObjectId());

                evento.add("listaCompras", Arrays.asList(produto));

                evento.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {


                        if(e == null){

                            eventoFetch();
                            progressDialog.dismiss();

                        }else{

                            eventoDidntFetch(e);
                            progressDialog.dismiss();

                        }

                    }
                });

            }else if(!listaCompras && playlist){ // Sem lista

                ParseObject evento = new ParseObject("Eventos");

                evento.put("nomeEvento", nomeEvento);
                evento.put("dataEvento", dataEvento);
                evento.put("localEvento", localEvento);
                evento.put("creatorId", ParseUser.getCurrentUser().getObjectId());

                evento.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {


                        if(e == null){

                            eventoFetch();
                            progressDialog.dismiss();

                        }else{

                            eventoDidntFetch(e);
                            progressDialog.dismiss();

                        }

                    }
                });

                //SPOTIFY PLAYLIST

            }else{ // Com tudo

                ParseObject produto = new ParseObject("Produtos");

                ParseObject evento = new ParseObject("Eventos");

                evento.put("nomeEvento", nomeEvento);
                evento.put("dataEvento", dataEvento);
                evento.put("localEvento", localEvento);
                evento.put("creatorId", ParseUser.getCurrentUser().getObjectId());

                evento.add("listaCompras", produto);

                evento.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {


                        if(e == null){

                            eventoFetch();
                            progressDialog.dismiss();

                        }else{

                            eventoDidntFetch(e);
                            progressDialog.dismiss();

                        }

                    }
                });

                //SPOTIFY PLAYLIST

            }

        }

    private void eventoFetch() {

        builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Pronto, agora é so organizar sua festa de arromba!").setTitle("Evento criado!");

        builder.setPositiveButton("Beleza", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(getContext(), EventiiActivity.class));
                getActivity().finish();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void eventoDidntFetch(ParseException e){

        builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(e.getMessage()).setTitle("Ocorreu um erro :(");

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){

            place = PlacePicker.getPlace(getContext(),data);

            localEvento.setText(place.getAddress());

        }

    }
}
