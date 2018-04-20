package br.edu.ucsal.eventii.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ucsal.eventii.R;
import br.edu.ucsal.eventii.fragments.CriaEventoFragment;
import br.edu.ucsal.eventii.fragments.HomeFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class EventiiActivity extends AppCompatActivity {

    private static ParseObject avatar;
    private AlertDialog alert;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private  View hView;

    private ParseQuery<ParseObject> query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventii);

        //Header view

        navigationView = findViewById(R.id.nav_view);

        setHeaderUserLayout();

        //Drawer
        drawerLayout = findViewById(R.id.drawer_layout);



            //Drawer listener

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        Fragment fragment = null;
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        switch (item.getItemId()){

                            case R.id.menu_sair:
                                sair();
                                return true;

                            case R.id.menu_home:

                                fragment = new HomeFragment();


                                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                                setTitle(R.string.app_name);

                                drawerLayout.closeDrawers();

                                return true;

                            case R.id.menu_criarEvento:

                                fragment = new CriaEventoFragment();

                                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                                setTitle(item.getTitle());

                                drawerLayout.closeDrawers();

                                return true;

                        }




                        return true;
                    }
                }
        );

        //Fragment initialize home

        Fragment fragment = new HomeFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();


        //Toolbar
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_claro);

    }

    private void setHeaderUserLayout() {

        hView =  navigationView.getHeaderView(0);

        TextView nomeUser = hView.findViewById(R.id.nav_header_username);
        TextView telefoneUser = hView.findViewById(R.id.nav_header_telefone);

        //Imagem
        final CircleImageView imagemUser = hView.findViewById(R.id.nav_header_profile_image);

        imagemUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                escolherFotoParaUpload();

            }
        });

        //

        nomeUser.setText(ParseUser.getCurrentUser().getUsername());
        telefoneUser.setText(fetchTelefoneUser());

        fecthAvatarUser(imagemUser);

    }

    private void escolherFotoParaUpload() {


        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == 1 && resultCode == RESULT_OK && data != null){

            Uri localImagem = data.getData();

            try {

                final Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagem);

                ByteArrayOutputStream steam = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG,75,steam);

                byte[] byteArray = steam.toByteArray();

                final ParseFile imagemNova = new ParseFile("user.png", byteArray);

                final ParseQuery<ParseObject> query = ParseQuery.getQuery("Avatar");

                query.whereEqualTo("idUser", ParseUser.getCurrentUser().getObjectId());
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if(e ==null){

                            objects.get(0).put("avatar", imagemNova);
                            objects.get(0).saveInBackground();

                        }

                    }
                });

                Toast.makeText(this, "Foto atualizada!", Toast.LENGTH_SHORT).show();

                CircleImageView imagemUser = hView.findViewById(R.id.nav_header_profile_image);

                Picasso.get().load(localImagem).into(imagemUser);



            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void fecthAvatarUser(final CircleImageView imageView){

        query = ParseQuery.getQuery("Avatar");
        query.whereEqualTo("idUser", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null){

                    Picasso.get()
                            .load(objects.get(0).getParseFile("avatar").getUrl())
                            .into(imageView);

                }

            }
        });


    }

    private String fetchTelefoneUser() {

        String telefone = "";

        telefone = ParseUser.getCurrentUser().getString("telefone");

        PhoneNumberUtils.formatNumber(telefone, "55");

        return telefone;

    }

    @Override
    public void onBackPressed() {

        drawerLayout.closeDrawers();

    }

    //Tollbar

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void sair() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sair");

        builder.setMessage("Deseja mesmo sair?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                logout();

            }
        });

        builder.setNegativeButton("Agora não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert = builder.create();

        alert.show();

    }

    private void logout() {

        ParseUser.logOut();
        finishAndRemoveTask();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }
}
