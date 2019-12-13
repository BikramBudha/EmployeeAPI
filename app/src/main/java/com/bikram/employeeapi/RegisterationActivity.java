package com.bikram.employeeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bikram.employeeapi.api.EmployeeApi;
import com.bikram.employeeapi.model.Employee;
import com.bikram.employeeapi.model.EmployeeCUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterationActivity extends AppCompatActivity {

    private final static String BASE_URL = "http://dummy.restapiexample.com/api/v1/";
    EditText etName, etSalary, etEmpAge;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        etName = findViewById(R.id.etName);
        etSalary = findViewById(R.id.etSalary);
        etEmpAge = findViewById(R.id.etAge);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });


    }

    private void Register(){
        String name = etName.getText().toString();
        Float salary = Float.parseFloat(etSalary.getText().toString());
        int age = Integer.parseInt(etEmpAge.getText().toString());

        EmployeeCUD employee = new EmployeeCUD(name, salary, age);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);
        Call<Void> voidCall = employeeApi.registerEmployee(employee);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(RegisterationActivity.this, "You have been sucessfuly registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterationActivity.this, "Error : "+ t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
