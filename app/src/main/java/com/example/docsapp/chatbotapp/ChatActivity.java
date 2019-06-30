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
        startChatFragment();
    }

    private void initializeViews(){
        Toolbar appbarLayout = findViewById(R.id.dashboardAppbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(appbarLayout);
        appBar = getSupportActionBar();
        appBar.setTitle("ChatBot");
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
                                Toast.makeText(getApplicationContext(), "User1 clicked", Toast.LENGTH_LONG).show();
                                break;

                            case R.id.nav_user2:
                                Toast.makeText(getApplicationContext(), "User2 clicked", Toast.LENGTH_LONG).show();
                                break;

                            case R.id.nav_user3:
                                Toast.makeText(getApplicationContext(), "User3 clicked", Toast.LENGTH_LONG).show();
                                break;

                            case R.id.nav_user4:
                                Toast.makeText(getApplicationContext(), "User4 clicked", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startChatFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChatFragment chatFragment = new ChatFragment();
        transaction.replace(R.id.chat_frag_container, chatFragment, "chatFrag")
                .commit();
    }
}
