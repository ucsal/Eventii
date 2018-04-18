package br.edu.ucsal.eventii.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.Parse;

import br.edu.ucsal.eventii.R;
import br.edu.ucsal.eventii.adapters.ScreenSlideAdapter;
import br.edu.ucsal.eventii.fragments.InviteFragment;

public class MainActivity extends FragmentActivity {

    public static final int NUM_PAGINAS = 4;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //PARSE INICIALIZA

        Parse.initialize(this);

        //Instancia o ViewPager e o pagerAdapter
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlideAdapter(getSupportFragmentManager(), viewPager);
        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem() == 0){
            super.onBackPressed();
        }else{
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }

    }
}
