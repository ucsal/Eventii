package br.edu.ucsal.eventii.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import br.edu.ucsal.eventii.R;

public class EditarActivity extends AppCompatActivity {

    private ParseObject evento;
    private EditText editTextNome;
    private TextView editTextData;
    private TextView editTextLocal;
    private Button btnCompras;
    private Button btnSalvar;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        findViewsById();

        String objectId = getIntent().getStringExtra("objectId");

        retriveEvento(objectId);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Carregando...");
        toolbar.setNavigationIcon(R.drawable.ic_action_back);

    }

    private void findViewsById() {

        toolbar = findViewById(R.id.toolbar_back);
        editTextData = findViewById(R.id.edit_editTextData);
        editTextNome = findViewById(R.id.edit_editTextNomeEvento);
        editTextLocal = findViewById(R.id.edit_editTextlocal);
        btnCompras = findViewById(R.id.btn_listaCompras);
        btnSalvar = findViewById(R.id.edit_btnSalvarEvento);

    }

    private void retriveEvento(String objectId) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Eventos");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                evento = object;

                String nomeObject = (String) object.get("nomeEvento");
                String localObject = (String) object.get("localEvento");
                String dataObject = (String) object.get("dataEvento");

                editTextData.setText(dataObject);
                editTextLocal.setText(localObject);
                editTextNome.setText(nomeObject);

                toolbar.setTitle("Editar " + nomeObject);

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
