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
import android.view.ViewDebug;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.VISIBLE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.content.FileProvider.getUriForFile;
import static java.security.AccessController.getContext;

public class NavegacionMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    FrameLayout Inicio,VentaC,Historial,GesUsuario,Ventas,Perfil,CambiarPass,CambiarTelf,FrameCrearTalonario,HTVentasCredito,HTVCinfo,GesUsuarioTalo;
    ////////////////////Para historial talonarios///////////////////////////////
    List<DataAdapterTalo> DataAdapterClassListT;
    RecyclerView recyclerViewT;
    RecyclerView.Adapter recyclerViewadapterT;
    RecyclerView.LayoutManager recyclerViewlayoutManagerT;

    JSONArray jsonArray = null;
    String FinalJSonObject = "";
    List<DataAdapterHT> DataAdapterClassListHT;
    RecyclerView recyclerViewHT;
    RecyclerView.Adapter recyclerViewadapterHT;
    ArrayList<String> SubjectNamesHTid,SubjectNamesHTfech,SubjectNamesHTestado;
    View ChildViewHT;
    ////////////////////Para historial talonarios venta credito///////////////////////////////
    Button bottomHTVC;
    List<DataAdapterHTVC> DataAdapterClassListHTVC;
    RecyclerView.LayoutManager recyclerViewlayoutManagerHTVC;
    RecyclerView recyclerViewHTVC;
    RecyclerView.Adapter recyclerViewadapterHTVC;
    JSONArray jsonArrayHTCV = null;
    String FinalJSonObjectHTVC = "";
    TextView tcardhtvcid,tcardhtvcfech;
    ArrayList<String> SubjectNamesHTVCid, SubjectNamesHTVCnombre,SubjectNamesHTVCtelf,SubjectNamesHTVCdir,SubjectNamesHTVCzona
            ,SubjectNamesHTVCfech,SubjectNamesHTVCvendedor,SubjectNamesHTVCfoto;

    //////////////////////////////Para informacion de la venta al credito en historial talonario////////////////////////////////////////////
    TextView nombreHTVCinfo,telefonoHTVCinfo,zonaHTVCinfo,vendedorHTVCinfo,direccionHTVCinfo,fechaHTVCinfo;
    RecyclerView recyclerViewHTVCinfo;
    RecyclerView.Adapter recyclerViewadapterHTVCinfo;
    List<DataAdapterHTVCinfo> DataAdapterClassListHTVCinfo;
    JSONArray jsonArrayHTCVinfo = null;
    ArrayList<String> SubjectNamesHTVCGPS;
    ////////////////////Para Gestionar usuarios///////////////////////////////////
    List<DataAdapterGesU> DataAdapterClassListG;
    RecyclerView recyclerViewG;
    RecyclerView.Adapter recyclerViewadapterG;
    RecyclerView.LayoutManager recyclerViewlayoutManagerG;
    ImageView ImgEditUser;
    View ChildViewHTVC ;
    View ChildViewR ;//para enviar los datos de recyclerview tocado
    int RecyclerViewClickedItemPOSR ;

    DataAdapterGesU dataAdapterGesU;
    persona personaGU;
    ArrayList<String> SubjectGUid, SubjectGUnombre, SubjectGUcargo, SubjectGUpass, SubjectGUestado;

    /////////////////////////Para ventas al credito/////////////////////////////////////////////////
    ImageView vcCamara;
    Button vcSacarFoto, vcConfirmar;
    TextView vcTitulo;
    Spinner SpinnerVcred;
    String imageFileName,gpsVC,fechaVC,fotoVC,talolocal,talonube;
    String NOMBRECVC,TELEFONOVC,DIRECCIONVC,ZONAVC,NOMBREPVC;
    EditText nombreCVC,telefonoVC,direccionVC,zonaVC,nombrePVC,productoVC;

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
    View ChildViewG;
    Button RegUser;
    ContentResolver mContentResolver;
    TextView nombre,usuario,ci,cargo,telefono;
    EditText oldpass,newpass,newphone,txtfecha;
    Button Cpass,Ctelf,CambiarC,CambiarT,CrearTalonario,creartalo;
    Calendar c;
    DatePickerDialog dpd;
    //EditText oldpass,newpass,newphone;
    //Button Cpass,Ctelf,CambiarC,CambiarT;

///////////////////////////////////Para cargar lista de usuarios/////////////////////////////////////////
    List<DataAdapterGesU> DataAdapterClassList;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;
    ProgressBar progressBar;
    JsonArrayRequest jsonArrayRequest ;
    ArrayList<String> SubjectNames;
    RequestQueue requestQueue ;
    String HTTP_SERVER_URL = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaGesU.php";
    View ChildView ;
    int RecyclerViewClickedItemPOS;

    ///////////////////////////////////Gestionar usuario talonario//////////////////////////////////////////
    CheckBox CheckPasivo, CheckExpirado;
    JSONArray jsonArrayGUtalo = null;
    //String FinalJSonObject = "";
    List<DataAdapterGesUTalo> DataAdapterClassListGUTalo;
    RecyclerView recyclerViewGUTalo;
    RecyclerView.Adapter recyclerViewadapterGUTalo;
    ArrayList<String> SubjectNamesGUTid,SubjectNamesGUTfech,SubjectNamesGUTestado;

    String HTTP_SERVER_URLHT = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaHTalo.php";
    String HTTP_SERVER_URLHTVC = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaHTVentaCred.php";
    String HTTP_SERVER_URLHTVCinfo = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaHTVCinfo.php";
    //////////////////////////////////ARRAY LIST//////////////////////////////////////////////////
    ArrayList<String> SubjectIdHT;
    String subjIdHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        VentaC= (FrameLayout) findViewById(R.id.activity_venta_credito);
        Inicio= (FrameLayout) findViewById(R.id.inicio);
        Ventas= (FrameLayout) findViewById(R.id.activity_ventas_contado);
        Historial= (FrameLayout) findViewById(R.id.activity_historial_talo);
        HTVentasCredito= (FrameLayout) findViewById(R.id.activity_ht_ventcred);
        HTVCinfo= (FrameLayout) findViewById(R.id.activity_htvc_info);
        GesUsuario= (FrameLayout) findViewById(R.id.activity_gestionar_user);
        GesUsuarioTalo= findViewById(R.id.activity_gestuser_talo);
        Perfil=(FrameLayout) findViewById(R.id.activity_perfil);
        CambiarPass=(FrameLayout) findViewById(R.id.cambiarpass);
        CambiarTelf=(FrameLayout) findViewById(R.id.cambiar_telf);
        FrameCrearTalonario=findViewById(R.id.crear_talonario);
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
        tcardhtvcid=findViewById(R.id.textidHTvc);
        tcardhtvcfech=findViewById(R.id.textfechHTvc);
        bottomHTVC=findViewById(R.id.bottomhtvc);
        /////////Textviews para info historial talonario venta credito///////////////////////////
        nombreHTVCinfo=findViewById(R.id.NombreHTVCinfo);
        telefonoHTVCinfo=findViewById(R.id.TelefonoHTVCinfo);
        zonaHTVCinfo=findViewById(R.id.ZonaHTVCinfo);
        vendedorHTVCinfo=findViewById(R.id.VendedorHTVCinfo);
        direccionHTVCinfo=findViewById(R.id.DireccionHTVCinfo);
        fechaHTVCinfo=findViewById(R.id.FechaHTVCinfo);


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

        jsonArray = new JSONArray();
        DataAdapterClassList = new ArrayList<>();
        DataAdapterClassList.clear();
        //JSON_WEB_CALL();
        SubjectNames = new ArrayList<>();
        SubjectIdHT = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recicladorG);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);

//////////////////////////////////Para Gestionar Usuario Talonario/////////////////////////////////////////
        CheckPasivo = (CheckBox) findViewById(R.id.checkBoxPasivo);
        CheckPasivo.setOnClickListener(this);
        CheckExpirado = (CheckBox) findViewById(R.id.checkBoxExpirado);
        CheckExpirado.setOnClickListener(this);
/////////////////////////////////para historial talonarios/////////////////////////////////
        jsonArray = new JSONArray();
        DataAdapterClassListHT = new ArrayList<>();
        DataAdapterClassListHT.clear();

        recyclerViewHT = (RecyclerView) findViewById(R.id.recicladorHT);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerViewHT.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerViewHT.setLayoutManager(recyclerViewlayoutManager);

        SubjectNamesHTid= new ArrayList<>();
        SubjectNamesHTfech= new ArrayList<>();
        SubjectNamesHTestado= new ArrayList<>();


        ////////////////////////////para ventas credito historial talonario///////////////////////////////
        SubjectNamesHTVCid = new ArrayList<>();
        SubjectNamesHTVCnombre = new ArrayList<>();
        SubjectNamesHTVCtelf = new ArrayList<>();
        SubjectNamesHTVCdir = new ArrayList<>();
        SubjectNamesHTVCzona = new ArrayList<>();
        SubjectNamesHTVCfech = new ArrayList<>();
        SubjectNamesHTVCvendedor = new ArrayList<>();
        SubjectNamesHTVCfoto = new ArrayList<>();

        jsonArray = new JSONArray();
        DataAdapterClassListHTVC = new ArrayList<>();
        DataAdapterClassListHTVC.clear();

        recyclerViewHTVC = (RecyclerView) findViewById(R.id.recicladorHTvc);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerViewHTVC.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerViewHTVC.setLayoutManager(recyclerViewlayoutManager);

        ////////////////////////////////Para info de ventas al credito historail talonario//////////////////////////////////////////
        SubjectNamesHTVCGPS = new ArrayList<>();
        jsonArray = new JSONArray();
        DataAdapterClassListHTVCinfo = new ArrayList<>();
        DataAdapterClassListHTVCinfo.clear();

        recyclerViewHTVCinfo = (RecyclerView) findViewById(R.id.recicladorHTVCinfo);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerViewHTVCinfo.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerViewHTVCinfo.setLayoutManager(recyclerViewlayoutManager);

        /////////////////////////////////Para talonarios de Gestionar Usuario//////////////////////////////////////////
        SubjectNamesGUTid = new ArrayList<>();
        jsonArrayGUtalo = new JSONArray();
        DataAdapterClassListGUTalo = new ArrayList<>();
        DataAdapterClassListGUTalo.clear();

        recyclerViewGUTalo = (RecyclerView) findViewById(R.id.recicladorGUtalo);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerViewGUTalo.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerViewGUTalo.setLayoutManager(recyclerViewlayoutManager);


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
                                                    ConseguirGPS();

                                                    Cursor Talo = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, null,
                                                            ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO + "=1", null, null);
                                                    Talo.moveToFirst();
                                                    talolocal = Talo.getString(Talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
                                                    talonube = Talo.getString(Talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ONLINE));

                                                    //Toast.makeText(getApplicationContext(), nombreCVC.getText().toString(), Toast.LENGTH_SHORT).show();

                                                    //Thread tr = new Thread() {
                                                    //    @Override
                                                    //    public void run() {
                                                            //if (vc.CheckEditTextIsEmptyOrNot(nombreCVC.getText().toString(), telefonoVC.getText().toString(), direccionVC.getText().toString(), zonaVC.getText().toString(), fechaVC, nombrePVC.getText().toString(), vcontprod, gpsVC, talolocal, talonube))
                                                            //if(TextUtils.isEmpty(nombreCVC.getText().toString())&&TextUtils.isEmpty(telefonoVC.getText().toString())&&TextUtils.isEmpty(direccionVC.getText().toString())&&TextUtils.isEmpty(zonaVC.getText().toString())&&TextUtils.isEmpty(fechaVC)&&TextUtils.isEmpty(nombrePVC.getText().toString())
                                                            //&&TextUtils.isEmpty(vcontprod)&&TextUtils.isEmpty(gpsVC)&&TextUtils.isEmpty(talolocal)&&TextUtils.isEmpty(talonube))
                                                            if(nombreCVC.getText().toString().equals("")||telefonoVC.getText().toString().equals("")||direccionVC.getText().toString().equals("")||zonaVC.getText().toString().equals("")||fechaVC.equals("")||nombrePVC.getText().toString().equals("")
                                                                    ||vcontprod.equals("")||gpsVC.equals("")||talolocal.equals("")||talonube.equals(""))
                                                            {
                                                                Toast.makeText(getApplicationContext(), "Todos los datos son necesarios", Toast.LENGTH_SHORT).show();
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        //Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "Todos los datos correctos", Toast.LENGTH_SHORT).show();

                                                                final venta_credito vcred= new venta_credito(nombreCVC.getText().toString(),telefonoVC.getText().toString(),zonaVC.getText().toString(),nombrePVC.getText().toString(),direccionVC.getText().toString(),
                                                                        fechaVC,vcontprod,talolocal,talonube,fotoVC,imageFileName);
                                                                //vcred.insert(vcred,);

                                                                }
                                                            }
                                                       // }
                                                    //};
                                                    //tr.start();

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
                vcontprod= adapterView.getItemAtPosition(position).toString();//adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // vacio

            }
        });

        SpinnerVcred.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ImgEditUser= findViewById(R.id.editarGU);
        //btn_hacerfoto = findViewById(R.id.vccamara);
        DataAdapterClassListHTVC = new ArrayList<>();
        DataAdapterClassListHTVC.clear();
        DataAdapterClassListHT = new ArrayList<>();
        DataAdapterClassListHT.clear();
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

        SubjectGUid = new ArrayList<>();
        SubjectGUnombre = new ArrayList<>();
        SubjectGUcargo = new ArrayList<>();
        SubjectGUpass = new ArrayList<>();
        SubjectGUestado = new ArrayList<>();

        ////////////////////////////////para gestionar usuarios talonario/////////////////////////////
        SubjectNamesGUTid = new ArrayList<>();
        SubjectNamesGUTfech = new ArrayList<>();
        SubjectNamesGUTestado = new ArrayList<>();
        personaGU = new persona();

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
                RecyclerViewClickedItemPOSR=0;

                if(ChildViewG != null && gestureDetectorG.onTouchEvent(motionEvent)) {

                    RecyclerViewClickedItemPOSR = Recyclerview.getChildAdapterPosition(ChildViewG);
                    Toast.makeText(getApplicationContext(),Integer.toString(RecyclerViewClickedItemPOSR),Toast.LENGTH_SHORT).show();
                    personaGU.IdUsuario=SubjectGUid.get(RecyclerViewClickedItemPOSR);
                    personaGU.Nombre=SubjectGUnombre.get(RecyclerViewClickedItemPOSR);
                    personaGU.Password=SubjectGUpass.get(RecyclerViewClickedItemPOSR);
                    personaGU.Estado=SubjectGUestado.get(RecyclerViewClickedItemPOSR);
                    personaGU.Rol=SubjectGUcargo.get(RecyclerViewClickedItemPOSR);
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

/////////////////////////////////////////////////////Cargar lista de ventas credito de historial talonario////////////////////////////////////////////////
        recyclerViewHT.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

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

                ChildViewHT = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                RecyclerViewClickedItemPOSR=0;

                if(ChildViewHT != null && gestureDetectorG.onTouchEvent(motionEvent)) {
                    Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_SHORT).show();
                    RecyclerViewClickedItemPOSR = Recyclerview.getChildAdapterPosition(ChildViewHT);
                    subjIdHT= SubjectNamesHTid.get(RecyclerViewClickedItemPOSR);
                    //SubjectNames.clear();// = new ArrayList<>();
                    DataAdapterClassListHTVC.clear();
                    recyclerViewHTVC.setAdapter(recyclerViewadapterHTVC);
                    String i="1";
                    EnvioHTVentaCredito(i);////////cambiar esto subjIdHT
                    tcardhtvcid.setText("Talonario: "+SubjectNamesHTid.get(RecyclerViewClickedItemPOSR));
                    tcardhtvcfech.setText("Fecha: "+SubjectNamesHTfech.get(RecyclerViewClickedItemPOSR));
                    Historial.setVisibility(View.INVISIBLE);
                    HTVentasCredito.setVisibility(View.VISIBLE);
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
//////////////////////////////////////////Para cargar los cobros en el historial ventas al credito////////////////////////////////////////
        recyclerViewHTVC.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

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

                ChildViewHTVC = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                RecyclerViewClickedItemPOSR=0;

                if(ChildViewHTVC != null && gestureDetectorG.onTouchEvent(motionEvent)) {
                    RecyclerViewClickedItemPOSR = Recyclerview.getChildAdapterPosition(ChildViewHTVC);
                    Toast.makeText(getApplicationContext(),Integer.toString(RecyclerViewClickedItemPOSR),Toast.LENGTH_SHORT).show();
                    //DataAdapterClassListHTVC.clear();
                    //recyclerViewHTVC.setAdapter(recyclerViewadapterHTVC);
                    String i="1";
                    //EnvioHTVentaCredito(i);////////cambiar esto subjIdHT
                    nombreHTVCinfo.setText("Nombre: "+SubjectNamesHTVCnombre.get(RecyclerViewClickedItemPOSR));
                    telefonoHTVCinfo.setText("Telefono: "+SubjectNamesHTVCtelf.get(RecyclerViewClickedItemPOSR));
                    zonaHTVCinfo.setText("Zona: "+SubjectNamesHTVCzona.get(RecyclerViewClickedItemPOSR));
                    vendedorHTVCinfo.setText("Vendedor: "+SubjectNamesHTVCvendedor.get(RecyclerViewClickedItemPOSR));
                    direccionHTVCinfo.setText("Dir: "+SubjectNamesHTVCdir.get(RecyclerViewClickedItemPOSR));
                    fechaHTVCinfo.setText("Fecha: "+SubjectNamesHTVCfech.get(RecyclerViewClickedItemPOSR));
                    EnvioHTVentCredCobro("1");
                    HTVentasCredito.setVisibility(View.INVISIBLE);
                    HTVCinfo.setVisibility(View.VISIBLE);
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
                if (verificacionCrearTalonario() == true) {
                    Thread tr = new Thread() {
                        @Override
                        public void run() {
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

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() { //int r=con.objJson(res);
                                    //if (r>0){
                                    //Intent i= new Intent(Login.this,NavegacionMenu.class);
                                    //startActivity(i);
                                    Cursor prueba = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, null,
                                            null, null, null);
                                    String m = "";
                                    String n = "";
                                    while (prueba.moveToNext()) {
                                        m = m + prueba.getString(prueba.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID)) + ",";
                                        n = n + prueba.getString(prueba.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID)) + ",";
                                    }

                                    Toast.makeText(getApplicationContext(), n + " " + m, Toast.LENGTH_LONG).show();
                                    //}else{
                                    //   Toast.makeText(getApplicationContext(),"Usuario o password incorrectos", Toast.LENGTH_SHORT).show();
                                    //   }

                                }
                            });
                        }
                    };
                    tr.start();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
                }

        );
    }

    //////////////////////////////////Para llenar la lista de los talonarios de Gestionar Usuario/////////////////////////////
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.checkBoxPasivo:
                if (CheckPasivo.isChecked())
                    Toast.makeText(getApplicationContext(), "Tiqueado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Des tiqueado", Toast.LENGTH_LONG).show();
                break;
        }
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
    public boolean verificacionCrearTalonario(){
        boolean b=false;
        if(txtfecha.getText().toString().isEmpty()==false){
            b=true;
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
        Historial.setVisibility(View.INVISIBLE);
        GesUsuario.setVisibility(View.INVISIBLE);
        Perfil.setVisibility(View.INVISIBLE);
        CambiarPass.setVisibility(View.INVISIBLE);
        CambiarTelf.setVisibility(View.INVISIBLE);
        FrameCrearTalonario.setVisibility(View.INVISIBLE);

        if (id == R.id.nav_camera) {
            String Sfecha = getCurrentTimeStamp();
            Vcontfecha.setText(Sfecha);
            Inicio.setVisibility(View.VISIBLE);


        } else if (id == R.id.nav_gallery) {
            String Idraid="22";///cambiar
            String estado="0";
            SubjectNames.clear();// = new ArrayList<>();
            DataAdapterClassListHT.clear();
            recyclerViewHT.setAdapter(recyclerViewadapterHT);
            EnvioHistorialTalo(Idraid,estado);
            Historial.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_slideshow) {
            generarHistorialTalo();
            SubjectNames.clear();// = new ArrayList<>();
            DataAdapterClassList.clear();
            recyclerView.setAdapter(recyclerViewadapter);
            JSON_WEB_CALL();
            Historial.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_manage) {
            Cursor cargo=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null);
            cargo.moveToFirst();
            String c=cargo.getString(cargo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID));


            if (c.equals("2")) {

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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    public void deinicioacreartalo(){
        txtfecha.setText("");
        Inicio.setVisibility(View.INVISIBLE);
        FrameCrearTalonario.setVisibility(View.VISIBLE);
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

    public void registrarUsuario(View v){



    }
    public void talonarioUsuario(View v){
        GesUsuario.setVisibility(View.INVISIBLE);
        GesUsuarioTalo.setVisibility(View.VISIBLE);
    }

    public void editarUsuario(View v){

        Intent intent = new Intent(NavegacionMenu.this, EditarUser.class);
        intent.putExtra("Persona", personaGU);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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
        Ventas.setVisibility(View.INVISIBLE);
        Inicio.setVisibility(View.INVISIBLE);
        VentaC.setVisibility(View.VISIBLE);
    }

    public void hTVCcatras (View v){
        HTVentasCredito.setVisibility(View.INVISIBLE);
        Historial.setVisibility(View.VISIBLE);
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
                gpsVC = locationStringFromLocation(location);
                //vcTitulo.setText(LogLat);
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
        SubjectGUid.clear();
        SubjectGUnombre.clear();
        SubjectGUcargo.clear();
        SubjectGUpass.clear();
        SubjectGUestado.clear();
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

                GetDataAdapter2.setIdusuario(json.getString("id"));
                SubjectGUid.add(json.getString("id"));
                GetDataAdapter2.setNombre(json.getString("nombre"));
                SubjectGUnombre.add(json.getString("nombre"));
                GetDataAdapter2.setPassword(json.getString("password"));
                SubjectGUpass.add(json.getString("password"));
                GetDataAdapter2.setCargo(json.getString("id_rol"));
                SubjectGUcargo.add(json.getString("id_rol"));
                GetDataAdapter2.setEstado(json.getString("estado"));
                SubjectGUestado.add(json.getString("estado"));


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

        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URLHT,

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

    public void EnvioHistorialTalo(final String Idpersona, final String estado){
        SubjectNamesHTid.clear();
        SubjectNamesHTfech.clear();
        SubjectNamesHTestado.clear();



        DataAdapterClassListHT.clear();
        recyclerViewHT.setAdapter(recyclerViewadapterHT);

        class HistorialTaloFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                //progressDialog.dismiss();
                if (httpResponseMsg.toString().equals("Registration Successfully")) {

                    //Toast.makeText(Menu_principal.this, "Listado", Toast.LENGTH_LONG).show();
                    //JSON_WEB_CALL();

                }
                //Toast.makeText(Menu_principal.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                FinalJSonObject = httpResponseMsg ;

                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        //Toast.makeText(NavegacionMenu.this, FinalJSonObject, Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(FinalJSonObject);
                        JSON_PARSE_DATA_AFTER_WEBCALLHT(jsonArray);
                        //JSONObject jsonObject;

                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //JSON_PARSE_DATA_AFTER_WEBCALLRLU(jsonArray);
                        //ReCargarLista(jsonArray);
                        e.printStackTrace();
                    }
                }else{
                    //DataAdapterClassList.clear();
                    //SubjectNamesRLU.clear();// = new ArrayList<>();
                    //DataAdapterClassListRLU.clear();

                    //JSON_PARSE_DATA_AFTER_WEBCALLRLU(jsonArray);
                    //JSONObject jsonObject;
                    //DataAdapterClassListRLU.clear();
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        //ReCargarLista(jsonArray);

                        //JSON_PARSE_DATA_AFTER_WEBCALLRLU(jsonArray);
                        //JSONObject jsonObject;

                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //JSON_PARSE_DATA_AFTER_WEBCALLRLU(jsonArray);
                        e.printStackTrace();
                    }
                }
                //JSON_PARSE_DATA_AFTER_WEBCALLRLU(FinalJSonObject);
                //JSONArray jsonArray = null;
                //jsonArray = new JSONArray(FinalJSonObject);
                //JSONObject jsonObject;
                //JSON_PARSE_DATA_AFTER_WEBCALLRLU(jsonArray);
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("idpersona",params[0]);

                hashMap.put("estado",params[1]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLHT);

                return finalResult;
            }
        }
        HistorialTaloFunctionClass RegisterFunctionClass = new HistorialTaloFunctionClass();
        RegisterFunctionClass.execute(Idpersona,estado);
    }

    /*public void ReCargarLista(JSONArray array){

        for(int i = 0; i<=array.length(); i++) {
            DataAdapterHT GetDataAdapter3 = new DataAdapterHT();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                //GetDataAdapter3.setRaidlvl(json.getString("idraid"));
                //SubjectNamesR.add(json.getString("idraid"));

                GetDataAdapter3.setNroTalo(json.getString("id"));

                GetDataAdapter3.setFechaTalo(json.getString("fecha_c"));

                GetDataAdapter3.setEstado(json.getString("estado"));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            DataAdapterClassListHT.add(GetDataAdapter3);
        }
        DataAdapterClassList.clear();
        //SubjectNamesRLU.clear();// = new ArrayList<>();

        //progressBarR.setVisibility(View.GONE);

        recyclerViewadapterHT = new RecyclerAdapHT(DataAdapterClassListHT, this);
        recyclerViewHT.setAdapter(recyclerViewadapterHT);
    }*/

    public void JSON_PARSE_DATA_AFTER_WEBCALLHT(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterHT GetDataAdapter3 = new DataAdapterHT();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                //GetDataAdapter3.setRaidlvl(json.getString("idraid"));
                //SubjectNamesR.add(json.getString("idraid"));

                GetDataAdapter3.setNroTalo(json.getString("id"));
                SubjectNamesHTid.add(json.getString("id"));
                GetDataAdapter3.setFechaTalo(json.getString("fecha_c"));
                SubjectNamesHTfech.add(json.getString("fecha_c"));
                GetDataAdapter3.setEstado(json.getString("estado"));
                SubjectNamesHTestado.add(json.getString("estado"));

            }
            catch (JSONException e)
            {

                e.printStackTrace();
            }

            DataAdapterClassListHT.add(GetDataAdapter3);

        }
        //progressBarR.setVisibility(View.GONE);
        recyclerViewadapterHT = new RecyclerAdapHT(DataAdapterClassListHT, this);
        recyclerViewHT.setAdapter(recyclerViewadapterHT);
    }

    //////////////////////////////////////////////////Consultar ventas credito de historial talo/////////////////////////////////////////////////////////////
    public void EnvioHTVentaCredito(final String Idtalo){

        SubjectNamesHTVCid.clear();SubjectNamesHTVCnombre.clear();
        SubjectNamesHTVCtelf.clear();SubjectNamesHTVCdir.clear();
        SubjectNamesHTVCzona.clear();SubjectNamesHTVCfech.clear();
        SubjectNamesHTVCvendedor.clear();SubjectNamesHTVCfoto.clear();

        DataAdapterClassListHTVC.clear();
        recyclerViewHTVC.setAdapter(recyclerViewadapterHTVC);

        class HTVentaCreditoFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }
            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                //progressDialog.dismiss();
                if (httpResponseMsg.toString().equals("Registration Successfully")) {
                }
                //Toast.makeText(Menu_principal.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                FinalJSonObject = httpResponseMsg ;

                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        //Toast.makeText(NavegacionMenu.this, FinalJSonObject, Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(FinalJSonObject);
                        JSON_PARSE_DATA_AFTER_WEBCALLHTVC(jsonArray);


                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{

                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("idtalo",params[0]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLHTVC);

                return finalResult;
            }
        }
        HTVentaCreditoFunctionClass RegisterFunctionClass = new HTVentaCreditoFunctionClass();
        RegisterFunctionClass.execute(Idtalo);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALLHTVC(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterHTVC GetDataAdapter3 = new DataAdapterHTVC();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                SubjectNamesHTVCid.add(json.getString("id"));
                GetDataAdapter3.setNombre(json.getString("nombre"));
                SubjectNamesHTVCnombre.add(json.getString("nombre"));
                SubjectNamesHTVCtelf.add(json.getString("telefono"));
                GetDataAdapter3.setDireccion(json.getString("direccion"));
                SubjectNamesHTVCdir.add(json.getString("direccion"));
                SubjectNamesHTVCzona.add(json.getString("zona"));
                GetDataAdapter3.setFecha(json.getString("fecha"));
                SubjectNamesHTVCfech.add(json.getString("fecha"));
                SubjectNamesHTVCvendedor.add(json.getString("vendedor"));
                SubjectNamesHTVCfoto.add(json.getString("foto"));
            }
            catch (JSONException e)
            {

                e.printStackTrace();
            }

            DataAdapterClassListHTVC.add(GetDataAdapter3);

        }
        //progressBarR.setVisibility(View.GONE);
        recyclerViewadapterHTVC = new RecyclerAdapHTVC(DataAdapterClassListHTVC, this);
        recyclerViewHTVC.setAdapter(recyclerViewadapterHTVC);
    }

    public void EnvioHTVentCredCobro(final String Idventa_credito){

        DataAdapterClassListHTVCinfo.clear();
        recyclerViewHTVCinfo.setAdapter(recyclerViewadapterHTVCinfo);

        class HTVentCredCobroFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }
            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                //progressDialog.dismiss();
                if (httpResponseMsg.toString().equals("Registration Successfully")) {
                }
                //Toast.makeText(Menu_principal.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                FinalJSonObject = httpResponseMsg ;

                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        Toast.makeText(NavegacionMenu.this, FinalJSonObject, Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(FinalJSonObject);
                        JSON_PARSE_DATA_AFTER_WEBCALLHTVCcobro(jsonArray);


                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{

                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("idventcred",params[0]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLHTVCinfo);

                return finalResult;
            }
        }
        HTVentCredCobroFunctionClass RegisterFunctionClass = new HTVentCredCobroFunctionClass();
        RegisterFunctionClass.execute(Idventa_credito);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALLHTVCcobro(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterHTVCinfo GetDataAdapter3 = new DataAdapterHTVCinfo();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter3.setFecha(json.getString("fecha"));
                GetDataAdapter3.setCuota(json.getString("nro_cuota"));
                GetDataAdapter3.setMonto(json.getString("monto"));
                GetDataAdapter3.setSubtotal(json.getString("subtotal"));
                GetDataAdapter3.setGps(json.getString("id_gps"));
                SubjectNamesHTVCGPS.add(json.getString("id_gps"));
            }
            catch (JSONException e)
            {

                e.printStackTrace();
            }

            DataAdapterClassListHTVCinfo.add(GetDataAdapter3);

        }
        //progressBarR.setVisibility(View.GONE);
        recyclerViewadapterHTVCinfo = new RecyclerAdapHTVCinfo(DataAdapterClassListHTVCinfo, this);
        recyclerViewHTVCinfo.setAdapter(recyclerViewadapterHTVCinfo);
    }

    /////////////////////////////////////Para talonario en Gestionar Usuarios////////////////////////////////////////
    public void EnvioGUTalo(final String Idpersona, final String estado){
        SubjectNamesHTid.clear();
        SubjectNamesGUTid.clear();
        SubjectNamesGUTfech.clear();
        SubjectNamesGUTestado.clear();
        DataAdapterClassListGUTalo.clear();
        recyclerViewGUTalo.setAdapter(recyclerViewadapterGUTalo);

        class GUTaloFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                //progressDialog.dismiss();

                Toast.makeText(NavegacionMenu.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
                FinalJSonObject = httpResponseMsg ;

                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        //Toast.makeText(NavegacionMenu.this, FinalJSonObject, Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(FinalJSonObject);
                        JSON_PARSE_DATA_AFTER_WEBCALLGUtalo(jsonArray);
                        //JSONObject jsonObject;

                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //JSON_PARSE_DATA_AFTER_WEBCALLRLU(jsonArray);
                        //ReCargarLista(jsonArray);
                        e.printStackTrace();
                    }
                }
            }
            @Override
            protected String doInBackground(String... params) {

                hashMap.put("idpersona",params[0]);

                hashMap.put("estado",params[1]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLHT);

                return finalResult;
            }
        }
        GUTaloFunctionClass RegisterFunctionClass = new GUTaloFunctionClass();
        RegisterFunctionClass.execute(Idpersona,estado);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALLGUtalo(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterHT GetDataAdapter3 = new DataAdapterHT();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                //GetDataAdapter3.setRaidlvl(json.getString("idraid"));
                //SubjectNamesR.add(json.getString("idraid"));

                GetDataAdapter3.setNroTalo(json.getString("id"));
                SubjectNamesHTid.add(json.getString("id"));
                GetDataAdapter3.setFechaTalo(json.getString("fecha_c"));
                SubjectNamesHTfech.add(json.getString("fecha_c"));
                GetDataAdapter3.setEstado(json.getString("estado"));
                SubjectNamesHTestado.add(json.getString("estado"));

            }
            catch (JSONException e)
            {

                e.printStackTrace();
            }

            DataAdapterClassListHT.add(GetDataAdapter3);

        }
        //progressBarR.setVisibility(View.GONE);
        recyclerViewadapterHT = new RecyclerAdapHT(DataAdapterClassListHT, this);
        recyclerViewHT.setAdapter(recyclerViewadapterHT);
    }

}
