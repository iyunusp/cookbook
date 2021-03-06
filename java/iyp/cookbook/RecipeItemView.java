package iyp.cookbook;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import iyp.cookbook.account.Account;
import iyp.cookbook.fragment.MenuCommunityFragment;
import iyp.cookbook.fragment.MenuIngredientsFragment;
import iyp.cookbook.fragment.MenuOverviewFragment;
import iyp.cookbook.fragment.MenuStepsFragment;
import iyp.cookbook.fragment.communicate;
import iyp.cookbook.listing.CommentData;
import iyp.cookbook.listing.MenuData;

public class RecipeItemView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MenuOverviewFragment.OnFragmentInteractionListener,
        MenuIngredientsFragment.OnFragmentInteractionListener,
        MenuStepsFragment.OnFragmentInteractionListener,
        MenuCommunityFragment.OnFragmentInteractionListener,communicate{

    private Account account;
    private ViewPager viewpager;
    private SectionsPagerAdapter section;
    private MenuData menu;
    private TextView overview,ingredients,steps,community;
    private MenuOverviewFragment home;
    private MenuStepsFragment step;
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
        Button home,order;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recipe_item_view);
        menuchart = (ImageView) findViewById(R.id.menuChart);
        menuchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "your Bookmark are empty", Toast.LENGTH_SHORT).show();
            }
        });
        home= (Button)findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("HOME", true);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        //passing user detail
        this.account = (Account) getIntent().getSerializableExtra("user");
        this.menu=(MenuData) getIntent().getSerializableExtra("menu");
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //side nav setting
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
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ingredients.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                steps.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                community.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                overview.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                //selectedcolor
                if(position==0)
                    overview.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                else if(position==1)
                    ingredients.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                else if(position==2)
                    steps.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                else if(position==3)
                    community.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //clickable text view
        overview=(TextView)findViewById(R.id.textOverview);
        ingredients=(TextView)findViewById(R.id.textIngredients);
        steps=(TextView)findViewById(R.id.textSteps);
        community=(TextView)findViewById(R.id.textCommunity);
        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(0);
            }
        });
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(1);
            }
        });
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(2);
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(3);
            }
        });
        //side nav
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
        }else {
            Intent intent = new Intent();
            intent.putExtra("update", menu);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        String filter="";
        Intent fill= new Intent(getApplicationContext(),MenuFilter.class);
        fill.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fill.putExtra("user",account);
        if (id == R.id.menuAll) {
            // Handle the camera action
        } else if (id == R.id.menuMeat) {
            filter="meat";
        } else if (id == R.id.menuSalad) {
            filter="salad";
        } else if (id == R.id.menuDessert) {
            filter="dessert";
        } else if (id == R.id.menuQuick) {
            filter="quick";
        }else if (id == R.id.menuLow) {
            filter="low";
        }else if (id == R.id.menuFried) {
            filter="fried";
        }else if (id == R.id.menuBaked) {
            filter="baked";
        }else if (id == R.id.menuSoup) {
            filter="soup";
        }else  {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        fill.putExtra("filter",filter);
        getApplicationContext().startActivity(fill);
        //finish();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void sendData(List<CommentData> comment) {
        //TODO
        this.menu.comments=comment;
        menu.calcStar();
        home.updateStar(menu.star);
    }

    @Override
    public void enableStep() {
        step.enableStep();
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 1:return MenuIngredientsFragment.newInstance(menu.imageID,menu.ingredients,viewpager);
                case 2:return step=MenuStepsFragment.newInstance(menu.steps);
                case 3:return MenuCommunityFragment.newInstance(account.getUname(),account.getImageID(),menu.comments);
                default:{
                    overview.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    home=MenuOverviewFragment.newInstance(menu.Title,menu.Desc,menu.imageID,menu.minute,menu.star,viewpager);
                    return home;//default is home screen
                }
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
