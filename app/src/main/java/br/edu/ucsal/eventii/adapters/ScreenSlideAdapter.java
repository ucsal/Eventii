package br.edu.ucsal.eventii.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import br.edu.ucsal.eventii.fragments.AboutFragment;
import br.edu.ucsal.eventii.fragments.InviteFragment;
import br.edu.ucsal.eventii.fragments.LoginFragment;
import br.edu.ucsal.eventii.fragments.RegisterFragment;

import static br.edu.ucsal.eventii.activity.MainActivity.NUM_PAGINAS;

public class ScreenSlideAdapter extends FragmentPagerAdapter {

    private final int ABOUT_POSITION = 0;
    private final int INVITE_POSITION = 1;
    private final int LOGIN_POSITION = 2;
    private final int REGISTER_POSITION = 3;

    ViewPager viewPager;

    public ScreenSlideAdapter(FragmentManager fm, ViewPager viewPager){
        super(fm);

        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {


        if(position == REGISTER_POSITION){

            return new RegisterFragment(viewPager);

        }else if(position == INVITE_POSITION){

            return new InviteFragment(viewPager);

        }else if(position == LOGIN_POSITION){

            return new LoginFragment();

        }else if(position == ABOUT_POSITION){

            return new AboutFragment(viewPager);

        }

        return new AboutFragment(viewPager);

    }

    @Override
    public int getCount() {
        return NUM_PAGINAS;
    }
}
