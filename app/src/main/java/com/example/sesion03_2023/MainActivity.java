    package com.example.sesion03_2023;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import com.example.sesion03_2023.Fragments.ChanelFragment;
    import com.example.sesion03_2023.Fragments.GamingFragment;
    import com.example.sesion03_2023.Fragments.HomeFragment;
    import com.example.sesion03_2023.Fragments.MusicFragment;
    import com.example.sesion03_2023.Fragments.ProfileFragment;
    import com.example.sesion03_2023.Fragments.ShortsFragment;
    import com.example.sesion03_2023.Fragments.SubscriptonsFragment;
    import com.example.sesion03_2023.Fragments.TrendingFragment;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.android.material.navigation.NavigationBarView;
    import com.google.android.material.navigation.NavigationView;


    public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    
        DrawerLayout drawerLayout;
        BottomNavigationView bottomNavigationView;
        FragmentManager fragmentManager;
        Toolbar toolbar;
        FloatingActionButton fab;

        private User currentUser;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            fab = findViewById(R.id.fab);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
    
            drawerLayout = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
    
            NavigationView navigationView = findViewById(R.id.navigation_drawer);
            navigationView.setNavigationItemSelectedListener(this);
    
            bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setBackground(null);

    
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.bottom_home){
                        openFragment(new HomeFragment());
                        return true;
                    } else if (itemId == R.id.bottom_shorts) {
                        openFragment(new ShortsFragment());
                        return true;
                    }else if (itemId == R.id.bottom_subscriptions) {
                        openFragment(new SubscriptonsFragment());
                        return true;
                    } else if (itemId == R.id.bottom_profile) {
                        openFragment(new ProfileFragment());
                        return true;
                    }
                    return false;
                }
            });
    
            fragmentManager = getSupportFragmentManager();
            openFragment(new HomeFragment());
    
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "Upload Videos", Toast.LENGTH_SHORT).show();
                }
            });



    
        }



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            int itemId= item.getItemId();
            if (itemId == R.id.nav_chanel){
                openFragment(new ChanelFragment());
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_trending){
                openFragment(new TrendingFragment());
                Toast.makeText(this, "Trending", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_music){
                openFragment(new MusicFragment());
                Toast.makeText(this, "Music", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_gaming){
                openFragment(new GamingFragment());
                Toast.makeText(this, "Gaming", Toast.LENGTH_SHORT).show();
            }  else if (itemId == R.id.nav_logout) {
                currentUser = null; // Eliminar la referencia al usuario actual
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
                return true;
            }
    
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;


    
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            User user = (User) getIntent().getSerializableExtra("user");

            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.navigation_drawer_menu, menu);
            TextView textView = findViewById(R.id.nav_nombre);
            ImageView imageView = findViewById(R.id.nav_image);
            textView.setText(user.getFirstName());
            imageView.setImageResource(user.getProfileImageRes());
            return true;
        }

        @Override
        public void onBackPressed() {
    
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }else {
                super.onBackPressed();
            }
        }
    
        private void openFragment(Fragment fragment){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }

    }


