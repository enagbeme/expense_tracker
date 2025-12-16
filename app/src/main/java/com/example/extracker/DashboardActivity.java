package com.example.extracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.example.extracker.Model.BudgetModel;
import com.example.extracker.Model.ExpenseModel;
import com.example.extracker.Model.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;





public class DashboardActivity extends AppCompatActivity {
    CardView all_budget,all_expense;
    TextView logout;
    CardView food_dash,utils_dash, transport_dash,others_dash;
    BottomNavigationView bottomNavigationView;
    TextView display_name,bud_txt, bal_txt,ex_txt;
    AppCompatButton guide;

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference ,ref,databaseReference;


    public static final String SHARED_PREFERS="sharedPrefs";

    private String bd_name;




    int amount,bd_amount,total_ex= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        all_budget = findViewById(R.id.all_budget);
        all_expense = findViewById(R.id.all_expenses);
        food_dash = findViewById(R.id.food_dash);
        utils_dash = findViewById(R.id.utils_dash);
        transport_dash = findViewById(R.id.transport_dash);
        others_dash = findViewById(R.id.others_dash);
        logout = findViewById(R.id.txt_logout);
        display_name = findViewById(R.id.ds_name);
        bud_txt = findViewById(R.id.bud_txt);
        bal_txt = findViewById(R.id.bal_txt);
        ex_txt = findViewById(R.id.ex_txt);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        guide = findViewById(R.id.guide);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);

                display_name.setText("Welcome, " + users.getUsername().toUpperCase());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("Budgets").child(firebaseUser.getUid());
        Query query = ref.orderByKey().limitToLast(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    BudgetModel model =  dataSnapshot.getValue(BudgetModel.class);
                    int budget= model.getAmount();
                    bud_txt.setText("GHS " + Integer.toString(budget));

                    SharedPreferences preferences = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("budgetName", model.getBd_name());
                    editor.putInt("budgetAmount", model.getAmount());
                    editor.apply();



                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
        bd_name = sharedPreferences.getString("budgetName","");
        bd_amount = sharedPreferences.getInt("budgetAmount",0);






        databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseUser.getUid());
        Query query1 = databaseReference.orderByChild("bd_name").equalTo(bd_name);

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int exp_amount=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ExpenseModel model = dataSnapshot.getValue(ExpenseModel.class);
                    exp_amount = exp_amount + model.getAmount();

                    SharedPreferences preferences = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("ExpenseAmount", exp_amount);
                    editor.apply();
                }

                ex_txt.setText("GHS " + Integer.toString(exp_amount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        SharedPreferences sharedPreferences1 = getSharedPreferences(SHARED_PREFERS,MODE_PRIVATE);
        total_ex = sharedPreferences1.getInt("ExpenseAmount",0);


        amount = bd_amount - total_ex;

        bal_txt.setText("GHS " + Integer.toString(amount));








        all_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AllBudgetActivity.class));
            }
        });


        all_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,AllExpensesActivity.class));
            }
        });


        food_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,FoodActivity.class));
            }
        });

        transport_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,TransportActivity.class));
            }
        });

        utils_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,UtilsActivity.class));
            }
        });

        others_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,OthersActivity.class));
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.category:
                        startActivity(new Intent(getApplicationContext(),CategoryActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.budget:
                        startActivity(new Intent(getApplicationContext(), AllBudgetActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.transactions:
                        startActivity(new Intent(getApplicationContext(),TransactionsActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();

            }
        });



    }

    private void Logout() {
        if(logout.getId() == R.id.txt_logout){

            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));

        }
    }
}