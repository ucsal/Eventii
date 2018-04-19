package br.edu.ucsal.eventii.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.parse.ParseUser;

import br.edu.ucsal.eventii.R;

public class EventiiActivity extends AppCompatActivity {

    private AlertDialog alert;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventii);

        //Drawer
        drawerLayout = findViewById(R.id.drawer_layout);

            //Drawer listener

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.menu_sair:
                                sair();
                                return true;

                        }


                        return true;
                    }
                }
        );

        //Toolbar
        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_claro);

    }

    @Override
    public void onBackPressed() {

        drawerLayout.closeDrawers();

    }

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
}
