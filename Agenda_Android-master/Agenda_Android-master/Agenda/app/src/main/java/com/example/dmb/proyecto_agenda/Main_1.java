package com.example.dmb.proyecto_agenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
/**
 * Diego Murillo Barrantes
 */
public class Main_1 extends AppCompatActivity {

    private AdministradorDB manager;
    //Componentes
    private ListView lista;
    private EditText buscar;
    private ImageButton ingresar;
    private Button salir;
    private TextWatcher text = null;
    //lista para llenar la listView
    private List<Notas> notas;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**Instacia de la clase AdministradorDB**/
        manager = new AdministradorDB(this);
        /**Componentes**/
        lista = (ListView) findViewById(R.id.lista_tareas);
        buscar = (EditText) findViewById(R.id.txt_buscar);
        ingresar = (ImageButton) findViewById(R.id.btn_insertar);
        salir = (Button) findViewById(R.id.btn_salir);
        /**base de datos para el listView**/
        manager.open();
        notas = manager.todasNotas();
        refreshDisplay();
        manager.close();

        /**
         * Evento filtro de busqueda de notas
         */
        text = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                manager.open();
                notas = manager.buscarNotas(buscar.getText().toString());
                refreshDisplay();
                manager.close();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        /**ejecuta el textWatcher**/
        buscar.addTextChangedListener(text);

        /**llama el menu con editar y borrar**/
        registerForContextMenu(lista);

        /**
         * finalizar la app
         */
        salir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Gracias por su preferencia", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        /**
         * evento lo lleva a otro activity
         */
        ingresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                intent = new Intent(Main_1.this, Main_2.class);
                startActivity(intent);
                finish();
            }
        });

        /**
         * Vista del titulo
         */
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder informacion = new AlertDialog.Builder(Main_1.this);
                informacion.setMessage("Titulo: " + notas.get((int) id).getTitulo() + "\n" +
                        "Lugar: " + notas.get((int) id).getLugar() + "\n" +
                        "Hora: " + notas.get((int) id).getHora() + "\n" +
                        "Nota: " + notas.get((int) id).getDescripcion())
                        .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("Nota").setIcon(R.drawable.note1).create();
                informacion.show();
            }
        });
    }

    /**
     * llamado de xml para el menu de borrar o editar
     *
     * @param menu
     * @param view
     * @param menuInfo
     */
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    /**
     * selecion de item del menu de borrar y editar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.id_borrar: //Eliminar la nota
                manager.open();
                manager.delete(notas.get(info.position).getId());
                Toast.makeText(getBaseContext(), "La nota se ha eliminado ", Toast.LENGTH_SHORT).show();
                notas = manager.todasNotas();
                buscar.setText("");
                refreshDisplay();
                manager.close();
                return true;
            case R.id.id_editar: //Editar la nota
                intent = new Intent(Main_1.this, Main_2.class);
                intent.putExtra("NOTAS", new String[]{String.valueOf(notas.get(info.position).getId()), notas.get(info.position).getLugar(),
                        notas.get(info.position).getDescripcion(), notas.get(info.position).getHora(), notas.get(info.position).getTitulo()});
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Refresta el listView
     */
    public void refreshDisplay() {
        ArrayAdapter<Notas> adapter = new ArrayAdapter<Notas>(this, android.R.layout.simple_list_item_1, notas);
        lista.setAdapter(adapter);
    }
}


