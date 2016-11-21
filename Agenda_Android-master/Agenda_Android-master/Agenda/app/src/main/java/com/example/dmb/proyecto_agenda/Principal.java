package com.example.dmb.proyecto_agenda;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
/**
 * Diego Murillo Barrantes
 */
public class Principal extends AppCompatActivity {
    ProgressBar pgBarCircular;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        añadirVistas();
    }

    private void añadirVistas() {
        pgBarCircular = (ProgressBar)findViewById(R.id.pgBarCircular);
        new AsyncTask_load().execute();
        // progressBarCircular.setProgress(0);

    }
    /*Metodo principal que permite la AsyncTask*/
    public class AsyncTask_load extends AsyncTask<Void, Integer, Void> {
        int progreso;
        @Override
        protected void onPreExecute() {
            Toast.makeText(Principal.this, "onPreExecute", Toast.LENGTH_LONG).show();
            progreso = 0;
            pgBarCircular.setVisibility(View.VISIBLE);
        }
        @Override
        protected Void doInBackground(Void... params) {
            while(progreso < 100){
                progreso++;
                publishProgress(progreso);
                SystemClock.sleep(20);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            pgBarCircular.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(Principal.this, "onPostExecute", Toast.LENGTH_LONG).show();
            pgBarCircular.setVisibility(View.INVISIBLE);
            if(progreso>=100) {
                intent = new Intent(Principal.this, Main_1.class);
                startActivity(intent);
            }
        }
    }

}
