package edu.tum.ase.androidgrpcclient;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: set up data-binding between ListView and List of chat messages + gRPC client stub
    }

    public void sendMessage(View view) {
        // TODO: get content and sender information from input elements + send RPC
    }
}
