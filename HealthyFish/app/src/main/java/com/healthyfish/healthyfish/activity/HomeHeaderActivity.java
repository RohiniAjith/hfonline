//package com.healthyfish.healthyfish.activity;
//
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.amulyakhare.textdrawable.TextDrawable;
//import com.healthyfish.healthyfish.R;
//import com.healthyfish.healthyfish.adapter.CustomAdapter;
//import com.healthyfish.healthyfish.fragments.AboutUs;
//import com.healthyfish.healthyfish.fragments.Cancellation;
//import com.healthyfish.healthyfish.fragments.ContactUs;
//import com.healthyfish.healthyfish.fragments.FAQ;
//import com.healthyfish.healthyfish.fragments.HomeFragment;
//import com.healthyfish.healthyfish.fragments.PrivacyPolicy;
//import com.healthyfish.healthyfish.fragments.RateUs;
//import com.healthyfish.healthyfish.fragments.RecentlyViewedItems;
//import com.healthyfish.healthyfish.fragments.ReturnRefunds;
//import com.healthyfish.healthyfish.fragments.HomeHeader_Fragment;
//import com.healthyfish.healthyfish.fragments.ShareApp;
//import com.healthyfish.healthyfish.fragments.ShopbyCategory;
//import com.healthyfish.healthyfish.fragments.TermsConditions;
//import com.healthyfish.healthyfish.fragments.YourAccount;
//import com.healthyfish.healthyfish.fragments.YourOrders;
//import com.healthyfish.healthyfish.fragments.YourWishList;
//import com.healthyfish.healthyfish.model.HomeHeaderListItem;
//import com.healthyfish.healthyfish.model.ItemObject;
//import com.healthyfish.healthyfish.model.MenuModel;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by User on 18-09-2016.
//// */
//public class HomeHeaderActivity extends AppCompatActivity {
//    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerList;
//
//    private CharSequence mTitle;
//    private CharSequence mDrawerTitle;
//    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
//    private Toolbar topToolBar;
//    TextView Name;
//    ImageView profile;
//    String item_array[],items_imgs_array[];
//    String category_id,category_title;
//    Menu menu;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_header);
//        Intent in= getIntent();
//        category_id = in.getStringExtra("category_id");
//        category_title = in.getStringExtra("category_title");
//        topToolBar = (Toolbar)findViewById(R.id.ahh_header_toolbar);
//        topToolBar.setTitleTextColor(getResources().getColor(R.color.windowBackground));
//        setSupportActionBar(topToolBar);
//        getSupportActionBar().setTitle(category_title);
//        item_array=getResources().getStringArray(R.array.nav_drawer_labels);
//        items_imgs_array=getResources().getStringArray(R.array.nav_drawer_icons);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.home_left_drawer);
//        LayoutInflater inflater = getLayoutInflater();
//        View listHeaderView = inflater.inflate(R.layout.nav_drawer_header,null, false);
//        Name= (TextView)listHeaderView.findViewById(R.id.usernameTextView);
//        profile=(ImageView)listHeaderView.findViewById(R.id.avatarImageView);
//        char first_letter=Name.getText().toString().charAt(0);
//        Random rnd = new Random();
//        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//
//        TextDrawable drawable = TextDrawable.builder()
//                .beginConfig()
//                .withBorder(4) /* thickness in px */
//                .endConfig()
//                .buildRound(String.valueOf(first_letter), color);
//        profile.setImageDrawable(drawable);
//        mDrawerList.addHeaderView(listHeaderView);
//
//        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
//
//        for(int i=0;i<item_array.length;i++)
//        {
//            System.out.println("Image name:::"+i+" value: "+item_array[i]);
//            System.out.println("Image icon:::"+i+" value: "+items_imgs_array[i]);
//            listViewItems.add(new ItemObject(item_array[i], items_imgs_array[i]));
//
//        }
//        mDrawerList.setAdapter(new CustomAdapter(this, listViewItems));
//
//        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(HomeHeaderActivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                getSupportActionBar().setTitle(mTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle(mDrawerTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//
//        // Set the drawer toggle as the DrawerListener
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        selectItemFragment(1);
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // make Toast when click
//                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
//                selectItemFragment(position);
//            }
//        });
//        Fragment fragment = new HomeHeader_Fragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("category_id", category_id);
//        bundle.putString("category_title",category_title);
//
//        fragment.setArguments(bundle);
//        String backStateName = fragment.getClass().getName();
//
//        FragmentManager manager = getSupportFragmentManager();
//        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
//
//        if (!fragmentPopped){ //fragment not in back stack, create it.
//            FragmentTransaction ft = manager.beginTransaction();
//            ft.replace(R.id.home_fragment_container, fragment);
//            ft.addToBackStack(null);
//            ft.commit();
//        }
//    }
//    private void selectItemFragment(int position) {
//
//        if (position != 0) {
//
//
//            Fragment fragment = null;
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            switch (position) {
//                default:
//                case 1:
//
//                    fragment = new HomeFragment();
//                    break;
//                case 2:
//                    fragment = new ShopbyCategory();
//                    break;
//                case 3:
//                    fragment = new YourOrders();
//                    break;
//                case 4:
//                    fragment = new YourAccount();
//                    break;
//                case 5:
//                    fragment = new ContactUs();
//                    break;
//                case 6:
//                    fragment = new AboutUs();
//                    break;
//                case 7:
//                    fragment = new RateUs();
//                    break;
//                case 8:
//                    fragment = new ShareApp();
//                    break;
//                case 9:
//                    fragment = new FAQ();
//                    break;
//                case 10:
//                    fragment = new Cancellation();
//                    break;
//                case 11:
//                    fragment = new ReturnRefunds();
//                    break;
//                case 12:
//                    fragment = new TermsConditions();
//                    break;
//                case 13:
//                    fragment = new PrivacyPolicy();
//                    break;
//            }
//            fragmentManager.beginTransaction().replace(R.id.home_fragment_container, fragment).commit();
//        }
//
//        mDrawerList.setItemChecked(position, true);
//        //   setTitle(item_array[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
//
//    }
//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//      //
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//}
