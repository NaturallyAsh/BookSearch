package com.example.ashleighwilson.booksearch;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.bumptech.glide.Glide;
import com.example.ashleighwilson.booksearch.dagger.Injector;
import com.example.ashleighwilson.booksearch.models.AuthUser;
import com.example.ashleighwilson.booksearch.models.Review;
import com.example.ashleighwilson.booksearch.models.UserBook;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener
{
    private static final String TAG = MainActivity.class.getSimpleName();

    public NavigationView mNavigationView;
    private String QUERY_KEY;
    private Button authenticat_BT;
    View navHeaderView;
    CircleImageView circleImageView;
    TextView headerTV;
    DrawerLayout drawer;
    @Inject
    PreferenceUser preferenceUser;
    @Inject
    AuthUser currentUser;
    UserBook userBook;
    Review userReview;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Injector.getInstance().inject(this);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        authenticat_BT = findViewById(R.id.authenticate_button);
        authenticat_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mNavigationView = findViewById(R.id.nav_view);
        //mNavigationView.bringToFront();
        mNavigationView.setNavigationItemSelectedListener(this);
        navHeaderView = mNavigationView.inflateHeaderView(R.layout.nav_header_main_nav);
        circleImageView = navHeaderView.findViewById(R.id.nav_header_imageView);
        headerTV = navHeaderView.findViewById(R.id.nav_header_tv);
        if (currentUser != null) {
            //Log.i(TAG, "current user: " + currentUser);
            authenticat_BT.setVisibility(View.GONE);
            if (currentUser.getImage_url() != null) {
                Glide.with(this)
                        .load(currentUser.getImage_url())
                        .into(circleImageView);
                //headerTV.setVisibility(View.GONE);
                headerTV.setText(currentUser.getName());
            } else {
                authenticat_BT.setVisibility(View.VISIBLE);
            }
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            UserFragment userFragment = new UserFragment();
            transaction
                    .add(R.id.main_frag_container, userFragment, TAG)
                    .commit();
        } else {
            getSupportFragmentManager().findFragmentByTag(TAG);
        }

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            QUERY_KEY = searchIntent.getStringExtra(SearchManager.QUERY);
            Log.i(TAG, "QUERY_KEY = " + QUERY_KEY);
            Intent intent = new Intent(MainActivity.this, BookActivity.class);
            intent.putExtra("key", QUERY_KEY);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.anim.
        switch (item.getItemId()) {
            case R.id.menu_qr:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId())
        {
            case R.id.favorites:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_reader:
                Intent readerIntent = new Intent(this, ReaderActivity.class);
                startActivity(readerIntent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, intent);
        if (scanningResult != null)
        {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            if (scanContent != null && scanFormat != null && scanFormat.equalsIgnoreCase("EAN_13"))
            {
                String bookSearchString = "isbn:" + scanContent;
                QUERY_KEY = bookSearchString;

                Intent intent1 = new Intent(getApplicationContext(), BookActivity.class);
                intent1.putExtra("key", QUERY_KEY);
                startActivity(intent1);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Not a valid scan!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No book scan data" +
                    "received", Toast.LENGTH_SHORT).show();
        }
    }
}
