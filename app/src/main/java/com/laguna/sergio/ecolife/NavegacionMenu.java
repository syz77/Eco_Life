package com.laguna.sergio.ecolife;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.laguna.sergio.ecolife.Datos.ecolifedb;
import static android.view.View.VISIBLE;

public class NavegacionMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout Inicio,VentaC,Historial,GesUsuario,Ventas,Perfil,CambiarPass,CambiarTelf;
    ////////////////////Para historial talonarios///////////////////////////////
    List<DataAdapterTalo> DataAdapterClassListT;
    RecyclerView recyclerViewT;
    RecyclerView.Adapter recyclerViewadapterT;
    RecyclerView.LayoutManager recyclerViewlayoutManagerT;
    ////////////////////Para Gestionar usuarios///////////////////////////////////
    List<DataAdapterGesU> DataAdapterClassListG;
    RecyclerView recyclerViewG;
    RecyclerView.Adapter recyclerViewadapterG;
    RecyclerView.LayoutManager recyclerViewlayoutManagerG;
    ImageView ImgEditUser;

    /////////////////////////Para ventas al credito/////////////////////////////////////////////////
    ImageView btn_hacerfoto,img;
    ////////////////////////Para ventas al contado//////////////////////////////////////////////////
    Spinner SpinnerVcont;
    TextView Vcontfecha,Vcfecha;
    String vcontprod;
    /////////////////////Para seleccionar la lista de gestion de usuario///////////////////////////
    View ChildViewG ;
    Button RegUser;
    ContentResolver mContentResolver;
    TextView nombre,usuario,ci,cargo,telefono;
    EditText oldpass,newpass,newphone;
    Button Cpass,Ctelf,CambiarC,CambiarT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        VentaC= (FrameLayout) findViewById(R.id.activity_venta_credito);
        Inicio= (FrameLayout) findViewById(R.id.inicio);
        Ventas= (FrameLayout) findViewById(R.id.activity_ventas_contado);
        Historial= (FrameLayout) findViewById(R.id.activity_talonarios);
        GesUsuario= (FrameLayout) findViewById(R.id.activity_gestionar_user);
        Perfil=(FrameLayout) findViewById(R.id.activity_perfil);
        CambiarPass=(FrameLayout) findViewById(R.id.cambiarpass);
        CambiarTelf=(FrameLayout) findViewById(R.id.cambiar_telf);
        setSupportActionBar(toolbar);
        mContentResolver=this.getContentResolver();
        nombre=(TextView) findViewById(R.id.textView10);
        usuario=(TextView)findViewById(R.id.textView11);
        ci=(TextView)findViewById(R.id.textView12);
        cargo=(TextView)findViewById(R.id.textView13);
        telefono=(TextView)findViewById(R.id.textView14);
        Cpass=(Button)findViewById(R.id.button3);
        Ctelf=findViewById(R.id.button4);
        oldpass=findViewById(R.id.EditPass);
        newpass=findViewById(R.id.EditNewPass);
        newphone=findViewById(R.id.EditPhone);
        CambiarC=findViewById(R.id.button5);
        CambiarT=findViewById(R.id.button6);

        ///////////////////////////////Para ventas al contado y credito/////////////////////////////////////
        Vcontfecha=findViewById(R.id.vcontfecha);
        Vcfecha=findViewById(R.id.vcfecha);
        SpinnerVcont= findViewById(R.id.spinnervcont);

        String[] pokemon = {"MEGA JUNIOR","NONI ENERGY","FIBRA PLUS","MEGA FAMILY"};
        SpinnerVcont.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pokemon));
        SpinnerVcont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                //Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                vcontprod= adapterView.getItemAtPosition(position).toString();//adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////

        RegUser= findViewById(R.id.button_reg_user);
        ImgEditUser= findViewById(R.id.editarGU);
        btn_hacerfoto = findViewById(R.id.vccamara);

        DataAdapterClassListT = new ArrayList<>();
        DataAdapterClassListT.clear();
        recyclerViewT = (RecyclerView) findViewById(R.id.recicladorT);
        recyclerViewT.setHasFixedSize(true);
        recyclerViewlayoutManagerT = new LinearLayoutManager(this);
        recyclerViewT.setLayoutManager(recyclerViewlayoutManagerT);

        DataAdapterClassListG = new ArrayList<>();
        DataAdapterClassListG.clear();
        recyclerViewG = (RecyclerView) findViewById(R.id.recicladorG);
        recyclerViewG.setHasFixedSize(true);
        recyclerViewlayoutManagerG = new LinearLayoutManager(this);
        recyclerViewG.setLayoutManager(recyclerViewlayoutManagerG);


        btn_hacerfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent para llamar a la Camara
                Intent cameraIntent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //Creamos una carpeta en la memeria del terminal
                File imagesFolder = new File(
                        Environment.getDataDirectory(),"AndroidFacil");
                imagesFolder.mkdirs();
                //añadimos el nombre de la imagen
                File image = new File(imagesFolder, "foto.jpg");
                Uri uriSavedImage = Uri.fromFile(image);
                //Le decimos al Intent que queremos grabar la imagen
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                //Lanzamos la aplicacion de la camara con retorno (forResult)
                startActivityForResult(cameraIntent, 1);
            }});

        recyclerViewG.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetectorG = new GestureDetector(NavegacionMenu.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }
                //@Override public boolean onLongPress(MotionEvent e) {

                //    return true;

                //}

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());


                if(ChildViewG != null && gestureDetectorG.onTouchEvent(motionEvent)) {
                    Intent intent = new Intent(NavegacionMenu.this, RegUser.class);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (ChildViewG != null && ImgEditUser.onTouchEvent(motionEvent)){
                    Intent intent = new Intent(NavegacionMenu.this, EditarUser.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CambiarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr=new Thread() {
                    @Override
                    public void run() {
                        Cursor ck = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN + "=1", null, null);
                        ck.moveToNext();
                        final String User = ck.getString(ck.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO));
                        final String PassActualLocal = ck.getString(ck.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD));
                        final String PassActualUser = oldpass.getText().toString();
                        final String PassNewUser = newpass.getText().toString();
                        //final String res=con.login(User,PassActualLocal);
                        if (PassActualLocal.equals(PassActualUser)) {
                            final Conexion con = new Conexion();
                            final String res = con.CambiarPass(User, PassNewUser);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    int i = con.objJson(res);
                                    if (i > 0) {
                                        ContentValues val = new ContentValues();
                                        val.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD, PassNewUser);
                                        mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, val,
                                                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN + "=1", null);
                                        Toast.makeText(getApplicationContext(), "La contraseña se modifico exitosamente", Toast.LENGTH_SHORT).show();
                                        depassaperfil();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error de conexion a internet", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "La contraseña no coincide", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                };
                tr.start();
            }
        });
        CambiarT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor ck=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                        ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null);
                ck.moveToNext();
                final String User=ck.getString(ck.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO));
                final String phone=newphone.getText().toString();
                    Thread tr=new Thread(){
                        @Override
                        public void run() {
                            final Conexion con2=new Conexion();
                            final String r = con2.CambiarTelf(User, phone);
                            final int i = con2.objJson(r);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (i>0) {
                                        ContentValues val = new ContentValues();
                                        val.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD, phone);
                                        mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, val,
                                                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN + "=1", null);
                                        Toast.makeText(getApplicationContext(), "El telefono se modifico exitosamente", Toast.LENGTH_SHORT).show();
                                        detelefonoaperfil();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error de conexion a internet", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }};
                    tr.start();
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacion_menu, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        VentaC.setVisibility(View.INVISIBLE);
        Inicio.setVisibility(View.INVISIBLE);
        Ventas.setVisibility(View.INVISIBLE);
        Historial.setVisibility(View.INVISIBLE);
        GesUsuario.setVisibility(View.INVISIBLE);
        Perfil.setVisibility(View.INVISIBLE);
        CambiarPass.setVisibility(View.INVISIBLE);
        CambiarTelf.setVisibility(View.INVISIBLE);
       // CambiarPass.setVisibility(View.INVISIBLE);
       // CambiarTelf.setVisibility(View.INVISIBLE);
        if (id == R.id.nav_camera) {
            String Sfecha = getCurrentTimeStamp();
            Vcontfecha.setText(Sfecha);
            Inicio.setVisibility(View.VISIBLE);


        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_slideshow) {
            generarHistorialTalo();
            Historial.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_manage) {
            Cursor cargo=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null);
            cargo.moveToFirst();
            String c=cargo.getString(cargo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID));

            if (c.equals("2")) {
                generarGestionarUser();
                GesUsuario.setVisibility(View.VISIBLE);
            }else{
                if (c.equals("1")){
                    cargarperfil();
                    Perfil.setVisibility(View.VISIBLE);
                }
            }
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void cargarperfil(){
        nombre.setText("Nombre:");
        usuario.setText("Usuario:");
        ci.setText("CI:");
        cargo.setText("Cargo:");
        telefono.setText("Telefono:");
        Cursor perf=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null);
            perf.moveToNext();
            String nom, usu, carnet, carg, telf;
            nom = nombre.getText()+" "+ perf.getString(perf.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NOMBRE));
            usu = usuario.getText()+" "+ perf.getString(perf.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO));
            carnet = ci.getText()+" "+ perf.getString(perf.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CI));
            carg = perf.getString(perf.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID));
            telf = telefono.getText()+" "+ perf.getString(perf.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TELEFONO));
            nombre.setText(nom);
            usuario.setText(usu);
            ci.setText(carnet);
            if(carg.equals("1")) {
                cargo.setText("Cargo: Administrador");
            }else{
                if(carg.equals("2")) {
                    cargo.setText("Cargo: Supervisor");
                }
            }
            telefono.setText(telf);
    }

    public void pass(View v){
        Perfil.setVisibility(View.INVISIBLE);
        CambiarPass.setVisibility(View.VISIBLE);
    }
    public void telff(View v){
        Perfil.setVisibility(View.INVISIBLE);
        CambiarTelf.setVisibility(View.VISIBLE);
    }
    public void depassaperfil(){
        oldpass.setText("");
        newpass.setText("");
        CambiarPass.setVisibility(View.INVISIBLE);
        Perfil.setVisibility(View.VISIBLE);
    }
    public void detelefonoaperfil(){
        newphone.setText("");
        CambiarTelf.setVisibility(View.INVISIBLE);
        Perfil.setVisibility(View.VISIBLE);
    }

    public void pantallas(){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

        }
        return false;
    }

    ////////////////////////////Generamos la lista del historial de los talonarios//////////////////
    public void generarHistorialTalo(){

        Talonarios b = new Talonarios();
        DataAdapterClassListT=(b.generar());

        recyclerViewadapterT = new RecyclerAdapTalo(DataAdapterClassListT, this);
        recyclerViewT.setAdapter(recyclerViewadapterT);
    }
    ///////////////////////////Generamos la lista de los usuarios///////////////////////////////////
    public void generarGestionarUser(){

        GestionarUser b = new GestionarUser();
        DataAdapterClassListG=(b.generar());

        recyclerViewadapterG = new RecyclerAdapGesU(DataAdapterClassListG, this);
        recyclerViewG.setAdapter(recyclerViewadapterG);
    }

    public void registrarUsuario(View v){
        Intent intent = new Intent(NavegacionMenu.this, RegUser.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void editarUsuario(View v){
        Intent intent = new Intent(NavegacionMenu.this, EditarUser.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    //////////////////////////////Para sacar fotos////////////////////////////////////////////
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprovamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            Bitmap bMap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory()+
                    "/AndroidFacil/"+"foto.jpg");
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            img.setImageBitmap(bMap);
        }
    }

    public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void ventCont(View v){
        String Sfecha = getCurrentTimeStamp();
        Vcontfecha.setText(Sfecha);
        VentaC.setVisibility(View.INVISIBLE);
        Inicio.setVisibility(View.INVISIBLE);
        Ventas.setVisibility(View.VISIBLE);
    }

    public void ventCred(View v){
        String Sfecha = getCurrentTimeStamp();
        Vcfecha.setText(Sfecha);
        Ventas.setVisibility(View.INVISIBLE);
        Inicio.setVisibility(View.INVISIBLE);
        VentaC.setVisibility(View.VISIBLE);
    }

    public void cadena(){

    }

}
