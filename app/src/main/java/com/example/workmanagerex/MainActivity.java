package com.example.workmanagerex;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import androidx.work.*;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating a data object
        //to pass the data with workRequest
        //we can put as many variables needed
        Data data = new Data.Builder()
                .putString(MyWorker.TASK_DESC, "The task data passed from MainActivity")
                .build();

//        final OneTimeWorkRequest workRequest =
//                new OneTimeWorkRequest.Builder(MyWorker.class)
//                        .setInputData(data)
//                        .build();
//
//        final OneTimeWorkRequest workRequest3 =
//                new OneTimeWorkRequest.Builder(MyWorker.class)
//                        .setInputData(data)
//                        .build();



        final PeriodicWorkRequest workRequest1 =
                new PeriodicWorkRequest.Builder(MyWorker.class, 15,
                        TimeUnit.MINUTES).setInputData(data).build();




        findViewById(R.id.buttonEnqueue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueue(workRequest1);


//                WorkManager.getInstance()
//                        .beginWith(workRequest1)
//                        .then(workRequest3)
//                        .enqueue();



            }
        });


        final TextView textView = findViewById(R.id.textViewStatus);
        WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest1.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        textView.append(workInfo.getState().name() + "\n");
                    }
                });
    }
}