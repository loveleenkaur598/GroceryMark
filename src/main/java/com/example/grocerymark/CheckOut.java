package com.example.grocerymark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckOut extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        // get the list of stories titles and contents in string array

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_product = database.getReference("Product");

        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();



        navigationView = findViewById(R.id.navi_view_check_out);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Intent signOut = new Intent(CheckOut.this, HomeScreen.class);
                    startActivity(signOut);
                }else if(id == R.id.Cart){
                    Intent cart = new Intent(CheckOut.this, Cart.class);
                    startActivity(cart);
                }else if(id == R.id.user){
                    Intent profile = new Intent(CheckOut.this, Profile.class);
                    startActivity(profile);
                }
                else if(id == R.id.Stores){
                    Intent stores = new Intent(CheckOut.this, MapsActivity.class);
                    startActivity(stores);
                }
                else if(id == R.id.logout){
                    logout();
                }
                return true;
            }
        });

        drawer = findViewById(R.id.drawerCheckOut);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        CardForm cardForm = (CardForm) findViewById(R.id.card_form);
        TextView payment = (TextView)findViewById(R.id.payment_amount);
        Button buttonPay = (Button)findViewById(R.id.btn_pay);

        payment.setText("$15");
        buttonPay.setText("Payer " + payment.getText());
        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {
                //Your code here!! use card.getXXX() for get any card property
                //for instance card.getName();
                Toast.makeText(CheckOut.this,"Name : " + card.getName() + " Last 4:" +
                        card.getLast4(),Toast.LENGTH_SHORT).show();
                Intent order = new Intent(CheckOut.this,Order.class);
                startActivity(order);

            }
        });
    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent signOut = new Intent(CheckOut.this, MainActivity.class);
        startActivity(signOut);

    }
}
