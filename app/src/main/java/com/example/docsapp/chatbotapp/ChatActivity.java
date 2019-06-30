package com.example.docsapp.chatbotapp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.docsapp.chatbotapp.Fragment.ChatFragment;

public class ChatActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBar appBar;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        fragmentManager = getSupportFragmentManager();
        initializeViews();
        Bundle bundle = new Bundle();
        bundle.putString("userId", "1");
        bundle.putString("externalId", "Vishal");
        startChatFragment(bundle);
    }

    private void initializeViews(){
        Toolbar appbarLayout = findViewById(R.id.dashboardAppbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(appbarLayout);
        appBar = getSupportActionBar();
        appBar.setTitle("Vishal");
        appBar.setDisplayHomeAsUpEnabled(true);
        appBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        attachListeners();
    }

    private void attachListeners(){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_user1:
                                appBar.setTitle("Vishal");
                                userConfig(1);
                                break;

                            case R.id.nav_user2:
                                appBar.setTitle("Dk");
                                userConfig(2);
                                break;

                            case R.id.nav_user3:
                                appBar.setTitle("Soham");
                                userConfig(3);
                                break;

                            case R.id.nav_user4:
                                appBar.setTitle("Sourjo");
                                userConfig(4);
                                break;
                        }
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

    }

    private void userConfig(Integer userId){
        Bundle bundle = new Bundle();
        switch (userId){
            case 1:
                bundle.putString("userId", "1");
                bundle.putString("externalId", "vishal");
                startChatFragment(bundle);
                break;
            case 2:
                bundle.putString("userId", "2");
                bundle.putString("externalId", "dk");
                startChatFragment(bundle);
                break;
            case 3:
                bundle.putString("userId", "3");
                bundle.putString("externalId", "soham");
                startChatFragment(bundle);
                break;
            case 4:
                bundle.putString("userId", "4");
                bundle.putString("externalId", "sourjo");
                startChatFragment(bundle);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startChatFragment(Bundle bundle){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(bundle);
        transaction.replace(R.id.chat_frag_container, chatFragment, "chatFrag")
                .commit();
    }
}
