package iyp.cookbook;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import iyp.cookbook.account.Account;
import iyp.cookbook.fragment.MenuCommunityFragment;
import iyp.cookbook.fragment.MenuIngredientsFragment;
import iyp.cookbook.fragment.MenuOverviewFragment;
import iyp.cookbook.fragment.MenuStepsFragment;

public class RecipeItemView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MenuOverviewFragment.OnFragmentInteractionListener,
        MenuIngredientsFragment.OnFragmentInteractionListener,
        MenuStepsFragment.OnFragmentInteractionListener,
        MenuCommunityFragment.OnFragmentInteractionListener{

    Account account;
    ViewPager viewpager;
    SectionsPagerAdapter section;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item_view);
        TextView profilUname, profilMail;
        ImageView profilImage, menuchart;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe_item_view);
        menuchart = (ImageView) findViewById(R.id.menuChart);
        menuchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "your chart are empty", Toast.LENGTH_SHORT).show();
            }
        });

        this.account = (Account) getIntent().getSerializableExtra("user");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);//remove hamburger icon
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //tabbed TODO fragment help
        section= new SectionsPagerAdapter(getSupportFragmentManager());
        viewpager = (ViewPager) findViewById(R.id.menuHome);
        viewpager.setAdapter(section);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        //how to edit text in nav list header
        View header = navigationView.getHeaderView(0);
        profilUname = (TextView) header.findViewById(R.id.UnameTxt);
        profilMail = (TextView) header.findViewById(R.id.Mailtxt);
        profilImage = (ImageView) header.findViewById(R.id.Profilview);
        profilUname.setText(this.account.getRealname());
        profilMail.setText(this.account.getMail());
        profilImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You're Nice", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.menuBacon) {
            // Handle the camera action
        } else if (id == R.id.menuBeef) {

        } else if (id == R.id.menuChicken) {

        } else if (id == R.id.menuSnack) {

        } else if (id == R.id.menuSeafood) {

        } else if (id == R.id.nav_send || id==R.id.nav_share) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return  MenuOverviewFragment.newInstance();
            }else if(position==1){
                return  MenuIngredientsFragment.newInstance();
            }else if(position==3){
                return MenuStepsFragment.newInstance();
            }else if(position==4){
                return MenuCommunityFragment.newInstance();
            }else{
                return MenuOverviewFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
