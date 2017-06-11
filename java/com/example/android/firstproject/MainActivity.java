package com.example.android.firstproject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;

public class MainActivity extends AppCompatActivity implements ListviewAdapter.ListItemClickListener {

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private ProgressDialog mProgressDialog;
    private TabLayout tabLayout;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setCustomView(R.layout.action_bar_home);

//        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar, null);
//        final ActionBar actionBar = getActionBar();
////        actionBar.setDisplayShowHomeEnabled(false);
////        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(actionBarLayout);


//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.action_bar);
//        View view =getSupportActionBar().getCustomView();
//        final int actionBarColor = getResources().getColor(R.color.action_bar);
//        actionBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));

//        final Button actionBarTitle = (Button) findViewById(R.id.action_bar_title);
//        actionBarTitle.setText("Index(2)");
//
//        final Button actionBarSent = (Button) findViewById(R.id.action_bar_sent);
//        actionBarSent.setText("Sent");
//
//        final Button actionBarStaff = (Button) findViewById(R.id.action_bar_staff);
//        actionBarStaff.setText("Staff");
//
//        final Button actionBarLocations = (Button) findViewById(R.id.action_bar_locations);
//        actionBarLocations.setText("HIPPA Locations");


        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new BlankFragment(),
                    new BlankFragment(),
                    new BlankFragment(),
            };
            private final String[] mFragmentNames = new String[]{
                    getString(R.string.stringfirst),
                    getString(R.string.stringsecond),
                    getString(R.string.stringthird)
            };

            @Override
            public int getCount() {
                return mFragments.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        Method_checkNetworkStatus();

//         mAdView = (AdView)findViewById(R.id.adView);
//
//        AdRequest request = new AdRequest.Builder().build();
////        AdRequest request = new AdRequest.Builder().addTestDevice("33BE2250B43518CCDA7DE426D04EE232").build();
//        mAdView.loadAd(request);

        MobileAds.initialize(this, "ca-app-pub-6664933490379364~5816453534");
        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("2CCC4D7BCE45823B826E2434A32371ED")
                .build();
        mAdView.loadAd(adRequest);

        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.NativeExpressAdView);

        AdRequest request = new AdRequest.Builder().addTestDevice("2CCC4D7BCE45823B826E2434A32371ED").build();
        adView.loadAd(request);
//
//        WebView wv;
//        wv = (WebView) findViewById(R.id.webView1);
//        wv.loadUrl("file:///android_asset/shree.html");

    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }

    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) && networkInfo.isConnected();
    }

    public void Method_checkNetworkStatus() {
        if (isNetworkConnected()) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage("Please wait...");
//            mProgressDialog.setCancelable(false);
//            mProgressDialog.show();

//            setCancelabletartDownload();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("It looks like your internet connection is off. Please turn it " +
                            "on and try again")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,TaskActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onListItemClick(int postId, Recorddata recorddata) {
        Toast.makeText(MainActivity.this, postId + "", Toast.LENGTH_LONG).show();

        tabLayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.INVISIBLE);

        MainFragment blankFragment1 = new MainFragment(postId, recorddata);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().popBackStack();
        fragmentTransaction.replace(R.id.content_main, blankFragment1, "BlankFragment1");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        MainFragment newFragment = new MainFragment(postId,recorddata);
//        getSupportFragmentManager().popBackStack();
////        newFragment.setArguments(bundle);
//        ft.addToBackStack(null);
//        ft.show(newFragment);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tabLayout.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
    }
}
