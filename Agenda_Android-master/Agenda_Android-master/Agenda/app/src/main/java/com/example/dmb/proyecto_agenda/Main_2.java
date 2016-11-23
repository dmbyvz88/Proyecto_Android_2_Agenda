package com.example.dmb.proyecto_agenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
/**
 * Diego Murillo Barrantes
 */
public class Main_2 extends AppCompatActivity {
    /**
     * Base de datos
     **/
    private AdministradorDB manager;
    /**
     * Componentes
     **/
    private EditText titulo, nota, fecha, lugar;
    private Button ingresar, cancelar;
    /**
     * Objeto para las notas
     **/
    private Notas notas;
    private String[] myNota;
    private Intent intent;
    /**
     * Variable para fecha
     **/
    private String hora;
    private String cal;
    /**
     * llamado del Calendar
     **/
    private Calendar calendario;

    @RequiresApi(api = Build.VERSION_CODES.N)//requerido para el calendario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_form);
        /**Instacia de la clase AdministradorDB**/
        manager = new AdministradorDB(this);
        /**Componentes**/
        titulo = (EditText) findViewById(R.id.txt_titulo);
        nota = (EditText) findViewById(R.id.txt_nota);
        fecha = (EditText) findViewById(R.id.txt_hora);
        lugar = (EditText) findViewById(R.id.txt_lugar);
        ingresar = (Button) findViewById(R.id.btn_ingresar);
        cancelar = (Button) findViewById(R.id.btn_cancelar);

        /**Inicializar Calendar**/
        calendario = Calendar.getInstance();
        /**Obtiene datos del intent**/
        intent = getIntent();
        myNota = intent.getStringArrayExtra("NOTAS");
        if (myNota != null) {
            llenar();
        }

        /**
         * evento de touch al edit
         */
        fecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    new DatePickerDialog(Main_2.this, calendarListener, calendario.get(Calendar.YEAR),
                            calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
                    return true;
                }
                return false;
            }
        });

        /**
         * Ingresar Nota
         */
        ingresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /**Validacion de campos**/
                if (titulo.length() > 0 && nota.length() > 0 && fecha.length() > 0 && lugar.length() > 0) {
                    notas = new Notas();
                    notas.setTitulo(titulo.getText().toString());
                    notas.setLugar(lugar.getText().toString());
                    notas.setHora(fecha.getText().toString());
                    notas.setDescripcion(nota.getText().toString());
                    manager.open();
                    /**Ingresa a la base de datos**/
                    if (myNota == null) {//crear datos
                        manager.create(notas);
                        Toast.makeText(getBaseContext(), "Se Guardo la nota con exito", Toast.LENGTH_SHORT).show();
                    } else {//actualizar datos
                        manager.update(notas, Integer.parseInt(myNota[0]));
                        Toast.makeText(getBaseContext(), "Se ha actualizado los datos", Toast.LENGTH_SHORT).show();
                    }
                    manager.close();
                    activity();
                } else {//Error por no ingresar todos los datos
                    Toast.makeText(getBaseContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        /**
         * Se regresa a la lista de las notas
         */
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity();
            }
        });
    }

    /**
     * Metodo que regresa a la vista anterior
     */
    private void activity() {
        Intent intent = new Intent(Main_2.this, Main_1.class);
        startActivity(intent);
        finish();
    }

    /**
     * llena los datos en editext
     */
    public void llenar() {
        lugar.setText(myNota[1].toString());
        nota.setText(myNota[2].toString());
        fecha.setText(myNota[3].toString());
        titulo.setText(myNota[4].toString());
    }

    /**
     * obtiene los datos del date y abre el dialogo del time
     */
    DatePickerDialog.OnDateSetListener calendarListener = new DatePickerDialog.OnDateSetListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            cal = dayOfMonth + "/" + month + "/" + year;
            new TimePickerDialog(Main_2.this, timeListener, calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE), true).show();

        }
    };

    /**
     * obtiene los datos del time, pero si cancela no ingresa datos al edit
     */
    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hora = hourOfDay + ":" + minute;
            if (hora != null) {
                fecha.setText(cal + " " + hora);
            }
        }
    };
}
