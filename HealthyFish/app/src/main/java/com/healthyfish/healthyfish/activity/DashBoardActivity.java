package com.healthyfish.healthyfish.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.healthyfish.healthyfish.R;
import com.healthyfish.healthyfish.adapter.CustomAdapter;
import com.healthyfish.healthyfish.dialogbox.AlertBox;
import com.healthyfish.healthyfish.fragments.AboutUs;
import com.healthyfish.healthyfish.fragments.Cancellation;
import com.healthyfish.healthyfish.fragments.ContactUs;
import com.healthyfish.healthyfish.fragments.FAQ;
import com.healthyfish.healthyfish.fragments.HomeFragment;
import com.healthyfish.healthyfish.fragments.PrivacyPolicy;

import com.healthyfish.healthyfish.fragments.ReturnRefunds;

import com.healthyfish.healthyfish.fragments.TermsConditions;
import com.healthyfish.healthyfish.fragments.YourAccount;
import com.healthyfish.healthyfish.fragments.YourOrders;
import com.healthyfish.healthyfish.manager.PrefManager;
import com.healthyfish.healthyfish.manager.SetBagdeCount;
import com.healthyfish.healthyfish.model.ItemObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by RohiniAjith on 5/31/2016.
 */
public class DashBoardActivity extends ActionBarActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private Toolbar topToolBar;
    TextView Name;
    ImageView profile;
    MenuItem filterMenuItem;
    String item_array[],items_imgs_array[];
    RelativeLayout badgeLayout;
    View itemChooser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        mTitle = mDrawerTitle =getString(R.string.app_name);
         item_array=getResources().getStringArray(R.array.nav_drawer_labels);
        items_imgs_array=getResources().getStringArray(R.array.nav_drawer_icons);
        topToolBar = (Toolbar)findViewById(R.id.toolbar);
        topToolBar.setTitleTextColor(getResources().getColor(R.color.windowBackground));
        setSupportActionBar(topToolBar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_drawer_header,null, false);
       Name= (TextView)listHeaderView.findViewById(R.id.usernameTextView);
        profile=(ImageView)listHeaderView.findViewById(R.id.avatarImageView);
        char first_letter=Name.getText().toString().charAt(0);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRound(String.valueOf(first_letter), color);
        profile.setImageDrawable(drawable);
        mDrawerList.addHeaderView(listHeaderView);
        System.out.println("Session Id"+new PrefManager(DashBoardActivity.this).receivedSession());
        List<ItemObject> listViewItems = new ArrayList<ItemObject>();

        for(int i=0;i<item_array.length;i++)
        {
            System.out.println("Image name:::"+i+" value: "+item_array[i]);
System.out.println("Image icon:::"+i+" value: "+items_imgs_array[i]);
            listViewItems.add(new ItemObject(item_array[i], items_imgs_array[i]));

        }
        mDrawerList.setAdapter(new CustomAdapter(this, listViewItems));

        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(DashBoardActivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        selectItemFragment(1);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                selectItemFragment(position);
            }
        });


    }

    private void selectItemFragment(int position) {

        if (position != 0) {


        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
        switch (position) {
            default:
            case 1:

                   fragment = new HomeFragment();

                bundle.putString("category", "HealthyFish");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
//            case 2:
//                 fragment = new ShopbyCategory();
//                bundle.putString("category", "ShopbyCategory");
//                break;
            case 2:
                fragment = new YourOrders();
                bundle.putString("category", "Orders");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
            case 3:
                fragment = new YourAccount();
                bundle.putString("category", "Account");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
            case 4:
                fragment = new ContactUs();
                bundle.putString("category", "Contact Us");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
            case 5:
                fragment = new AboutUs();
                bundle.putString("category", "AboutUs");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();


                break;
            case 6:
                new AlertBox(DashBoardActivity.this).alertBox_rateus();
                break;
            case 7:
//                fragment = new ShareApp();
//                bundle.putString("category", "ShareApp");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.jaivakarshakan.com.com.jaivakarshakan");
                startActivity(Intent.createChooser(intent, "Share it "));
                break;
            case 8:
                fragment = new FAQ();
                bundle.putString("category", "FAQ");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
            case 9:
                fragment = new Cancellation();
                bundle.putString("category", "Cancellation");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
            case 10:
                fragment = new ReturnRefunds();
                bundle.putString("category", "ReturnRefunds");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
            case 11:
                fragment = new TermsConditions();
                bundle.putString("category", "TermsConditions");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
            case 12:
                fragment = new PrivacyPolicy();
                bundle.putString("category", "PrivacyPolicy");
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();

                break;
            case 13:
                PrefManager prefManager= new PrefManager(DashBoardActivity.this);
                prefManager.logout();
                DashBoardActivity.this.finish();
                break;
        }

        }

        mDrawerList.setItemChecked(position, true);
     //   setTitle(item_array[position]);
        mDrawerLayout.closeDrawer(mDrawerList);

    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.healthyfish_menu, menu);
//        for (int i = 0; i < menu.size(); i++) {
//            MenuItem item = menu.getItem(i);
//            if (item.getItemId() == R.id.action_shoping_cart) {
//                itemChooser = item.getActionView();
//                if (itemChooser != null) {
//                    itemChooser.setOnClickListener(this);
//                }
//            }
        //}

        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        new SetBagdeCount().setBadgeCount(this, icon, "0");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        System.out.println("ID: "+id);
        System.out.println("ID cart: "+R.id.action_cart);
        if (id==R.id.action_cart)
        {
            System.out.println("ID EQUAL ");
            Intent i= new Intent(DashBoardActivity.this,ShopingCartActivity.class);
            startActivity(i);
        }

        //noinspection SimplifiableIfStatement

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {

            Intent i= new Intent(DashBoardActivity.this,ShopingCartActivity.class);
            startActivity(i);

    }
    @Override
    public void onBackPressed() {


//        if(selectedFragment.equals(fragmentA) && fragmentA.hasExpandedRow()) {
//            fragmentA.collapseRow();
//        } else if(selectedFragment.equals(fragmentA) && fragmentA.isShowingLoginView()) {
//            fragmentA.hideLoginView();
//        } else if(selectedFragment.equals(fragmentA)) {
//            popBackStack();
//        } else if(selectedFragment.equals(fragmentB) && fragmentB.hasCondition1()) {
//            fragmentB.reverseCondition1();
//        } else if(selectedFragment.equals(fragmentB) && fragmentB.hasCondition2()) {
//            fragmentB.reverseCondition2();
//        } else if(selectedFragment.equals(fragmentB)) {
//            popBackStack();
//        } else {
//            // handle by activity
//            super.onBackPressed();
//        }
    }
}


