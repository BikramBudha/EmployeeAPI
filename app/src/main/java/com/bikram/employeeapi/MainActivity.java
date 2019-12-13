package com.bikram.employeeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bikram.employeeapi.api.EmployeeApi;
import com.bikram.employeeapi.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tvOutput;

    private static String base_url="http://dummy.restapiexample.com/api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOutput = findViewById(R.id.tvOutput);

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(base_url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        //Interface instance
        EmployeeApi employeeApi = retrofit.create(EmployeeApi.class);
        Call<List<Employee>> listCall = employeeApi.getAllEmployees();

        //Assynchorous call

        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error" + response.code(),Toast.LENGTH_SHORT);
                    Log.d("error msg", response.message());
                }

                List<Employee> employeeList = response.body();
                for (Employee employee : employeeList){
                    String emp = "";
                    emp += "Employee name" + employee.getEmployee_name()+"/n";
                    emp += "Employee salary" + employee.getEmployee_salary()+"/n";
                    emp += "Employee age" + employee.getEmployee_age()+"/n";
                    emp += ".................................";
                    tvOutput.append(emp);
                }

            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.d("error msg", t.getLocalizedMessage());
            }
        });
    }
}
