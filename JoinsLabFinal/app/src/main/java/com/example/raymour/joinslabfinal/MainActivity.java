package com.example.raymour.joinslabfinal;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button addData;
    private Button sameCompany;
    private Button boston;
    private Button highestSalary;
    private TextView textView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addData = (Button) findViewById(R.id.buttonAddData);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

        boston = (Button) findViewById(R.id.buttonBoston);
        boston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView = (ListView) findViewById(R.id.list_view);
                DatabaseHelper helper = DatabaseHelper.getINTSTANCE(MainActivity.this);

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(view.getContext(), android.R.layout.simple_list_item_1, helper.companyBoston(),
                        new String[]{DatabaseHelper.COL_COMPANY}, new int[] {android.R.id.text1},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

                listView.setAdapter(adapter);
            }
        });

        highestSalary = (Button) findViewById(R.id.buttonSalary);
        highestSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView = (TextView) findViewById(R.id.textView);
                DatabaseHelper helper = DatabaseHelper.getINTSTANCE(MainActivity.this);
                Cursor cursor = helper.higestSalary();
                if (cursor.moveToFirst()) {
                    textView.setText("Company with highest Salary: " + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_COMPANY)));
                }
            }
        });

        sameCompany = (Button) findViewById(R.id.buttonSameCompany);
        sameCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView = (ListView) findViewById(R.id.list_view);
                DatabaseHelper helper = DatabaseHelper.getINTSTANCE(MainActivity.this);
                Cursor cursor = helper.getSameCompany();

                CursorAdapter adapter = new CursorAdapter(MainActivity.this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER) {
                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        TextView name = (TextView) view.findViewById(android.R.id.text1);
                        String firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_FIRST_NAME));
                        String lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LAST_NAME));
                        String fullName = firstName + " " + lastName;
                        name.setText(fullName);

                    }
                };

                listView.setAdapter(adapter);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.newemployeelayout, null);
                builder.setView(dialogView);
                builder.setTitle("Enter Employee Info");
                final Employee newEmployee = new Employee(null,null,null,null,null);
                final EditText ssn = (EditText) dialogView.findViewById(R.id.edit_Ssn);
                final EditText firstName = (EditText) dialogView.findViewById(R.id.edit_firstName);
                final EditText lastName = (EditText) dialogView.findViewById(R.id.edit_lastName);
                final EditText birthYear = (EditText) dialogView.findViewById(R.id.edit_yearOfBirth);
                final EditText city = (EditText) dialogView.findViewById(R.id.enterHomeCity);
                builder.setPositiveButton("Enter Data", null)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ssn.getText().toString().trim().length() == 0) {
                            ssn.setError("Please Enter SSN #");
                        } else if (firstName.getText().toString().trim().length() == 0){
                            firstName.setError("Please Enter First Name");
                        } else if (lastName.getText().toString().trim().length() == 0){
                            lastName.setError("Please Enter Last Name");
                        } else if (birthYear.getText().toString().trim().length() == 0){
                            birthYear.setError("Please Enter Year of Birth");
                        } else if (city.getText().toString().trim().length() == 0){
                            city.setError("Please enter Home City");
                        }
                        else {
                            newEmployee.setSsn(ssn.getText().toString());
                            newEmployee.setFirstName(firstName.getText().toString());
                            newEmployee.setLastName(lastName.getText().toString());
                            newEmployee.setYearOfBirth(birthYear.getText().toString());
                            newEmployee.setHomeCity(city.getText().toString());
                            DatabaseHelper.getINTSTANCE(view.getContext()).insertRowEmployee(newEmployee);
                            Toast.makeText(MainActivity.this, "New Employee has been added to the Database.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }
                });
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addData(){
        DatabaseHelper helper = DatabaseHelper.getINTSTANCE(MainActivity.this);
        helper.emptyTable();

        helper.insertRowEmployee(new Employee("John","Smith","123-04-5678","1973","NY"));
        helper.insertRowEmployee(new Employee("David","McWill","123-04-5679","1982","Seattle"));
        helper.insertRowEmployee(new Employee("Katerina","Wise","123-04-5680","1973","Boston"));
        helper.insertRowEmployee(new Employee("Donald","Lee","123-04-5681","1992","London"));
        helper.insertRowEmployee(new Employee("Gary","Henwood","123-04-5682","1987","Las Vegas"));
        helper.insertRowEmployee(new Employee("Anthony","Bright","123-04-5683","1963","Seattle"));
        helper.insertRowEmployee(new Employee("William","Newey","123-04-5684","1995","Boston"));
        helper.insertRowEmployee(new Employee("Melony","Smith","123-04-5685","1970","Chicago"));

        helper.insertRowJobs(new Job("Fuzz","1","60","123-04-5678"));
        helper.insertRowJobs(new Job("GA","2","70","123-04-5679"));
        helper.insertRowJobs(new Job("Little Place","5","120","123-04-5680"));
        helper.insertRowJobs(new Job("Macy's","3","78","123-04-5681"));
        helper.insertRowJobs(new Job("New Life","1","65","123-04-5682"));
        helper.insertRowJobs(new Job("Believe","6","158","123-04-5683"));
        helper.insertRowJobs(new Job("Macy's","8","200","123-04-5684"));
        helper.insertRowJobs(new Job("Stop","12","299","123-04-5685"));

        Toast.makeText(MainActivity.this, "You've added data to the database!",
                Toast.LENGTH_SHORT).show();


    }
}
