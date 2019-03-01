package com.laguna.sergio.ecolife;


import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.laguna.sergio.ecolife.Datos.ecolifedb;
import com.laguna.sergio.ecolife.Datos.persona;
import com.laguna.sergio.ecolife.Datos.talonario;
import com.laguna.sergio.ecolife.Datos.venta_credito;
import com.laguna.sergio.ecolife.Datos.cobro;
import com.laguna.sergio.ecolife.Datos.gps;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static android.view.View.VISIBLE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.content.FileProvider.getUriForFile;
import static java.security.AccessController.getContext;

public class NavegacionMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout Inicio,VentaC,ListaT,GesUsuario,Ventas,Perfil,CambiarPass,CambiarTelf,FrameCrearTalonario;
    ////////////////////Para historial talonarios///////////////////////////////
    List<DataAdapterTalo> DataAdapterClassListT;
    RecyclerView recyclerViewT;
    RecyclerView.Adapter recyclerViewadapterT;
    RecyclerView.LayoutManager recyclerViewlayoutManagerT;
    String idtalo;
    ArrayList<talonario> arraT;
    ////////////////////Para Gestionar usuarios///////////////////////////////////
    List<DataAdapterGesU> DataAdapterClassListG;
    RecyclerView recyclerViewG;
    RecyclerView.Adapter recyclerViewadapterG;
    RecyclerView.LayoutManager recyclerViewlayoutManagerG;
    ImageView ImgEditUser;
    /////////////////////////Para ventas al credito/////////////////////////////////////////////////
    RecyclerView recyclerViewVC;
    RecyclerView.Adapter recyclerViewadapterVC;
    RecyclerView.LayoutManager recyclerViewlayoutManagerVC;
    List<DataAdapterVentaCred> DataAdapterClassListVC;
    ImageView vcCamara;
    Button vcSacarFoto, vcConfirmar;
    TextView vcTitulo;
    Spinner SpinnerVcred;
    String imageFileName,fechaVC,fotoVC,talolocal,talonube;
    String latitud,longitud;
    String NOMBRECVC,TELEFONOVC,DIRECCIONVC,ZONAVC,NOMBREPVC;
    EditText nombreCVC,telefonoVC,direccionVC,zonaVC,nombrePVC,productoVC;
    FrameLayout ventaCredList;
    ArrayList<venta_credito> arrayVC;
    venta_credito ventacfinal;
    /////////////////////////Para cobro////////////////////////////////////////////////////////////
    RecyclerView recyclerViewC;
    RecyclerView.Adapter recyclerViewadapterC;
    RecyclerView.LayoutManager recyclerViewlayoutManagerC;
    List<DataAdapterCobro> DataAdapterClassListC;
    FrameLayout cobroList;
    TextView tvcuota,tvmonto,tvsubtotal,tvfecha,tvnombreCobro,tvtelefonoCobro,tvzonaCobro,tvvendedorCobro;
    TextView tvdireccionCobro,tvfechaCobro;
    Button btnNuevoCobro;
    ImageButton btnMostrarMapa,btnMostrarFoto;
    EditText etmontoCobro;

    ListView lvdetallevc;
    Button btnAgregarDetalle;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    String HttpURL = "http://u209922277.hostingerapp.com/servicios_ecolife/insertarimagen.php";// Verificacion de Imei en la nube
    Boolean CheckEditText ;


    ////////////////////////Para ventas al contado//////////////////////////////////////////////////
    Spinner SpinnerVcont;
    TextView Vcontfecha,Vcfecha;
    String vcontprod;
    /////////////////////Para seleccionar la lista de gestion de usuario///////////////////////////
    View ChildViewG ;
    Button RegUser;
    ContentResolver mContentResolver;
    TextView nombre,usuario,ci,cargo,telefono;
    EditText oldpass,newpass,newphone,txtfecha;
    Button Cpass,Ctelf,CambiarC,CambiarT,CrearTalonario,creartalo;
    Calendar c;
    DatePickerDialog dpd;
    //EditText oldpass,newpass,newphone;
    //Button Cpass,Ctelf,CambiarC,CambiarT;

    JSONArray jsonArray = null;
    List<DataAdapterGesU> DataAdapterClassList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    RecyclerView.Adapter recyclerViewAdapterT;
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayRequest ;
    ArrayList<String> SubjectNames;
    RequestQueue requestQueue ;
    String HTTP_SERVER_URL = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaGesU.php";
    View ChildView ;
    int RecyclerViewClickedItemPOS;
    TextView nrotalo,nroventas,fechatalo;
    String HTTP_SERVER_URLT = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaTalo.php";
    FrameLayout nuevavc;
    boolean nuevaventac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        nrotalo=findViewById(R.id.textView6);
        nroventas=findViewById(R.id.textView7);
        fechatalo=findViewById(R.id.textView8);
        VentaC= (FrameLayout) findViewById(R.id.activity_venta_credito);
        Inicio= (FrameLayout) findViewById(R.id.inicio);
        Ventas= (FrameLayout) findViewById(R.id.activity_ventas_contado);
        ListaT= (FrameLayout) findViewById(R.id.activity_talonarios);
        GesUsuario= (FrameLayout) findViewById(R.id.activity_gestionar_user);
        Perfil=(FrameLayout) findViewById(R.id.activity_perfil);
        CambiarPass=(FrameLayout) findViewById(R.id.cambiarpass);
        CambiarTelf=(FrameLayout) findViewById(R.id.cambiar_telf);
        FrameCrearTalonario=findViewById(R.id.crear_talonario);
        ventaCredList=findViewById(R.id.venta_cred_list);
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
        CrearTalonario=findViewById(R.id.btnCrear_Talonario);
        creartalo=findViewById(R.id.btncrear_talo);
        txtfecha=findViewById(R.id.editTextDate);
        nuevavc=findViewById(R.id.activity_venta_credito);
        cargarDatosTalo();
        lvdetallevc=findViewById(R.id.listviewVCont);
        btnAgregarDetalle=findViewById(R.id.btnagregardetalle);

        ///////////////////////////////Para ventas al contado y credito/////////////////////////////////////
        Vcontfecha=findViewById(R.id.vcontfecha);
        Vcfecha=findViewById(R.id.vcfecha);
        SpinnerVcont= findViewById(R.id.spinnervcont);
        vcCamara= findViewById(R.id.vccamara);
        vcConfirmar= findViewById(R.id.vcconfirmar);
        vcTitulo= findViewById(R.id.vctitulo);

        SpinnerVcred=findViewById(R.id.spinnervc);
        nombreCVC= findViewById(R.id.vcnombre);
        telefonoVC= findViewById(R.id.vctelefono);
        direccionVC= findViewById(R.id.vcdireccion);
        zonaVC= findViewById(R.id.vcnrozona);
        nombrePVC= findViewById(R.id.vcvendedor);
        nuevaventac=false;

        //////////////////////////Cobro////////////////////////////////////////////////////////////
        cobroList=findViewById(R.id.cobro_list);
        tvcuota=findViewById(R.id.nrocuotaCobro);
        tvmonto=findViewById(R.id.montoCobro);
        tvsubtotal=findViewById(R.id.subtotalCobro);
        tvfecha=findViewById(R.id.fechaCobro);
        btnMostrarFoto=findViewById(R.id.btnmostrar_foto);
        btnMostrarMapa=findViewById(R.id.btnmostrar_mapa);
        btnNuevoCobro=findViewById(R.id.btnnuevo_cobro);
        tvnombreCobro=findViewById(R.id.NombreCobro);
        tvtelefonoCobro=findViewById(R.id.TelefonoCobro);
        tvdireccionCobro=findViewById(R.id.DireccionCobro);
        tvzonaCobro=findViewById(R.id.ZonaCobro);
        tvvendedorCobro=findViewById(R.id.VendedorCobro);
        tvfechaCobro=findViewById(R.id.FechaCobro);
        etmontoCobro=findViewById(R.id.editMonto);
        /////////////////////////FinCobro//////////////////////////////////////////////////////////
        jsonArray = new JSONArray();
        DataAdapterClassList = new ArrayList<>();
        DataAdapterClassList.clear();
        //JSON_WEB_CALL();
        SubjectNames = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recicladorG);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        if(!estadoverificacion()){
            finish();
            System.exit(0);
        }

        vcConfirmar.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View v){

                                                if (TextUtils.isEmpty(imageFileName)) {
                                                    Toast.makeText(getApplicationContext(), "debe tomar una foto", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    fotoVC = convertirImgString(imagen);
                                                    final VentaCredito vc = new VentaCredito();
                                                    fechaVC = getCurrentTimeStamp();

                                                    Cursor Talo = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, null,
                                                            ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO + "=1", null, null);
                                                    Talo.moveToFirst();
                                                    talolocal = Talo.getString(Talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
                                                    talonube = Talo.getString(Talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID));

                                                            if(nombreCVC.getText().toString().equals("")||telefonoVC.getText().toString().equals("")||direccionVC.getText().toString().equals("")||zonaVC.getText().toString().equals("")||fechaVC.equals("")||nombrePVC.getText().toString().equals("")
                                                                    ||vcontprod.equals("")||talolocal.equals("")||talonube.equals(""))
                                                            {
                                                                Toast.makeText(getApplicationContext(), "Todos los datos son necesarios", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                venta_credito vcred= new venta_credito(nombreCVC.getText().toString(),telefonoVC.getText().toString(),zonaVC.getText().toString(),nombrePVC.getText().toString(),direccionVC.getText().toString(),
                                                                        fechaVC,vcontprod,talolocal,talonube,fotoVC,imageFileName);
                                                                vcred.insert(vcred,mContentResolver);
                                                                Toast.makeText(getApplicationContext(),"Venta creada exitosamente",Toast.LENGTH_SHORT).show();
                                                                denuevaventaacobro();
                                                                }
                                                                Talo.close();
                                                            }

                                            }

        });
////////////////////////////////////////////////////////////////////////////////////////////
        RegUser= findViewById(R.id.button_reg_user);
        //btn_hacerfoto = findViewById(R.id.vccamara);
        vcSacarFoto= findViewById(R.id.vcsacarfoto);
        //img = findViewById(R.id.img);
        if (ContextCompat.checkSelfPermission(NavegacionMenu.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NavegacionMenu.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NavegacionMenu.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

/////////////////////////////////////////////Spinner para productos en ventas al credito y contado/////////////////////////////////////////////////////////////
        String[] producto = {"MEGA JUNIOR","NONI ENERGY","FIBRA PLUS","MEGA FAMILY"};
        SpinnerVcont.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, producto));
        SpinnerVcred.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, producto));
        SpinnerVcont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                //Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                String c= adapterView.getItemAtPosition(position).toString();//adapterView.getItemAtPosition(position);
                if(c.equals("MEGA JUNIOR")){
                    vcontprod="1";
                }else if(c.equals("NONI ENERGY")){
                    vcontprod="2";
                    }else if(c.equals("FIBRA PLUS")){
                        vcontprod="3";
                        }else if(c.equals("MEGA FAMILY")){
                            vcontprod="4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                vcontprod="1";

            }
        });

        SpinnerVcred.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                //Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                String c= adapterView.getItemAtPosition(position).toString();//adapterView.getItemAtPosition(position);
                if(c.equals("MEGA JUNIOR")){
                    vcontprod="1";
                }else if(c.equals("NONI ENERGY")){
                    vcontprod="2";
                }else if(c.equals("FIBRA PLUS")){
                    vcontprod="3";
                }else if(c.equals("MEGA FAMILY")){
                    vcontprod="4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                vcontprod="1";

            }
        });

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ImgEditUser= findViewById(R.id.editarGU);
        //btn_hacerfoto = findViewById(R.id.vccamara);

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
        ////////////
        recyclerViewVC=findViewById(R.id.recicladorVC);
        recyclerViewVC.setHasFixedSize(true);
        recyclerViewlayoutManagerVC = new LinearLayoutManager(this);
        recyclerViewVC.setLayoutManager(recyclerViewlayoutManagerVC);
        /////////////////////
        DataAdapterClassListC = new ArrayList<>();
        DataAdapterClassListC.clear();
        recyclerViewC=findViewById(R.id.recicladorC);
        recyclerViewC.setHasFixedSize(true);
        recyclerViewlayoutManagerC = new LinearLayoutManager(this);
        recyclerViewC.setLayoutManager(recyclerViewlayoutManagerC);




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
                    int x=recyclerView.getChildAdapterPosition(ChildViewG);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (ChildViewG != null && ImgEditUser.onTouchEvent(motionEvent)){
                    //Intent intent = new Intent(NavegacionMenu.this, EditarUser.class);
                    //startActivity(intent);

                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        recyclerViewT.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

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
                    int x=recyclerView.getChildAdapterPosition(ChildViewG);
                    talonario t=arraT.get(x);
                    idtalo= t.Id;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (ChildViewG != null && ImgEditUser.onTouchEvent(motionEvent)){
                    //Intent intent = new Intent(NavegacionMenu.this, EditarUser.class);
                    //startActivity(intent);

                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        recyclerViewVC.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

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
                    int x=recyclerView.getChildAdapterPosition(ChildViewG);
                    ventacfinal=arrayVC.get(x);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (ChildViewG != null && ImgEditUser.onTouchEvent(motionEvent)){
                    //Intent intent = new Intent(NavegacionMenu.this, EditarUser.class);
                    //startActivity(intent);

                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });



        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {

                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
        //    }
        //});

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
                        ck.close();
                    }
                };
                if(verificacionCP()==true) {
                    tr.start();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Rellene todos los datos", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
                                        val.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TELEFONO, phone);
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
                    if(verificacionCT()==true) {
                        tr.start();
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Rellene todos los campos", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    ck.close();
            }
        });


    txtfecha.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            c=Calendar.getInstance();
            int day=c.get(Calendar.DAY_OF_MONTH);
            int month1=c.get(Calendar.MONTH);
            int year1=c.get(Calendar.YEAR);

            dpd=new DatePickerDialog(NavegacionMenu.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtfecha.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                }
            },year1,month1,day);
            dpd.show();
        }
    });

        CrearTalonario.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            deinicioacreartalo();
        }
    });
        creartalo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String be=verificacionCrearTalonario();
                if (be.equals("")) {
                            final String estado = "1";
                            final Conexion con = new Conexion();
                            final String fecha = txtfecha.getText().toString();
                            Cursor t = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN + "=1", null, null);
                            t.moveToFirst();
                            final String idsup = t.getString(t.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._PERSONAID));
                            final String idsupnube = t.getString(t.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID));
                            talonario talo = new talonario(estado, fecha, idsup, idsupnube);
                            talo.insert(talo, mContentResolver);
                            t.close();
                }else{
                    Toast.makeText(getApplicationContext(),be,Toast.LENGTH_SHORT).show();


                }
            }


    });
        btnNuevoCobro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String nuevomonto=etmontoCobro.getText().toString();
                if(nuevomonto.equals("")){
                    Toast.makeText(getApplicationContext(),"Ingrese un monto",Toast.LENGTH_SHORT).show();
                }else {
                    int subtotal=0;
                    int nrocuota=1;
                    String[] args = new String[]{ventacfinal.Id};
                    Cursor c = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO, null,
                            ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITOID + "=?", args, null);
                    if (c.getCount() != 0) {
                        while(c.moveToNext()){
                            String a=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_MONTO));
                            subtotal=subtotal+Integer.parseInt(a);
                            nrocuota++;
                        }
                        if(Integer.parseInt(nuevomonto)<=(140-subtotal)){
                            fechaVC = getCurrentTimeStamp();
                            subtotal=subtotal+Integer.parseInt(nuevomonto);
                            ConseguirGPS();
                            gps g=new gps(latitud,longitud);
                            g.insert(g,mContentResolver);
                            //String[] max=new String[]{"MAX("+ecolifedb.EcoLifeEntry._GPSID+")"};
                            Cursor gs=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,null,
                                    null,null,null);
                            gs.moveToLast();
                            String idgps=gs.getString(gs.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._GPSID));
                            String idgpsnube=gs.getString(gs.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID));
                            cobro co=new cobro(nuevomonto,Integer.toString(nrocuota),Integer.toString(subtotal),fechaVC,ventacfinal.Id,
                                    ventacfinal.NubeId,idgps,idgpsnube);
                            co.insert(co,mContentResolver);
                            Toast.makeText(getApplicationContext(),"Cobro agregado exitosamente",Toast.LENGTH_SHORT).show();
                            gs.close();
                        }else{
                            Toast.makeText(getApplicationContext(),"Monto invalido",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if(Integer.parseInt(nuevomonto)<=140){
                            nrocuota=1;
                            subtotal=Integer.parseInt(nuevomonto);
                            fechaVC = getCurrentTimeStamp();
                            ConseguirGPS();
                            gps g=new gps(latitud,longitud);
                            g.insert(g,mContentResolver);
                            //String[] max=new String[]{"MAX("+ecolifedb.EcoLifeEntry._GPSID+")"};
                            Cursor gs=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,null,
                                    null,null,null);
                            gs.moveToLast();
                            String idgps=gs.getString(gs.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._GPSID));
                            String idgpsnube=gs.getString(gs.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID));
                            cobro co=new cobro(nuevomonto,Integer.toString(nrocuota),Integer.toString(subtotal),fechaVC,ventacfinal.Id,
                                    ventacfinal.NubeId,idgps,idgpsnube);
                            co.insert(co,mContentResolver);
                            Toast.makeText(getApplicationContext(),"Cobro agregado exitosamente",Toast.LENGTH_SHORT).show();
                            gs.close();
                        }else{
                            Toast.makeText(getApplicationContext(),"Monto invalido",Toast.LENGTH_SHORT).show();
                        }
                    }
                    c.close();
                    generarCobrosActuales();
                }

            }

        });
        lvdetallevc=findViewById(R.id.listviewVCont);
        btnAgregarDetalle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String[] val=new String[]{"uno","dos","tres","cuatro","cinco","seis"};
                ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,val);
                lvdetallevc.setAdapter(adapter);
            }
        });
    }
    public boolean verificacionCP(){
        boolean b=false;
        if(oldpass.getText().toString().isEmpty()==false && newpass.getText().toString().isEmpty()==false){
            b=true;
        }
        return b;
    }
    public boolean verificacionCT(){
        boolean b=false;
        if(newphone.getText().toString().isEmpty()==false){
            b=true;
        }
        return b;
    }
    ////////////////////////////////////////PERMISOS PARA CAMARA//////////////////////////
    public String verificacionCrearTalonario() {
        String b = "Rellene todos los campos";
        if (txtfecha.getText().toString().isEmpty() == false) {
            b = "";
            Cursor c = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, null,
                    ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO + "=1", null, null);
            if (c.getCount() != 0) {
                b = "Ya hay un talonario activo";
            }
            c.close();
        }
        return b;
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
        ListaT.setVisibility(View.INVISIBLE);
        GesUsuario.setVisibility(View.INVISIBLE);
        Perfil.setVisibility(View.INVISIBLE);
        CambiarPass.setVisibility(View.INVISIBLE);
        CambiarTelf.setVisibility(View.INVISIBLE);
        FrameCrearTalonario.setVisibility(View.INVISIBLE);
        ventaCredList.setVisibility(View.INVISIBLE);
        cobroList.setVisibility(View.INVISIBLE);
        if(!estadoverificacion()){
            finish();
            System.exit(0);
        }
        if (id == R.id.nav_camera) {
            String Sfecha = getCurrentTimeStamp();
            Vcontfecha.setText(Sfecha);
            cargarDatosTalo();
            Inicio.setVisibility(View.VISIBLE);


        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_slideshow) {
            if(!estadoverificacion()){
                finish();
                System.exit(0);
            }
            generarTalonariosActuales();
            ListaT.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_manage) {
            if(!estadoverificacion()){
                finish();
                System.exit(0);
            }
            Cursor cargo=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null);
            cargo.moveToFirst();
            String c=cargo.getString(cargo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID));
            cargo.close();

            if (c.equals("2")) {
                generarGestionarUser();
                SubjectNames.clear();// = new ArrayList<>();
                DataAdapterClassList.clear();
                recyclerView.setAdapter(recyclerViewadapter);
                JSON_WEB_CALL();
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
                cargo.setText("Cargo: Supervisor");
            }else{
                if(carg.equals("2")) {
                    cargo.setText("Cargo: Administrador");
                }
            }
            telefono.setText(telf);
            perf.close();
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
    public void deinicioacreartalo(){
        txtfecha.setText("");
        Inicio.setVisibility(View.INVISIBLE);
        FrameCrearTalonario.setVisibility(View.VISIBLE);
    }


    public void cargarDatosTalo(){
        Cursor c=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,null,
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO+"=1",null,null);
        c.moveToNext();
        String id=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
        String fecha=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_FECHA_C));
        String[] args=new String[]{id};
        Cursor v=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO,null,
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIOPID+"=?",args,null);
        nrotalo.setText("Nro Talonario: "+id);
        fechatalo.setText("Fecha de Creacion: "+fecha);
        nroventas.setText("Creditos actuales:"+Integer.toString(v.getCount()));
        c.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if(FrameCrearTalonario.getVisibility()==View.VISIBLE) {
                FrameCrearTalonario.setVisibility(View.INVISIBLE);
                Inicio.setVisibility(View.VISIBLE);
            }else{
                if (Perfil.getVisibility() == View.VISIBLE) {
                    Perfil.setVisibility(View.INVISIBLE);
                    Inicio.setVisibility(View.VISIBLE);
                }else{
                    if(GesUsuario.getVisibility()==View.VISIBLE){
                        GesUsuario.setVisibility(View.INVISIBLE);
                        Inicio.setVisibility(View.VISIBLE);
                    }else{
                        if(CambiarPass.getVisibility()==View.VISIBLE){
                            CambiarPass.setVisibility(View.INVISIBLE);
                            Perfil.setVisibility(View.VISIBLE);
                        }else{
                            if(CambiarTelf.getVisibility()==View.VISIBLE){
                                CambiarTelf.setVisibility(View.INVISIBLE);
                                Perfil.setVisibility(View.VISIBLE);
                            }else{
                                if(ListaT.getVisibility()==View.VISIBLE){
                                    ListaT.setVisibility(View.INVISIBLE);
                                    Inicio.setVisibility(View.VISIBLE);
                                }else{
                                    if(ventaCredList.getVisibility()==View.VISIBLE){
                                        ventaCredList.setVisibility(View.INVISIBLE);
                                        ListaT.setVisibility(View.VISIBLE);
                                    }if(cobroList.getVisibility()==View.VISIBLE && nuevaventac==true){
                                        cobroList.setVisibility(View.INVISIBLE);
                                        VentaC.setVisibility(VISIBLE);
                                    }else{
                                        if(VentaC.getVisibility()==View.VISIBLE){
                                            VentaC.setVisibility(View.INVISIBLE);
                                            Inicio.setVisibility(View.VISIBLE);
                                        }else{
                                            if(cobroList.getVisibility()==View.VISIBLE){
                                                cobroList.setVisibility(View.INVISIBLE);
                                                ventaCredList.setVisibility(VISIBLE);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
             /*   VentaC.setVisibility(View.INVISIBLE);
            Inicio.setVisibility(View.INVISIBLE);
            Ventas.setVisibility(View.INVISIBLE);
            ListaT.setVisibility(View.INVISIBLE);
            GesUsuario.setVisibility(View.INVISIBLE);
            Perfil.setVisibility(View.INVISIBLE);
            CambiarPass.setVisibility(View.INVISIBLE);
            CambiarTelf.setVisibility(View.INVISIBLE);
            FrameCrearTalonario.setVisibility(View.INVISIBLE);
            ventaCredList.setVisibility(View.INVISIBLE);
            cobroList.setVisibility(View.INVISIBLE);*/
        }
        return false;
    }
    ////////////////////////////Generamos la lista de talonarios actuales//////////////////
    public void generarTalonariosActuales(){
        SubjectNames.clear();
        DataAdapterClassList.clear();
        Talonarios b = new Talonarios();
        DataAdapterClassListT=(b.generar(mContentResolver));
        arraT=b.talosplease();
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
    public void denuevaventaacobro(){
        Cursor c=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO,null,
                null,null,null);
        c.moveToLast();
        String id=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._VENTA_CREDITOID));
        String nombre=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NOMBRE));
        String tele=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TELEFONO));
        String zon=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ZONA));
        String vend=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_VENDEDOR));
        String dir=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_DIRECCION));
        String fec=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FECHA));
        ventacfinal=new venta_credito();
        ventacfinal.Id=id;
        ventacfinal.Nombre=nombre;
        ventacfinal.Telefono=tele;
        ventacfinal.Zona=zon;
        ventacfinal.Vendedor=vend;
        ventacfinal.Direccion=dir;
        ventacfinal.Fecha=fec;
        SubjectNames.clear();
        DataAdapterClassList.clear();
        //c.close();
        generarCobrosActuales();
        tvnombreCobro.setText("Nombre: "+ventacfinal.Nombre);
        tvtelefonoCobro.setText("Telefono: "+ventacfinal.Telefono);
        tvzonaCobro.setText("Zona: "+ventacfinal.Zona);
        tvvendedorCobro.setText("Vendedor: "+ventacfinal.Vendedor);
        tvdireccionCobro.setText("Direccion: "+ventacfinal.Direccion);
        tvfechaCobro.setText("Fecha de venta: "+ventacfinal.Fecha);
        etmontoCobro.setText("");
        //ventaCredList.setClickable(false);
        Inicio.setVisibility(View.INVISIBLE);
        ventaCredList.setVisibility(View.INVISIBLE);
        nuevavc.setVisibility(View.INVISIBLE);
        cobroList.setVisibility(View.VISIBLE);
        nuevaventac=true;

    }
    public boolean estadoverificacion(){
        boolean b=false;
                Cursor pers = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                        ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null );
                if (pers.getCount()!=0) {
                    b=true;
                }
                pers.close();
                return b;
    }


    public void registrarUsuario(View v){
        Intent intent = new Intent(NavegacionMenu.this, RegUser.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
    public void TalonarioUsuario(View v){
        Intent intent = new Intent(NavegacionMenu.this, RegUser.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void editarUsuario(View v){
        Intent intent = new Intent(NavegacionMenu.this, EditarUser.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"si",Toast.LENGTH_SHORT).show();
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void talonario_ventas(View v){
        Toast.makeText(this.getApplicationContext(),idtalo,Toast.LENGTH_SHORT).show();
        SubjectNames.clear();
        DataAdapterClassList.clear();
        generarVentasCreditoActuales();
        Inicio.setVisibility(View.INVISIBLE);
        ListaT.setVisibility(View.INVISIBLE);
        ventaCredList.setVisibility(View.VISIBLE);
    }
    public void generarVentasCreditoActuales(){
        SubjectNames.clear();
        DataAdapterClassList.clear();
        VentaCredList b=new VentaCredList();
        DataAdapterClassListVC=(b.generar(mContentResolver,idtalo));
        arrayVC=b.VentaCList;
        recyclerViewadapterVC = new RecyclerAdapVentaCred(DataAdapterClassListVC, this);
        recyclerViewVC.setAdapter(recyclerViewadapterVC);
    }
    public void ventas_cobro(View v){
        SubjectNames.clear();
        DataAdapterClassList.clear();
        generarCobrosActuales();
        tvnombreCobro.setText("Nombre: "+ventacfinal.Nombre);
        tvtelefonoCobro.setText("Telefono: "+ventacfinal.Telefono);
        tvzonaCobro.setText("Zona: "+ventacfinal.Zona);
        tvvendedorCobro.setText("Vendedor: "+ventacfinal.Vendedor);
        tvdireccionCobro.setText("Direccion: "+ventacfinal.Direccion);
        tvfechaCobro.setText("Fecha de venta: "+ventacfinal.Fecha);
        etmontoCobro.setText("");
        Inicio.setVisibility(View.INVISIBLE);
        //ventaCredList.setClickable(false);
        ventaCredList.setVisibility(View.INVISIBLE);
        cobroList.setVisibility(View.VISIBLE);
        nuevaventac=false;
    }
    public void generarCobrosActuales(){
        cobro_list b=new cobro_list();
        DataAdapterClassListC=(b.generar(mContentResolver,ventacfinal.Id));
        recyclerViewadapterC= new RecyclerAdapCobro(DataAdapterClassListC,this);
        recyclerViewC.setAdapter(recyclerViewadapterC);
    }

    //////////////////////////////Para sacar fotos////////////////////////////////////////////
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "b_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    public void tomarfoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                vcTitulo.setText(imageFileName);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    Bitmap imagen;
    static final int REQUEST_IMAGE_CAPTURE= 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            int alto= 100;
            int ancho= 150;
            //this.imagen=Bitmap.createScaledBitmap(imageBitmap,ancho,alto,true);
            this.imagen =redimensionarImagen(imageBitmap,600,800);
            vcCamara.setImageBitmap(imagen);
        }
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto= altoNuevo/alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }
    }

    public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date
            //para la fecha parpara

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
        fotoVC="";
        Vcfecha.setText(Sfecha);
        nombreCVC.setText("");
        telefonoVC.setText("");
        direccionVC.setText("");
        zonaVC.setText("");
        nombrePVC.setText("");
        vcCamara.setImageResource(android.R.color.transparent);
        Ventas.setVisibility(View.INVISIBLE);
        Inicio.setVisibility(View.INVISIBLE);
        VentaC.setVisibility(View.VISIBLE);
    }
    /////////////////////////////////////GPS PARA VENTAS AL CREDITO/////////////////////////

    public void ConseguirGPS(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
        }else{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try{
               // gpsVC = locationStringFromLocation(location);
                latitud=Double.toString(location.getLatitude());
                longitud=Double.toString(location.getLongitude());

            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(NavegacionMenu.this, "No encontrado GPS", Toast.LENGTH_SHORT).show();
            }
            //String img= convertirImgString(imagen);
        }
    }

    private String convertirImgString(Bitmap bitmap){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte= array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;
    }

    public static String locationStringFromLocation(final Location location) {
        return Location.convert(location.getLatitude(), Location.FORMAT_DEGREES) + " " + Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
    }

    public boolean CheckEditTextIsEmptyOrNot(String nombreC,String telefono,String direccion,String zona,String fecha,String nombreP,String producto,String gps,String talo,String talonube){

        if(TextUtils.isEmpty(nombreC)&&TextUtils.isEmpty(telefono)&&TextUtils.isEmpty(direccion)&&TextUtils.isEmpty(zona)&&TextUtils.isEmpty(fecha)&&TextUtils.isEmpty(nombreP)
                &&TextUtils.isEmpty(producto)&&TextUtils.isEmpty(gps)&&TextUtils.isEmpty(talo)&&TextUtils.isEmpty(talonube))
        {
            return false;
        }
        else {

            return true;
        }
    }

    /////////////////////////////////////////////////Cargar las listas de gestionar usuarios//////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void JSON_WEB_CALL(){

        SubjectNames.clear();// = new ArrayList<>();
        DataAdapterClassList.clear();
        recyclerView.setAdapter(recyclerViewadapter);

        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URL,

                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterGesU GetDataAdapter2 = new DataAdapterGesU();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setNombre(json.getString("nombre"));

                GetDataAdapter2.setCargo(json.getString("id_rol"));

                //Adding subject name here to show on click event.
                //SubjectNames.add(json.getString("gym"));

                GetDataAdapter2.setEstado(json.getString("estado"));


            }
            catch (JSONException e)
            {

                e.printStackTrace();

            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

        //progressBar.setVisibility(View.GONE);
        //List<SolicitudRecycle> items = new ArrayList<>();


        recyclerViewadapter = new RecyclerAdapGesU(DataAdapterClassList, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }

    /////////////////////////////////////////////////Cargar las listas de talonario//////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void JSON_WEB_CALL_TALO(){

        SubjectNames.clear();// = new ArrayList<>();
        DataAdapterClassList.clear();
        recyclerView.setAdapter(recyclerViewadapter);

        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URLT,

                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL_TALO(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterGesU GetDataAdapter2 = new DataAdapterGesU();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setNombre(json.getString("id"));

                GetDataAdapter2.setCargo(json.getString("estado"));

                //Adding subject name here to show on click event.
                //SubjectNames.add(json.getString("gym"));

                GetDataAdapter2.setEstado(json.getString("fecha_c"));

                GetDataAdapter2.setEstado(json.getString("id_supervisor"));
            }
            catch (JSONException e)
            {

                e.printStackTrace();

            }

            DataAdapterClassList.add(GetDataAdapter2);

        }

        //progressBar.setVisibility(View.GONE);
        //List<SolicitudRecycle> items = new ArrayList<>();


        recyclerViewadapter = new RecyclerAdapGesU(DataAdapterClassList, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }

}
