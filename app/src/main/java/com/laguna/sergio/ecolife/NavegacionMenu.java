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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewDebug;
import android.view.WindowManager;
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
import android.widget.CompoundButton;
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
import android.widget.ToggleButton;

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

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1.model.AnnotateTextRequest;
import com.google.api.services.language.v1.model.AnnotateTextResponse;
import com.google.api.services.language.v1.model.Document;
import com.google.api.services.language.v1.model.Entity;
import com.google.api.services.language.v1.model.Features;
import com.google.api.services.language.v1.model.Sentiment;
import com.laguna.sergio.ecolife.Datos.ecolifedb;
import com.laguna.sergio.ecolife.Datos.persona;
import com.laguna.sergio.ecolife.Datos.talonario;
import com.laguna.sergio.ecolife.Datos.venta_credito;
import com.laguna.sergio.ecolife.Datos.cobro;
import com.laguna.sergio.ecolife.Datos.gps;
import com.laguna.sergio.ecolife.Datos.venta_contado;
import com.laguna.sergio.ecolife.Datos.detalle_contado;
import com.laguna.sergio.ecolife.Datos.Sync.EcoLifeSyncAdapter;
import com.squareup.picasso.Picasso;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

import static android.view.View.VISIBLE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.content.FileProvider.getUriForFile;
import static java.security.AccessController.getContext;

public class NavegacionMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        RecognitionListener {



    FrameLayout Inicio,VentaC,Historial,GesUsuario,Ventas,Perfil,CambiarPass,CambiarTelf,FrameCrearTalonario,HTVentasCredito,HTVCinfo,GesUsuarioTalo,ListaT,
    GesUserTaloCambiar,GesUserTaloEstado,GesUserTaloVentCred,Foto;

    String palabraSpeechT;
    ////////////////////Para historial talonarios///////////////////////////////
    List<DataAdapterTalo> DataAdapterClassListT;
    RecyclerView recyclerViewT;
    RecyclerView.Adapter recyclerViewadapterT;
    RecyclerView.LayoutManager recyclerViewlayoutManagerT;

    String idtalolocal;
    String idtalonube;
    ArrayList<talonario> arraT;

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
            ,SubjectNamesHTVCfech,SubjectNamesHTVCfechmapa,SubjectNamesHTVCvendedor,SubjectNamesHTVCfoto,SubjectNamesHTVClat,SubjectNamesHTVClong,SubjectNamesHTVCcuotas;

    ArrayList<String> SubjectNamesTVCfecha,SubjectNamesTVCcuota,SubjectNamesTVClat,SubjectNamesTVClong,SubjectNamesTVCfoto;
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
    ImageView ImgEditUser,ImageestadoGU;

    View ChildViewHTVC ;
    View ChildViewR ;//para enviar los datos de recyclerview tocado
    int RecyclerViewClickedItemPOSR ;

    DataAdapterGesU dataAdapterGesU;
    persona personaGU;
    ArrayList<String> SubjectGUid, SubjectGUnombre, SubjectGUcargo, SubjectGUpass, SubjectGUestado
            ,SubjectGUcorreo,SubjectGUtelefono,SubjectGUfecha,SubjectGUci;


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


    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    String HttpURL = "http://u209922277.hostingerapp.com/servicios_ecolife/insertarimagen.php";// Verificacion de Imei en la nube
    Boolean CheckEditText ;


    ////////////////////////Para ventas al contado//////////////////////////////////////////////////
    ListView lvdetallevc;
    Spinner SpinnerVcont;
    TextView Vcontfecha,Vcfecha;
    Button btnAgregarDetalle,btnAgregarVContado;
    EditText etclientecontado,etdireccioncontado,etzonacontado,etpromcontado,etcantcontado,etsubcontado,ettelefcontado;
    String vcontprod,fechacontado,vcontprodcont;
    ArrayList<String> detalle;
    int subtotal,contador;
    ArrayAdapter<String>adapterDetalle;
    /////////////////////Para seleccionar la lista de gestion de usuario///////////////////////////
    View ChildViewG;
    Button RegUser;
    ContentResolver mContentResolver;
    TextView nombre,usuario,ci,cargo,telefono,textfechacrearTalo;
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

    ///////////////////////////////////Gestionar usuario talonario//////////////////////////////////////////
    Button btnCambiarAPasivo;
    AlertDialog.Builder dialogo1,dialogo2,dialogo3,dialogoinfo,dialogouser,dialogoGPS;
    CheckBox CheckPasivo, CheckExpirado;
    JSONArray jsonArrayGUtalo = null;
    //String FinalJSonObject = "";
    List<DataAdapterGesUTalo> DataAdapterClassListGUTalo;
    RecyclerView recyclerViewGUTalo;
    RecyclerView.Adapter recyclerViewadapterGUTalo;
    ArrayList<String> SubjectNamesGUTid,SubjectNamesGUTfech,SubjectNamesGUTestado,SubjectNamesGUTcredito
        ,SubjectNamesGUTsaldo;

    ////////////////////////////////Para Gestionar Usuario cambiar talonario//////////////////////////////////////////
    TextView TextGUTCidtalo,TextGUTCsupervisor,TextGUTCfecha;
    String GUTCiduser="",GUTCnombreuser="",GUTCidtalo="",GUTCestadotalo="";
    RecyclerView recyclerViewGUTC;
    RecyclerView.Adapter recyclerViewadapterGUTC;
    List<DataAdapterGesU> DataAdapterClassListGUTC;
    JSONArray jsonArrayGUTC = null;
    ArrayList<String> SubjectGUTCIdUser;

    ///////////////////////////////Para Gestionar Usuario talonario estado////////////////////////////////////////////
    TextView TextGUTEidtalo,TextGUTEfecha,TextGUTEestado;
    String GUTEidtalo="";

    ///////////////////////////////Para Gestionar Usuario ventas al credito lista////////////////////////////////////////////
    TextView TextGUTVCidtalo,TextGUTVCfecha,TextGUTVCestado;
    ArrayList<String> SubjectGUTvcid,SubjectGUTvcnombre,SubjectGUTvctelf,SubjectGUTvczona,SubjectGUTvcvendedor,
            SubjectGUTvcdir,SubjectGUTvcfecha,SubjectGUTvcfoto;
    RecyclerView recyclerViewGUTvc;
    RecyclerView.Adapter recyclerViewadapterGUTvc;
    List<DataAdapterGesUTaloVC> DataAdapterClassListGUTvc;
    Integer bandera;
    //JSONArray jsonArrayGUTC = null;

    //////////////////////////////////Para traer las fotos desde el servidor///////////////////////////////////////////////
    ImageView Image_foto;
    Integer banderafoto;

    String HTTP_SERVER_URLHT = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaHTalo.php";
    String HTTP_SERVER_URLHTVC = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaHTVentaCred.php";
    String HTTP_SERVER_URLHTVCinfo = "http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaHTVCinfo.php";
    String HTTP_SERVER_URLGUtalo ="http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaGesUtalo.php";
    String HTTP_SERVER_URLGUTC ="http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaGesUCambiar.php";
    String HTTP_SERVER_URLGUTCcambio ="http://u209922277.hostingerapp.com/servicios_ecolife/InsertarGUTcambiar.php";
    String HTTP_SERVER_URLGUTE ="http://u209922277.hostingerapp.com/servicios_ecolife/InsertarGUTestado.php";
    String HTTP_SERVER_URLGUTvc ="http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaGUTventcred.php";
    String HTTP_SERVER_URLTVA ="http://u209922277.hostingerapp.com/servicios_ecolife/CargarListaTVAinfo.php";
    //////////////////////////////////ARRAY LIST//////////////////////////////////////////////////
    ArrayList<String> SubjectIdHT;
    String subjIdHT;
    /////////////////////////////////////Para speech to text
    public static final String API_KEY = "AIzaSyDkQaOJcMgjXUuEBuQ8iqdacOVUjzzV6Ms";



    EntityListAdapter entityListAdapter;
    private List<Entity> entityList;

    private CloudNaturalLanguage naturalLanguageService;
    private Document document;
    public TextView nombrez;
    private Features features;

    ///////////////////////////////Para speech to text///////////////////////////////////////////////
    private static final int REQUEST_RECORD_PERMISSION = 100;
    private TextView returnedText;
    private ToggleButton toggleButton;
    private ProgressBar progressBartext;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion_menu);
        toggleButton=findViewById(R.id.toggleButton1);

        //progressBartext.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    //progressBartext.setVisibility(View.VISIBLE);
                    //progressBartext.setIndeterminate(true);
                    ActivityCompat.requestPermissions
                            (NavegacionMenu.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO},
                                    REQUEST_RECORD_PERMISSION);
                } else {
                    //progressBartext.setIndeterminate(false);
                    //progressBartext.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                }
            }
        });

        ///////////////////////////////////////para speech to text////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////
        //nombrez = findViewById(R.id.nombre);
        naturalLanguageService = new CloudNaturalLanguage.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                null
        ).setCloudNaturalLanguageRequestInitializer(
                new CloudNaturalLanguageRequestInitializer(API_KEY)
        ).build();

        document = new Document();
        document.setType("PLAIN_TEXT");
        document.setLanguage("es-ES");

        features = new Features();
        features.setExtractEntities(true);
        features.setExtractSyntax(true);
        features.setExtractDocumentSentiment(true);

        final AnnotateTextRequest request = new AnnotateTextRequest();
        request.setDocument(document);
        request.setFeatures(features);

        entityList = new ArrayList<>();
        entityListAdapter = new EntityListAdapter(entityList);
        //entities.setAdapter(entityListAdapter);
        //entities.setLayoutManager(new LinearLayoutManager(this));



        ////////////////////////////////////////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        nrotalo=findViewById(R.id.textView6);
        nroventas=findViewById(R.id.textView7);
        fechatalo=findViewById(R.id.textView8);
        VentaC= (FrameLayout) findViewById(R.id.activity_venta_credito);
        Inicio= (FrameLayout) findViewById(R.id.inicio);
        Ventas= (FrameLayout) findViewById(R.id.activity_ventas_contado);
        ListaT= (FrameLayout) findViewById(R.id.activity_talonarios);
        Historial= (FrameLayout) findViewById(R.id.activity_historial_talo);
        HTVentasCredito= (FrameLayout) findViewById(R.id.activity_ht_ventcred);
        HTVCinfo= (FrameLayout) findViewById(R.id.activity_htvc_info);
        GesUsuario= (FrameLayout) findViewById(R.id.activity_gestionar_user);
        GesUsuarioTalo= findViewById(R.id.activity_gestuser_talo);
        GesUserTaloCambiar= findViewById(R.id.activity_gutalo_cambiar);
        GesUserTaloEstado= findViewById(R.id.activity_gutalo_estado);
        GesUserTaloVentCred= findViewById(R.id.activity_gutalo_ventcred);
        Perfil=(FrameLayout) findViewById(R.id.activity_perfil);
        Foto= findViewById(R.id.activity_foto);


        ImageestadoGU= findViewById(R.id.estadoGU);

        Image_foto= findViewById(R.id.image_foto);
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
        etclientecontado=findViewById(R.id.vcontnombre);
        etdireccioncontado=findViewById(R.id.vcontdireccion);
        etzonacontado=findViewById(R.id.vcontnrozona);
        etpromcontado=findViewById(R.id.vcontvendedor);
        etcantcontado=findViewById(R.id.editCantidad);
        btnCambiarAPasivo=findViewById(R.id.btnpasivo);
        textfechacrearTalo=findViewById(R.id.textFechaCrearTalo);
        
        TextGUTCidtalo=findViewById(R.id.textGUTCidtalo);
        TextGUTCsupervisor=findViewById(R.id.textGUTCsupervisor);
        TextGUTCfecha=findViewById(R.id.textGUTCfecha);
        TextGUTEidtalo= findViewById(R.id.textGUTEidtalo);
        TextGUTEfecha= findViewById(R.id.textGUTEfecha);
        TextGUTEestado= findViewById(R.id.textGUTEestado);
        TextGUTVCidtalo= findViewById(R.id.idtaloGUTvc);
        TextGUTVCfecha= findViewById(R.id.fechaGUTvc);
        TextGUTVCestado= findViewById(R.id.estadoGUTvc);


        nuevavc=findViewById(R.id.activity_venta_credito);
        cargarDatosTalo();
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++
        lvdetallevc=findViewById(R.id.listviewVCont);
        btnAgregarDetalle=findViewById(R.id.btnagregardetalle);

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
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++=
        Vcontfecha=findViewById(R.id.vcontfecha);
        SpinnerVcont= findViewById(R.id.spinnervcont);

        Vcfecha=findViewById(R.id.vcfecha);

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
        SubjectIdHT = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recicladorG);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        if(!estadoverificacion()){
            finish();
            System.exit(0);
        }

//////////////////////////////////Para Gestionar Usuario Talonario/////////////////////////////////////////
        //CheckPasivo = (CheckBox) findViewById(R.id.checkBoxPasivo);
        //
        // CheckPasivo.setOnClickListener(this);

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
        SubjectNamesHTVCfechmapa= new ArrayList<>();
        SubjectNamesHTVCvendedor = new ArrayList<>();
        SubjectNamesHTVCfoto = new ArrayList<>();
        SubjectNamesHTVClat = new ArrayList<>();
        SubjectNamesHTVClong = new ArrayList<>();
        SubjectNamesHTVCcuotas= new ArrayList<>();

        ////////Para fotos y mapas en Talonarios actuales ventas al credito
        SubjectNamesTVCfecha = new ArrayList<>();
        SubjectNamesTVCcuota = new ArrayList<>();
        SubjectNamesTVClat = new ArrayList<>();
        SubjectNamesTVClong = new ArrayList<>();
        SubjectNamesTVCfoto = new ArrayList<>();

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
        recyclerViewGUTalo.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerViewGUTalo.setLayoutManager(recyclerViewlayoutManager);
        ///////////////////////Dialogo de alerta///////////////////
        dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Esta seguro de cambiar el estado a pasivo ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cambiarActivo();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.cancel();
            }
        });

        ////////////////////////////////Dialogo para activar el GPS///////////////////////////////////
        dialogoGPS = new AlertDialog.Builder(this);
        dialogoGPS.setTitle("Importante");
        dialogoGPS.setMessage("Active el GPS");
        dialogoGPS.setCancelable(false);
        dialogoGPS.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });

        dialogo2 = new AlertDialog.Builder(this);
        dialogo2.setTitle("IMPORTANTE");
        dialogo2.setMessage("¿ Esta seguro de adicionar este cobro?");
        dialogo2.setCancelable(false);
        ///////////////////Dialogo para ver la informacion del usuario//////////////////////////////


        //////////////////////////////Para cargar usuarios en cambiar talonario/////////////////////////////////////////
        SubjectGUTCIdUser = new ArrayList<>();
        jsonArrayGUTC = new JSONArray();
        DataAdapterClassListGUTC = new ArrayList<>();
        DataAdapterClassListGUTC.clear();
        recyclerViewGUTC = (RecyclerView) findViewById(R.id.recicladorGUTCcambiar);
        recyclerViewGUTC.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerViewGUTC.setLayoutManager(recyclerViewlayoutManager);
        ////////////////////////////Para cargar gestionar usuarios talonario ventas//////////////////////////////////////
        SubjectGUTvcid= new ArrayList<>();
        SubjectGUTvcnombre= new ArrayList<>();
        SubjectGUTvctelf= new ArrayList<>();
        SubjectGUTvczona= new ArrayList<>();
        SubjectGUTvcvendedor= new ArrayList<>();
        SubjectGUTvcdir= new ArrayList<>();
        SubjectGUTvcfecha= new ArrayList<>();
        SubjectGUTvcfoto= new ArrayList<>();
        bandera=0;
        banderafoto=0;
        DataAdapterClassListGUTvc = new ArrayList<>();
        DataAdapterClassListGUTvc.clear();
        recyclerViewGUTvc = (RecyclerView) findViewById(R.id.recicladorGUTvc);
        recyclerViewGUTvc.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        recyclerViewGUTvc.setLayoutManager(recyclerViewlayoutManager);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }

        vcConfirmar.setOnClickListener(new View.OnClickListener(){
                                            @Override
                                            public void onClick(View v) {
                                                //vcCamara.setImageResource(android.R.color.transparent);
                                                    if (TextUtils.isEmpty(imageFileName)) {
                                                        Toast.makeText(getApplicationContext(), "debe tomar una foto", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        fotoVC = convertirImgString(imagen);
                                                        final VentaCredito vc = new VentaCredito();
                                                        fechaVC = getCurrentTimeStamp();

                                                        Cursor Talo = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, null,
                                                                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO + "=1", null, null);
                                                        Talo.moveToFirst();
                                                        talolocal = Talo.getString(Talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
                                                        talonube = Talo.getString(Talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID));

                                                        if (nombreCVC.getText().toString().equals("") || telefonoVC.getText().toString().equals("") || direccionVC.getText().toString().equals("") || zonaVC.getText().toString().equals("") || fechaVC.equals("") || nombrePVC.getText().toString().equals("")
                                                                || vcontprod.equals("") || talolocal.equals("")/*||talonube.equals("")*/) {
                                                            Toast.makeText(getApplicationContext(), "Todos los datos son necesarios", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            venta_credito vcred = new venta_credito(nombreCVC.getText().toString(), telefonoVC.getText().toString(), zonaVC.getText().toString(), nombrePVC.getText().toString(), direccionVC.getText().toString(),
                                                                    fechaVC, vcontprod, talolocal, talonube, fotoVC, imageFileName);
                                                            vcred.insert(vcred, mContentResolver);
                                                            Toast.makeText(getApplicationContext(), "Venta creada exitosamente", Toast.LENGTH_SHORT).show();
                                                            denuevaventaacobro();
                                                        }
                                                        Talo.close();
                                                    }
                                                    //vcCamara.setImageResource(android.R.color.transparent);
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
        SpinnerVcred.setSelection(0);
        SpinnerVcont.setSelection(0);
        SpinnerVcont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
            {
                //Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                String c= adapterView.getItemAtPosition(position).toString();//adapterView.getItemAtPosition(position);
                vcontprodcont=c;
                /*if(c.equals("MEGA JUNIOR")){
                    vcontprod="1";
                }else if(c.equals("NONI ENERGY")){
                    vcontprod="2";
                    }else if(c.equals("FIBRA PLUS")){
                        vcontprod="3";
                        }else if(c.equals("MEGA FAMILY")){
                            vcontprod="4";
                }*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                vcontprodcont="MEGA JUNIOR";

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


        SubjectGUid = new ArrayList<>();
        SubjectGUnombre = new ArrayList<>();
        SubjectGUcargo = new ArrayList<>();
        SubjectGUpass = new ArrayList<>();
        SubjectGUestado = new ArrayList<>();
        SubjectGUcorreo = new ArrayList<>();
        SubjectGUtelefono = new ArrayList<>();
        SubjectGUfecha = new ArrayList<>();
        SubjectGUci = new ArrayList<>();;

        ////////////////////////////////para gestionar usuarios talonario/////////////////////////////
        SubjectNamesGUTid = new ArrayList<>();
        SubjectNamesGUTfech = new ArrayList<>();
        SubjectNamesGUTestado = new ArrayList<>();
        SubjectNamesGUTcredito = new ArrayList<>();
        SubjectNamesGUTsaldo = new ArrayList<>();
        personaGU = new persona();

        recyclerViewG.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetectorG = new GestureDetector(NavegacionMenu.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                RecyclerViewClickedItemPOSR=0;

                if(ChildViewG != null && gestureDetectorG.onTouchEvent(motionEvent)){
                    RecyclerViewClickedItemPOSR = Recyclerview.getChildAdapterPosition(ChildViewG);
                    //Toast.makeText(getApplicationContext(),Integer.toString(RecyclerViewClickedItemPOSR),Toast.LENGTH_SHORT).show();
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


        recyclerViewGUTalo.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetectorG = new GestureDetector(NavegacionMenu.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                RecyclerViewClickedItemPOSR=0;

                if(ChildViewG != null && gestureDetectorG.onTouchEvent(motionEvent)){

                    RecyclerViewClickedItemPOSR = Recyclerview.getChildAdapterPosition(ChildViewG);
                    //Toast.makeText(getApplicationContext(),Integer.toString(RecyclerViewClickedItemPOSR),Toast.LENGTH_SHORT).show();
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

        recyclerViewGUTC.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetectorG = new GestureDetector(NavegacionMenu.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                RecyclerViewClickedItemPOSR=0;

                if(ChildViewG != null && gestureDetectorG.onTouchEvent(motionEvent)){

                    RecyclerViewClickedItemPOSR = Recyclerview.getChildAdapterPosition(ChildViewG);
                    //Toast.makeText(getApplicationContext(),Integer.toString(RecyclerViewClickedItemPOSR),Toast.LENGTH_SHORT).show();
                    if (Integer.parseInt(GUTCestadotalo)!=1) {
                        InsertGUTcambiar(SubjectGUTCIdUser.get(RecyclerViewClickedItemPOSR), GUTCidtalo);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Error: El talonario esta ACTIVO",Toast.LENGTH_SHORT).show();
                    }
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

        recyclerViewGUTvc.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetectorG = new GestureDetector(NavegacionMenu.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                RecyclerViewClickedItemPOSR=0;

                if(ChildViewG != null && gestureDetectorG.onTouchEvent(motionEvent)){

                    RecyclerViewClickedItemPOSR = Recyclerview.getChildAdapterPosition(ChildViewG);
                    //Toast.makeText(getApplicationContext(),Integer.toString(RecyclerViewClickedItemPOSR),Toast.LENGTH_SHORT).show();
                    //InsertGUTcambiar(SubjectGUTCIdUser.get(RecyclerViewClickedItemPOSR), GUTCidtalo);
                    nombreHTVCinfo.setText("Nombre: "+SubjectGUTvcnombre.get(RecyclerViewClickedItemPOSR));
                    telefonoHTVCinfo.setText("Telefono: "+SubjectGUTvctelf.get(RecyclerViewClickedItemPOSR));
                    zonaHTVCinfo.setText("Zona: "+SubjectGUTvczona.get(RecyclerViewClickedItemPOSR));
                    vendedorHTVCinfo.setText("Vendedor: "+SubjectGUTvcvendedor.get(RecyclerViewClickedItemPOSR));
                    direccionHTVCinfo.setText("Dir: "+SubjectGUTvcdir.get(RecyclerViewClickedItemPOSR));
                    fechaHTVCinfo.setText("Fecha: "+SubjectGUTvcfecha.get(RecyclerViewClickedItemPOSR));
                    EnvioHTVentCredCobro(SubjectGUTvcid.get(RecyclerViewClickedItemPOSR));//REVISAR DSADASDKSADSJDKSLADJSKLAJDSKLDJSALKDJSAKDASDSADKSADLKSDJDKASDJLAJ
                    //HTVentasCredito.setVisibility(View.INVISIBLE);
                    GesUserTaloVentCred.setVisibility(View.INVISIBLE);
                    bandera=0;
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
                    idtalonube= t.Idnube;
                    idtalolocal=t.Id;
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

                RecyclerViewClickedItemPOSR=0;
                ChildViewG = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildViewG != null && gestureDetectorG.onTouchEvent(motionEvent)) {
                    int x=recyclerView.getChildAdapterPosition(ChildViewG);
                    ventacfinal=arrayVC.get(x);
                    RecyclerViewClickedItemPOSR=x;
                    if (isOnlineNet()) {
                        EnvioTAVentCred(ventacfinal.NubeId);
                    }
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
                    RecyclerViewClickedItemPOSR = Recyclerview.getChildAdapterPosition(ChildViewHT);
                    subjIdHT= SubjectNamesHTid.get(RecyclerViewClickedItemPOSR);
                    //SubjectNames.clear();// = new ArrayList<>();
                    DataAdapterClassListHTVC.clear();
                    recyclerViewHTVC.setAdapter(recyclerViewadapterHTVC);
                    String i="1";
                    EnvioHTVentaCredito(SubjectNamesHTid.get(RecyclerViewClickedItemPOSR));////////cambiar esto subjIdHT
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
                    //Toast.makeText(getApplicationContext(),Integer.toString(RecyclerViewClickedItemPOSR),Toast.LENGTH_SHORT).show();
                    //DataAdapterClassListHTVC.clear();
                    //recyclerViewHTVC.setAdapter(recyclerViewadapterHTVC);
                    //EnvioHTVentaCredito(i);////////cambiar esto subjIdHT
                    nombreHTVCinfo.setText("Nombre: "+SubjectNamesHTVCnombre.get(RecyclerViewClickedItemPOSR));
                    telefonoHTVCinfo.setText("Telefono: "+SubjectNamesHTVCtelf.get(RecyclerViewClickedItemPOSR));
                    zonaHTVCinfo.setText("Zona: "+SubjectNamesHTVCzona.get(RecyclerViewClickedItemPOSR));
                    vendedorHTVCinfo.setText("Vendedor: "+SubjectNamesHTVCvendedor.get(RecyclerViewClickedItemPOSR));
                    direccionHTVCinfo.setText("Dir: "+SubjectNamesHTVCdir.get(RecyclerViewClickedItemPOSR));
                    fechaHTVCinfo.setText("Fecha: "+SubjectNamesHTVCfech.get(RecyclerViewClickedItemPOSR));
                    EnvioHTVentCredCobro(SubjectNamesHTVCid.get(RecyclerViewClickedItemPOSR));//REVISAR DSADASDKSADSJDKSLADJSKLAJDSKLDJSALKDJSAKDASDSADKSADLKSDJDKASDJLAJ
                    HTVentasCredito.setVisibility(View.INVISIBLE);
                    bandera=1;
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
        public void onClick(View view) {
            Cursor s = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN + "=1", null, null);
            if (s.getCount() > 0) {
                s.moveToNext();
                String as = s.getString(s.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO));
                if (as.equals("2")) {
                    Toast.makeText(getApplicationContext(), "No puede crear talonarios", Toast.LENGTH_SHORT);
                } else {
                    deinicioacreartalo();
                }
            }
        }
    });
        creartalo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String be=verificacionCrearTalonario();
                if (be.equals("")) {
                            final String estado = "1";
                            final String fecha = txtfecha.getText().toString();
                            Cursor t = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN + "=1", null, null);
                            t.moveToFirst();
                            final String idsup = t.getString(t.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._PERSONAID));
                            final String idsupnube = t.getString(t.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID));
                            talonario talo = new talonario(estado, fecha, idsup, idsupnube);
                            talo.insert(talo, mContentResolver);
                            t.close();
                            cargarDatosTalo();
                            Toast.makeText(getApplicationContext(),"Talonario creado exitosamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),be,Toast.LENGTH_SHORT).show();
                }

            }


    });
        dialogo2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Cursor s=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                        ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null);
                if(s.getCount()>0) {
                    s.moveToNext();
                    String as=s.getString(s.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO));
                    if(as.equals("2")) {
                        Toast.makeText(getApplicationContext(),"No puede realizar cobros",Toast.LENGTH_SHORT);
                    }else{
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
                                    //gps g=new gps(latitud,longitud);
                                    //g.insert(g,mContentResolver);
                                    //String[] max=new String[]{"MAX("+ecolifedb.EcoLifeEntry._GPSID+")"};
                                    //Cursor gs=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,null,
                                    //        null,null,null);
                                    //gs.moveToLast();
                                    //String idgps=gs.getString(gs.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._GPSID));
                                    //String idgpsnube=gs.getString(gs.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID));
                                    cobro co=new cobro(nuevomonto,Integer.toString(nrocuota),Integer.toString(subtotal),fechaVC,ventacfinal.Id,
                                            ventacfinal.NubeId,latitud,longitud);
                                    co.insert(co,mContentResolver);
                                    Toast.makeText(getApplicationContext(),"Cobro agregado exitosamente",Toast.LENGTH_SHORT).show();
                                    //gs.close();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Monto invalido",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if(Integer.parseInt(nuevomonto)<=140){
                                    nrocuota=1;
                                    subtotal=Integer.parseInt(nuevomonto);
                                    fechaVC = getCurrentTimeStamp();
                                    ConseguirGPS();
                                    //gps g=new gps(latitud,longitud);
                                    //g.insert(g,mContentResolver);
                                    //String[] max=new String[]{"MAX("+ecolifedb.EcoLifeEntry._GPSID+")"};
                                    //Cursor gs=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,null,
                                    //        null,null,null);
                                    //gs.moveToLast();
                                    //String idgps=gs.getString(gs.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._GPSID));
                                    //String idgpsnube=gs.getString(gs.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID));
                                    cobro co=new cobro(nuevomonto,Integer.toString(nrocuota),Integer.toString(subtotal),fechaVC,ventacfinal.Id,
                                            ventacfinal.NubeId,latitud,longitud);
                                    co.insert(co,mContentResolver);
                                    Toast.makeText(getApplicationContext(),"Cobro agregado exitosamente",Toast.LENGTH_SHORT).show();
                                    //gs.close();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Monto invalido",Toast.LENGTH_SHORT).show();
                                }
                            }
                            c.close();
                            generarCobrosActuales();
                            EcoLifeSyncAdapter.syncImmediately(getApplicationContext());
                        }
                    }
                }
                s.close();
            }
        });
        dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo2, int id) {
                dialogo2.cancel();
            }
        });

        btnNuevoCobro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Cursor c = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                        ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN + "=1", null, null);
                c.moveToNext();
                String est = c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO));
                if (est.equals("1")) {
                    if (checkIfLocationOpened()==true){
                        dialogo2.show();
                    }else{
                        dialogoGPS.show();
                    }
                    //dialogo2.show();
                }else{
                    Toast.makeText(getApplicationContext(),"No puede realizar ventas en vacaciones", Toast.LENGTH_SHORT).show();
                }
                c.close();
            }

        });
        etsubcontado=findViewById(R.id.editSub);
        lvdetallevc=findViewById(R.id.listviewVCont);
        btnAgregarVContado=findViewById(R.id.vcontconfirmar);
        ettelefcontado=findViewById(R.id.vconttelefono);
        subtotal=0;
        contador=0;
        detalle=new ArrayList<String>();
        adapterDetalle=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1);
        //EditText etclientecontado,etdireccioncontado,etzonacontado,etpromcontado,etcantcontado;subtotal contador
        //String vcontprod,fechacontado;
        btnAgregarDetalle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(etcantcontado.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Introducir una cantidad",Toast.LENGTH_SHORT).show();
                }else {
                    String cantidad = etcantcontado.getText().toString();
                    String cad = vcontprodcont + " x " + cantidad;
                    detalle.add(transform(vcontprodcont));
                    detalle.add(cantidad);
                    contador++;
                    subtotal = subtotal + (Integer.parseInt(cantidad) * 120);
                    etsubcontado.setText(Integer.toString(subtotal));
                    adapterDetalle.add(cad);
                    lvdetallevc.setAdapter(adapterDetalle);
                }
            }
        });
        btnAgregarVContado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etclientecontado.getText().toString().equals("")||etdireccioncontado.getText().toString().equals("")||
                        etzonacontado.getText().toString().equals("")||etpromcontado.getText().toString().equals("")||
                        etcantcontado.getText().toString().equals("")||etsubcontado.getText().toString().equals("") ||
                        ettelefcontado.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_SHORT).show();
                }else {
                    Cursor p = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                            ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN + "=1", null, null);
                    p.moveToNext();
                    fechacontado = getCurrentTimeStamp();
                    String id = p.getString(p.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._PERSONAID));
                    String idnube = p.getString(p.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID));
                    venta_contado vc = new venta_contado(etclientecontado.getText().toString(), ettelefcontado.getText().toString()
                            , etzonacontado.getText().toString(), etpromcontado.getText().toString(), etdireccioncontado.getText().toString()
                            , fechacontado, id, idnube);
                    vc.insert(vc, mContentResolver);
                    Cursor d = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CONTADO, null,
                            null, null, null);
                    d.moveToLast();
                    for (int i = 0; i < contador * 2; i = i + 2) {
                        String idc = d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._VENTA_CONTADOID));
                        String idcn = d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_NUBEID));
                        String prod = detalle.get(i);
                        String cant = detalle.get(i + 1);
                        detalle_contado detc = new detalle_contado(prod, idc, idcn, cant);
                        detc.insert(detc, mContentResolver);
                    }
                    p.close();
                    d.close();
                    contador = 0;
                    subtotal=0;
                    detalle.clear();
                    adapterDetalle.clear();
                    cleanventacontado();
                    EcoLifeSyncAdapter.syncImmediately(getApplicationContext());
                    Toast.makeText(getApplicationContext(),"Venta al contado agregada con exito",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCambiarAPasivo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Cursor c=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,null,
                        ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO+"=1",null,null);
                if (c.getCount()>0) {
                    dialogo1.show();

                }else{
                    Toast.makeText(getApplicationContext(),"No hay un talonario activo",Toast.LENGTH_SHORT).show();
                }
                cargarDatosTalo();

            }
        });

        /*analyze.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String text = docText.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    document.setContent(text);

                    final AnnotateTextRequest request = new AnnotateTextRequest();
                    request.setDocument(document);
                    request.setFeatures(features);

                    new AsyncTask<Object, Void, AnnotateTextResponse>() {
                        @Override
                        protected AnnotateTextResponse doInBackground(Object... params) {
                            AnnotateTextResponse response = null;
                            try {
                                response = naturalLanguageService.documents().annotateText(request).execute();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return response;
                        }

                        @Override
                        protected void onPostExecute(AnnotateTextResponse response) {
                            super.onPostExecute(response);
                            if (response != null) {
                                Sentiment sent = response.getDocumentSentiment();
                                entityList.addAll(response.getEntities());
                                int longitud = entityList.size();
                                int count = 0;
                                //Entity et = entityList.get(count);
                                while (count<longitud) {
                                    Entity et = entityList.get(count);
                                    //String valor = entityList.ge
                                    if (et.getType().equals("PERSON")) {
                                        nombreCVC.setText(et.getName());
                                        count = count + 1;
                                    } else {
                                        count = count + 1;
                                    }
                                }
                                //entityListAdapter.notifyDataSetChanged();
                                //sentiment.setText("Score : " + sent.getScore() + " Magnitude : " + sent.getMagnitude());
                            }
                        }
                    }.execute();
                }
            }
        });*/
    }




    public void cambiarActivo(){
        Cursor c=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,null,
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO+"=1",null,null);
        if(c.getCount()>0) {
            c.moveToNext();
            String id=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
            String[] args=new String[]{id};
            String est="3";
            ContentValues val=new ContentValues();
            val.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO,est);
            mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,val,
                    ecolifedb.EcoLifeEntry._TALONARIOID+"=?",args);
            Toast.makeText(getApplicationContext(),"Talonario modificado exitosamente",Toast.LENGTH_SHORT).show();
            nrotalo.setText("Nro Talonario: ");
            fechatalo.setText("Fecha de Creacion: ");
            nroventas.setText("Creditos actuales:" );
        }
        c.close();
        EcoLifeSyncAdapter.syncImmediately(getApplicationContext());
    }

    public String transform(String s){
        String a="";
        if(s.equals("MEGA JUNIOR")){
            a="1";
        }else{
            if(s.equals("NONI ENERGY")){
                a="2";
            }else{
                if(s.equals("FIBRA PLUS")){
                    a="3";
                }else{
                    if(s.equals("MEGA FAMILY")){
                        a="4";
                    }
                }
            }
        }
        return a;
    }

    public void cleanventacontado(){
        etclientecontado.setText("");
        etdireccioncontado.setText("");
        etzonacontado.setText("");
        etpromcontado.setText("");
        etcantcontado.setText("");
        etsubcontado.setText("");
        ettelefcontado.setText("");
        etcantcontado.setText("");
    }

    //////////////////////////////////Para llenar la lista de los talonarios de Gestionar Usuario/////////////////////////////
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.checkBoxExpirado:

                if (CheckExpirado.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Tiqueado", Toast.LENGTH_LONG).show();
                    EnvioGUTalo(GUTCiduser, "1","2");
                }else {
                    Toast.makeText(getApplicationContext(), "Des tiqueado", Toast.LENGTH_LONG).show();
                    EnvioGUTalo(GUTCiduser, "0","0");
                }
                break;
        }
        //GUTCnombreuser
        //EnvioGUTalo(SubjectGUid.get(RecyclerViewClickedItemPOSR), "1","2");
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
                Cursor s= mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, null,
                        null,null,null);
                String m="";
                while(s.moveToNext()){
                    m=m+s.getString(s.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
                }
                b=m;
                //b = "Ya hay un talonario activo";
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





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Ventas.setVisibility(View.INVISIBLE);
        VentaC.setVisibility(View.INVISIBLE);
        Inicio.setVisibility(View.INVISIBLE);
        Ventas.setVisibility(View.INVISIBLE);
        ListaT.setVisibility(View.INVISIBLE);
        GesUsuario.setVisibility(View.INVISIBLE);
        Perfil.setVisibility(View.INVISIBLE);
        CambiarPass.setVisibility(View.INVISIBLE);
        CambiarTelf.setVisibility(View.INVISIBLE);
        FrameCrearTalonario.setVisibility(View.INVISIBLE);
        Historial.setVisibility(View.INVISIBLE);
        HTVentasCredito.setVisibility(View.INVISIBLE);
        HTVCinfo.setVisibility(View.INVISIBLE);
        GesUsuarioTalo.setVisibility(View.INVISIBLE);
        GesUserTaloCambiar.setVisibility(View.INVISIBLE);
        GesUserTaloEstado.setVisibility(View.INVISIBLE);
        GesUserTaloVentCred.setVisibility(View.INVISIBLE);
        Foto.setVisibility(View.INVISIBLE);
        //Ventas.removeAllViews();



        ventaCredList.setVisibility(View.INVISIBLE);
        cobroList.setVisibility(View.INVISIBLE);
        EcoLifeSyncAdapter.syncImmediately(getApplicationContext());


        if(!estadoverificacion()){
            finish();
            System.exit(0);
        }


        if (id == R.id.nav_camera) {
            EcoLifeSyncAdapter.syncImmediately(getApplicationContext());
            if(!estadoverificacion()){
                finish();
                System.exit(0);
            }
            String Sfecha = getCurrentTimeStamp();
            Vcontfecha.setText(Sfecha);
            cargarDatosTalo();
            Inicio.setVisibility(View.VISIBLE);


        } else if (id == R.id.nav_gallery) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

            Cursor Persona = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1", null, null);
            Persona.moveToFirst();
            String idpersona = Persona.getString(Persona.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID));

            String estado="0";
            SubjectNames.clear();// = new ArrayList<>();
            DataAdapterClassListHT.clear();
            recyclerViewHT.setAdapter(recyclerViewadapterHT);
            EnvioHistorialTalo(idpersona,estado);
            Historial.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(NavegacionMenu.this, "No tiene acceso a internet: ", Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.nav_slideshow) {
            EcoLifeSyncAdapter.syncImmediately(getApplicationContext());
            if(!estadoverificacion()){
                finish();
                System.exit(0);
            }
            generarTalonariosActuales();
            ListaT.setVisibility(View.VISIBLE);

        } else if (id == R.id.nav_manage) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                EcoLifeSyncAdapter.syncImmediately(getApplicationContext());
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
            }else {
                Toast.makeText(NavegacionMenu.this, "No tiene acceso a internet: ", Toast.LENGTH_LONG).show();
            }


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
        String Sfecha = getCurrentTimeStamp();
        textfechacrearTalo.setText("Fecha: "+Sfecha);
        FrameCrearTalonario.setVisibility(View.VISIBLE);
    }


    public void cargarDatosTalo(){
        Cursor c=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,null,
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO+"=1",null,null);
        if(c.getCount()>0) {
            c.moveToNext();
            String id = c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
            String fecha = c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_FECHA_C));
            String[] args = new String[]{id};
            Cursor v = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO, null,
                    ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIOPID + "=?", args, null);
            nrotalo.setText("Nro Talonario: " + id);
            fechatalo.setText("Fecha de Creacion: " + fecha);
            nroventas.setText("Creditos actuales:" + Integer.toString(v.getCount()));
        }else{
            nrotalo.setText("Nro Talonario: ");
            fechatalo.setText("Fecha de Creacion: ");
            nroventas.setText("Creditos actuales:" );
        }
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
                                            }else{
                                                if(Ventas.getVisibility()==View.VISIBLE){
                                                    Ventas.setVisibility(View.INVISIBLE);
                                                    Inicio.setVisibility(View.VISIBLE);
                                            }else{
                                                if (GesUserTaloCambiar.getVisibility()==View.VISIBLE){
                                                    EnvioGUTalo(GUTCiduser,"0","0");
                                                    GesUserTaloCambiar.setVisibility(View.INVISIBLE);
                                                    GesUsuarioTalo.setVisibility(View.VISIBLE);
                                                }else{
                                                    if (GesUsuarioTalo.getVisibility()==View.VISIBLE){
                                                        GesUsuarioTalo.setVisibility(View.INVISIBLE);
                                                        GesUsuario.setVisibility(View.VISIBLE);
                                                    }else{
                                                        if (GesUsuario.getVisibility()==View.VISIBLE){
                                                            GesUsuario.setVisibility(View.INVISIBLE);
                                                            Inicio.setVisibility(View.VISIBLE);
                                                        }else{
                                                            if (Historial.getVisibility()==View.VISIBLE){
                                                                Historial.setVisibility(View.INVISIBLE);
                                                                Inicio.setVisibility(View.VISIBLE);
                                                            }else{
                                                                if (HTVentasCredito.getVisibility()==View.VISIBLE){
                                                                    HTVentasCredito.setVisibility(View.INVISIBLE);
                                                                    Historial.setVisibility(View.VISIBLE);
                                                                }else{
                                                                    if (HTVCinfo.getVisibility()==View.VISIBLE && bandera==1){
                                                                        HTVCinfo.setVisibility(View.INVISIBLE);
                                                                        HTVentasCredito.setVisibility(View.VISIBLE);
                                                                    }else if (GesUserTaloEstado.getVisibility()==View.VISIBLE){
                                                                        EnvioGUTalo(GUTCiduser,"0","0");
                                                                        GesUserTaloEstado.setVisibility(View.INVISIBLE);
                                                                        GesUsuarioTalo.setVisibility(View.VISIBLE);
                                                                    }else if (GesUserTaloVentCred.getVisibility()==View.VISIBLE){
                                                                        GesUserTaloVentCred.setVisibility(View.INVISIBLE);
                                                                        GesUsuarioTalo.setVisibility(View.VISIBLE);
                                                                    }else{
                                                                        if (HTVCinfo.getVisibility()==View.VISIBLE && bandera==0){
                                                                            HTVCinfo.setVisibility(View.INVISIBLE);
                                                                            GesUserTaloVentCred.setVisibility(View.VISIBLE);
                                                                        }else{
                                                                            if (Foto.getVisibility()==View.VISIBLE && banderafoto==0){
                                                                                Foto.setVisibility(View.INVISIBLE);
                                                                                HTVCinfo.setVisibility(View.VISIBLE);
                                                                            }else{
                                                                                if (Foto.getVisibility()==View.VISIBLE && banderafoto==1){
                                                                                    Foto.setVisibility(View.INVISIBLE);
                                                                                    cobroList.setVisibility(View.VISIBLE);
                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                   }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    ////////////////////////////Para generar la lista de ventas de talonarios en gestionar usuarios
    public void gestuserVentasTalo(View v){
        TextGUTVCidtalo.setText("Talonario: "+SubjectNamesGUTid.get(RecyclerViewClickedItemPOSR));
        TextGUTVCfecha.setText("Fecha: "+SubjectNamesGUTfech.get(RecyclerViewClickedItemPOSR));
        if (SubjectNamesGUTestado.get(RecyclerViewClickedItemPOSR).equals("1")){
            TextGUTVCestado.setText("Estado: Activo");
        }else if (SubjectNamesGUTestado.get(RecyclerViewClickedItemPOSR).equals("2")){
            TextGUTVCestado.setText("Estado: Pasivo");
        }else if (SubjectNamesGUTestado.get(RecyclerViewClickedItemPOSR).equals("0")){
            TextGUTVCestado.setText("Estado: Expirado");
        }
        SolicitudGUTventcred(SubjectNamesGUTid.get(RecyclerViewClickedItemPOSR));
        GesUsuarioTalo.setVisibility(View.INVISIBLE);
        GesUserTaloVentCred.setVisibility(View.VISIBLE);
    }
    ////////////////////////////Para cambiar los talonarios entre supervisores
    public void gestuserCambiarTalo(View v){
        //SubjectNames.clear();// = new ArrayList<>();
        DataAdapterClassListGUTC.clear();
        recyclerViewGUTC.setAdapter(recyclerViewadapterGUTC);
        JSON_WEB_CALLGUTC();
        GUTCidtalo=SubjectNamesGUTid.get(RecyclerViewClickedItemPOSR);
        GUTCestadotalo= SubjectNamesGUTestado.get(RecyclerViewClickedItemPOSR);
        TextGUTCidtalo.setText("Talonario: "+SubjectNamesGUTid.get(RecyclerViewClickedItemPOSR));
        TextGUTCfecha.setText("fecha: "+SubjectNamesGUTfech.get(RecyclerViewClickedItemPOSR));
        TextGUTCsupervisor.setText("Usuario actual responsable: "+GUTCnombreuser);
        GesUsuarioTalo.setVisibility(View.INVISIBLE);
        GesUserTaloCambiar.setVisibility(View.VISIBLE);
    }
    //////////////////////////////Para cambiar el estado de un talonario////////////////////////////////////////
    public void gestuserEstadoTalo(View v){
        //Toast.makeText(NavegacionMenu.this, "No encontrado GPS", Toast.LENGTH_SHORT).show();
        GUTEidtalo=SubjectNamesGUTid.get(RecyclerViewClickedItemPOSR);
        TextGUTEidtalo.setText("Talonario: "+SubjectNamesGUTid.get(RecyclerViewClickedItemPOSR));
        TextGUTEfecha.setText("Fecha: "+SubjectNamesGUTfech.get(RecyclerViewClickedItemPOSR));
        if (SubjectNamesGUTestado.get(RecyclerViewClickedItemPOSR).equals("1")){
            TextGUTEestado.setText("Estado actual: Activo");
        }else if (SubjectNamesGUTestado.get(RecyclerViewClickedItemPOSR).equals("2")){
            TextGUTEestado.setText("Estado actual: Pasivo");
        }else if (SubjectNamesGUTestado.get(RecyclerViewClickedItemPOSR).equals("0")){
            TextGUTEestado.setText("Estado actual: Expirado");
        }
        GesUsuarioTalo.setVisibility(View.INVISIBLE);
        GesUserTaloEstado.setVisibility(View.VISIBLE);
    }

    public void nuevoEstadoTalonario(View v){

        dialogo3 = new AlertDialog.Builder(this);
        dialogo3.setTitle("Advertencia");
        dialogo3.setMessage("Esta por cambiar el estado del talonario");
        dialogo3.setCancelable(false);
        dialogo3.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                if (TextGUTEestado.getText().equals("Estado actual: Activo")&& personaGU.Estado.equals("0")){
                    InsertGUTestado(GUTEidtalo,"2");
                    //Toast.makeText(NavegacionMenu.this, "El talonario sigue activo", Toast.LENGTH_SHORT).show();
                }else if(TextGUTEestado.getText().equals("Estado actual: Pasivo")){
                    InsertGUTestado(GUTEidtalo,"0");
                    //TextGUTEestado.setText("Estado actual: Expirado");
                }else if(TextGUTEestado.getText().equals("Estado actual: Expirado")){
                    Toast.makeText(NavegacionMenu.this, "El talonario ya expiro", Toast.LENGTH_SHORT).show();
                }else{
                    if (TextGUTEestado.getText().equals("Estado actual: Activo")&& personaGU.Estado.equals("1")){
                        Toast.makeText(NavegacionMenu.this, "La persona sigue habilitada", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(NavegacionMenu.this, "El talonario sigue activo", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        dialogo3.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.cancel();
            }
        });

        dialogo3.show();
/*
        if (TextGUTEestado.getText().equals("Estado actual: Activo")&& personaGU.Estado.equals("0")){
            InsertGUTestado(GUTEidtalo,"2");
            //Toast.makeText(NavegacionMenu.this, "El talonario sigue activo", Toast.LENGTH_SHORT).show();
        }else if(TextGUTEestado.getText().equals("Estado actual: Pasivo")){
            InsertGUTestado(GUTEidtalo,"0");
            //TextGUTEestado.setText("Estado actual: Expirado");
        }else if(TextGUTEestado.getText().equals("Estado actual: Expirado")){
            Toast.makeText(NavegacionMenu.this, "El talonario ya expiro", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(NavegacionMenu.this, "El talonario sigue activo", Toast.LENGTH_SHORT).show();
        }*/
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

    public void registrarUsuario(View v){
        Intent i= new Intent(NavegacionMenu.this,RegUser.class);
        startActivity(i);
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

    public void talonarioUsuario(View v){

        EnvioGUTalo(SubjectGUid.get(RecyclerViewClickedItemPOSR),"0","0");
        GUTCiduser=SubjectGUid.get(RecyclerViewClickedItemPOSR);
        GUTCnombreuser=SubjectGUnombre.get(RecyclerViewClickedItemPOSR);
        GesUsuario.setVisibility(View.INVISIBLE);
        GesUsuarioTalo.setVisibility(View.VISIBLE);
    }

    public void editarUsuario(View v){

        Intent intent = new Intent(NavegacionMenu.this, EditarUser.class);
        intent.putExtra("Persona", personaGU);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void talonario_ventas(View v){
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
        DataAdapterClassListVC=(b.generar(mContentResolver,idtalolocal));
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
                //vcTitulo.setText(imageFileName);
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
            //this.imagen=Bitmap.createScaledBitmap(imageBitmap,800,1000,true);
            this.imagen=Bitmap.createBitmap(imageBitmap);
            //this.imagen =redimensionarImagen(imageBitmap,1200,1500);
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        Cursor c = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1", null, null);
        c.moveToNext();
        String est = c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO));
        if (est.equals("1")) {
            String Sfecha = getCurrentTimeStamp();
            Vcontfecha.setText(Sfecha);
            VentaC.setVisibility(View.INVISIBLE);
            Inicio.setVisibility(View.INVISIBLE);
            Ventas.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(getApplicationContext(),"No puede realizar ventas en vacaciones", Toast.LENGTH_SHORT).show();
        }
        c.close();

    }

    public void ventCred(View v){

        Cursor s=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null);
        Cursor t=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,null,
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO+"=1",null,null);
        vcCamara.setImageResource(android.R.color.transparent);
        if(t.getCount()>0) {

            if (s.getCount() > 0) {
                s.moveToNext();
                String a = s.getString(s.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO));
                if (a.equals("2")) {
                    Toast.makeText(getApplicationContext(), "No puede realizar ventas en vacaciones", Toast.LENGTH_SHORT).show();
                } else {
                    String Sfecha = getCurrentTimeStamp();
                    fotoVC = "";
                    Vcfecha.setText(Sfecha);
                    nombreCVC.setText("");
                    telefonoVC.setText("");
                    direccionVC.setText("");
                    zonaVC.setText("");
                    nombrePVC.setText("");
                    Ventas.setVisibility(View.INVISIBLE);
                    Inicio.setVisibility(View.INVISIBLE);
                    VentaC.setVisibility(View.VISIBLE);
                }
            }

            //String text = direccionVC.getText().toString().trim();


        }else{
            Toast.makeText(getApplicationContext(),"No hay talonario activo",Toast.LENGTH_SHORT).show();
        }


        s.close();
        t.close();
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
               // gpsVC = locationStringFromLocation(location);
                latitud=Double.toString(location.getLatitude());
                longitud=Double.toString(location.getLongitude());
                //if (latitud.equals("") & longitud.equals("")){
                //    latitud = "-17.783312";
                 //   longitud = "-63.182126";
                //}

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
                SubjectGUcorreo.add(json.getString("correo"));
                SubjectGUtelefono.add(json.getString("telefono"));
                SubjectGUfecha.add(json.getString("fecha"));
                SubjectGUci.add(json.getString("ci"));


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

                        e.printStackTrace();
                    }
                }else{

                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //JSON_PARSE_DATA_AFTER_WEBCALLRLU(jsonArray);
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
        HistorialTaloFunctionClass RegisterFunctionClass = new HistorialTaloFunctionClass();
        RegisterFunctionClass.execute(Idpersona,estado);
    }


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
    ///////////////////////////Para info cobros en general/////////////////////////////////////////////
    public void EnvioHTVentCredCobro(final String Idventa_credito){

        SubjectNamesHTVCfechmapa.clear();
        SubjectNamesHTVClat.clear();
        SubjectNamesHTVClong.clear();
        SubjectNamesHTVCcuotas.clear();
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
                        //Toast.makeText(NavegacionMenu.this, FinalJSonObject, Toast.LENGTH_LONG).show();
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
                SubjectNamesHTVCfechmapa.add(json.getString("fecha"));
                GetDataAdapter3.setCuota(json.getString("nro_cuota"));
                SubjectNamesHTVCcuotas.add(json.getString("nro_cuota"));
                GetDataAdapter3.setMonto(json.getString("monto"));
                GetDataAdapter3.setSubtotal(json.getString("subtotal"));
                //GetDataAdapter3.setGps(json.getString("id_gps"));
                SubjectNamesHTVClat.add(json.getString("latitud"));
                SubjectNamesHTVClong.add(json.getString("longitud"));
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
    ///////////////////////////////////Para obtener foto y gps en Talonarios actuales ventas al credito///////////
    public void EnvioTAVentCred(final String Idventa_credito){

        SubjectNamesTVCfecha.clear();
        SubjectNamesTVCcuota.clear();
        SubjectNamesTVClat.clear();
        SubjectNamesTVClong.clear();
        SubjectNamesTVCfoto.clear();

        class TVentCredCobroFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            public void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                if (httpResponseMsg.toString().equals("Registration Successfully")) {
                }
                FinalJSonObject = httpResponseMsg ;

                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;
                    try {
                        //Toast.makeText(NavegacionMenu.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(FinalJSonObject);
                        JSON_PARSE_DATA_AFTER_WEBCALLTVCGPS(jsonArray);
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

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLTVA);

                return finalResult;
            }
        }
        TVentCredCobroFunctionClass RegisterFunctionClass = new TVentCredCobroFunctionClass();
        RegisterFunctionClass.execute(Idventa_credito);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALLTVCGPS(JSONArray array){


        for(int i = 0; i<1; i++) {

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                SubjectNamesTVCfoto.add(json.getString("foto"));
                SubjectNamesTVCcuota.add(json.getString("nro_cuota"));
                SubjectNamesTVCfecha.add(json.getString("fecha"));
                SubjectNamesTVClat.add(json.getString("latitud"));
                SubjectNamesTVClong.add(json.getString("longitud"));

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }



    /////////////////////////////////////Para talonario en Gestionar Usuarios////////////////////////////////////////
    public void EnvioGUTalo(final String Idpersona, final String estado, final String estadodos){
        SubjectNamesHTid.clear();
        SubjectNamesGUTid.clear();
        SubjectNamesGUTfech.clear();
        SubjectNamesGUTestado.clear();
        SubjectNamesGUTcredito.clear();
        SubjectNamesGUTsaldo.clear();

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

                //Toast.makeText(NavegacionMenu.this, httpResponseMsg, Toast.LENGTH_LONG).show();
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

                hashMap.put("estadodos",params[2]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLGUtalo);

                return finalResult;
            }
        }
        GUTaloFunctionClass RegisterFunctionClass = new GUTaloFunctionClass();
        RegisterFunctionClass.execute(Idpersona,estado,estadodos);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALLGUtalo(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterGesUTalo GetDataAdapter3 = new DataAdapterGesUTalo();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter3.setNroTalo(json.getString("id"));
                SubjectNamesGUTid.add(json.getString("id"));
                GetDataAdapter3.setFechaTalo(json.getString("fecha_c"));
                SubjectNamesGUTfech.add(json.getString("fecha_c"));
                GetDataAdapter3.setEstado(json.getString("estado"));
                SubjectNamesGUTestado.add(json.getString("estado"));
                GetDataAdapter3.setCreditos(json.getString("COUNT(VC.id_talonario)"));
                SubjectNamesGUTcredito.add(json.getString("COUNT(VC.id_talonario)"));
                GetDataAdapter3.setSaldos(json.getString("saldos"));
                SubjectNamesGUTsaldo.add(json.getString("saldos"));
            }
            catch (JSONException e)
            {

                e.printStackTrace();
            }

            DataAdapterClassListGUTalo.add(GetDataAdapter3);

        }
        //progressBarR.setVisibility(View.GONE);
        recyclerViewadapterGUTalo = new RecyclerAdapGesUtalo(DataAdapterClassListGUTalo, this);
        recyclerViewGUTalo.setAdapter(recyclerViewadapterGUTalo);
    }

    ////////////////////////////////Para Cargar lista de usuarios en Gestionar Usuarios Cambiar Talonario////////////////////////////////
    public void JSON_WEB_CALLGUTC(){

        SubjectGUTCIdUser.clear();
        DataAdapterClassListGUTC.clear();
        recyclerViewGUTC.setAdapter(recyclerViewadapterGUTC);

        jsonArrayRequest = new JsonArrayRequest(HTTP_SERVER_URLGUTC,

                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALLGUTC(response);
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

    public void JSON_PARSE_DATA_AFTER_WEBCALLGUTC(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterGesU GetDataAdapter2 = new DataAdapterGesU();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setIdusuario(json.getString("id"));
                SubjectGUTCIdUser.add(json.getString("id"));
                GetDataAdapter2.setNombre(json.getString("nombre"));

                GetDataAdapter2.setCargo(json.getString("id_rol"));

                GetDataAdapter2.setEstado(json.getString("estado"));

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            DataAdapterClassListGUTC.add(GetDataAdapter2);
        }
        recyclerViewadapterGUTC = new RecyclerAdapGUTCambiar(DataAdapterClassListGUTC, this);

        recyclerViewGUTC.setAdapter(recyclerViewadapterGUTC);
    }

    /////////////////////////Para cambiar el talonario entre usuarios///////////////////////////////////////
    public void InsertGUTcambiar(final String Idpersona, final String Idtalo){

        class GUTCFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                //progressDialog.dismiss();

                //Toast.makeText(NavegacionMenu.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                if (httpResponseMsg.equals("Se cambio correctamente")){
                    TextGUTCsupervisor.setText("Nuevo encargado: "+GUTCnombreuser);
                }
                FinalJSonObject = httpResponseMsg ;

            }
            @Override
            protected String doInBackground(String... params) {

                hashMap.put("idpersona",params[0]);

                hashMap.put("idtalo",params[1]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLGUTCcambio);

                return finalResult;
            }
        }
        GUTCFunctionClass RegisterFunctionClass = new GUTCFunctionClass();
        RegisterFunctionClass.execute(Idpersona,Idtalo);
    }

    public void InsertGUTestado(final String Idtalo, final String estado){

        class GUTCFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                //progressDialog.dismiss();

                //Toast.makeText(NavegacionMenu.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                if (httpResponseMsg.equals("Se cambio correctamente")){
                    TextGUTEestado.setText("Nuevo estado: Expirado");
                }else if (httpResponseMsg.equals("Error no se pudo cambiar")){
                    Toast.makeText(NavegacionMenu.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                }

                //FinalJSonObject = httpResponseMsg ;

            }
            @Override
            protected String doInBackground(String... params) {

                hashMap.put("idtalo",params[0]);

                hashMap.put("estado",params[1]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLGUTE);

                return finalResult;
            }
        }
        GUTCFunctionClass RegisterFunctionClass = new GUTCFunctionClass();
        RegisterFunctionClass.execute(Idtalo,estado);
    }

    ///////////////////////////////Para Gestionar Usuarios Ventas al credito///////////////////////////////////////
    public void SolicitudGUTventcred(final String Idtalo){
        //SubjectNamesHTid.clear();
        //SubjectNamesGUTid.clear();
        //SubjectNamesGUTfech.clear();
        //SubjectNamesGUTestado.clear();
        //DataAdapterClassListGUTvc.clear();
        SubjectGUTvcid.clear();
        SubjectGUTvcnombre.clear();
        SubjectGUTvctelf.clear();
        SubjectGUTvczona.clear();
        SubjectGUTvcvendedor.clear();
        SubjectGUTvcdir.clear();
        SubjectGUTvcfecha.clear();
        SubjectGUTvcfoto.clear();
        DataAdapterClassListGUTvc.clear();
        recyclerViewGUTvc.setAdapter(recyclerViewadapterGUTvc);

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

                //Toast.makeText(NavegacionMenu.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                FinalJSonObject = httpResponseMsg ;

                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        //Toast.makeText(NavegacionMenu.this, FinalJSonObject, Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(FinalJSonObject);
                        JSON_PARSE_DATA_AFTER_WEBCALLGUTvc(jsonArray);
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

                hashMap.put("idtalo",params[0]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLGUTvc);

                return finalResult;
            }
        }
        GUTaloFunctionClass RegisterFunctionClass = new GUTaloFunctionClass();
        RegisterFunctionClass.execute(Idtalo);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALLGUTvc(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            DataAdapterGesUTaloVC GetDataAdapter3 = new DataAdapterGesUTaloVC();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter3.setIdventa(json.getString("id"));
                SubjectGUTvcid.add(json.getString("id"));
                GetDataAdapter3.setNombre(json.getString("nombre"));
                SubjectGUTvcnombre.add(json.getString("nombre"));
                SubjectGUTvctelf.add(json.getString("telefono"));
                SubjectGUTvczona.add(json.getString("zona"));
                SubjectGUTvcvendedor.add(json.getString("vendedor"));
                SubjectGUTvcdir.add(json.getString("direccion"));
                GetDataAdapter3.setGUTvcFecha(json.getString("fecha"));
                SubjectGUTvcfecha.add(json.getString("fecha"));
                GetDataAdapter3.setCuota(json.getString("nro_cuota"));
                GetDataAdapter3.setSubtotal(json.getString("subtotal"));
                SubjectGUTvcfoto.add(json.getString("foto"));

            }
            catch (JSONException e)
            {

                e.printStackTrace();
            }
            DataAdapterClassListGUTvc.add(GetDataAdapter3);
        }
        //progressBarR.setVisibility(View.GONE);
        recyclerViewadapterGUTvc = new RecyclerAdapGUTvc(DataAdapterClassListGUTvc, this);
        recyclerViewGUTvc.setAdapter(recyclerViewadapterGUTvc);
    }

    public void Traer_foto_GUTVC(View v){
        if (isOnlineNet()) {

            try {
                Traer_foto(SubjectGUTvcfoto.get(RecyclerViewClickedItemPOSR));
                HTVCinfo.setVisibility(View.INVISIBLE);
                Foto.setVisibility(View.VISIBLE);
                banderafoto=0;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            try {
                Traer_foto(SubjectNamesHTVCfoto.get(RecyclerViewClickedItemPOSR));
                HTVCinfo.setVisibility(View.INVISIBLE);
                Foto.setVisibility(View.VISIBLE);
                banderafoto=0;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        //Traer_foto(SubjectNamesHTVCfoto.get(RecyclerViewClickedItemPOSR));
        //Traer_foto(SubjectGUTvcfoto.get(RecyclerViewClickedItemPOSR));

        }else{
            Toast.makeText(NavegacionMenu.this, "No tiene acceso a internet: ", Toast.LENGTH_LONG).show();
        }
    }
    public void Traer_foto_VC(View v){
        if(SubjectNamesTVCfoto.isEmpty()) {
            Toast.makeText(NavegacionMenu.this, "No puede ver esta informacion ahora mismo", Toast.LENGTH_SHORT).show();
        }else{
            if (isOnlineNet()) {
                Traer_foto(SubjectNamesTVCfoto.get(0));
                cobroList.setVisibility(View.INVISIBLE);
                Foto.setVisibility(View.VISIBLE);
                banderafoto = 1;
                //banderafoto=0;
            } else {
                Toast.makeText(NavegacionMenu.this, "No tiene acceso a internet: ", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Traer_foto(String url){
        Picasso.with(this)
        .load(url)
        .error(R.drawable.camara)
        .fit()
        .centerInside()
        .into(Image_foto);
    }

    public void AbrirMapaVC(View v){

        if (SubjectNamesTVCfecha.isEmpty()) {
            Toast.makeText(NavegacionMenu.this, "No puede ver esta informacion ahora mismo", Toast.LENGTH_SHORT).show();
        }else {

            if (isOnlineNet()) {
                //Toast.makeText(NavegacionMenu.this, Integer.toString(SubjectNamesTVCfecha.size()), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(NavegacionMenu.this, MapsActivity.class);
                intent.putStringArrayListExtra("fechas", SubjectNamesTVCfecha);
                intent.putStringArrayListExtra("cuotas", SubjectNamesTVCcuota);
                intent.putStringArrayListExtra("latitudes", SubjectNamesTVClat);
                intent.putStringArrayListExtra("longitudes", SubjectNamesTVClong);
                startActivity(intent);
            } else {
                Toast.makeText(NavegacionMenu.this, "No tiene acceso a internet: ", Toast.LENGTH_LONG).show();
            }

        }
    }


    public void AbrirMapaGUTVC(View v){

            if (isOnlineNet()) {
                Intent intent = new Intent(NavegacionMenu.this, MapsActivity.class);
                intent.putStringArrayListExtra("fechas", SubjectNamesHTVCfechmapa);
                intent.putStringArrayListExtra("cuotas", SubjectNamesHTVCcuotas);
                intent.putStringArrayListExtra("latitudes", SubjectNamesHTVClat);
                intent.putStringArrayListExtra("longitudes", SubjectNamesHTVClong);
                startActivity(intent);
            } else {
                Toast.makeText(NavegacionMenu.this, "No tiene acceso a internet: ", Toast.LENGTH_LONG).show();
            }

    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void InfoGesUser(View v){

        dialogouser = new AlertDialog.Builder(this);
        dialogouser.setTitle(SubjectGUnombre.get(RecyclerViewClickedItemPOSR));
        dialogouser.setMessage("CI: "+SubjectGUci.get(RecyclerViewClickedItemPOSR) +"\n"+
                "Telf: "+SubjectGUtelefono.get(RecyclerViewClickedItemPOSR)+"\n"+
                "Fecha nac:"+SubjectGUfecha.get(RecyclerViewClickedItemPOSR)+"\n"+
                "Usuario: "+SubjectGUcorreo.get(RecyclerViewClickedItemPOSR));
        dialogouser.setCancelable(false);
        dialogouser.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogouser, int id) {

            }
        });

        dialogouser.show();
    }

    public void GUTaloEstadoInfo(View v){

        dialogoinfo = new AlertDialog.Builder(this);
        dialogoinfo.setTitle(R.string.gutalo_estadoinfoboton);
        dialogoinfo.setMessage(R.string.gutalo_estadoinfo);
        dialogoinfo.setCancelable(false);
        dialogoinfo.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });

        dialogoinfo.show();
    }

    public void GUTaloCrearInfo(View v){

        dialogoinfo = new AlertDialog.Builder(this);
        dialogoinfo.setTitle(R.string.gutalo_estadoinfoboton);
        dialogoinfo.setMessage(R.string.crear_taloinfo);
        dialogoinfo.setCancelable(false);
        dialogoinfo.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });

        dialogoinfo.show();
    }

    public boolean checkIfLocationOpened(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        System.out.println("Provider contains=> "+ provider);
        if (provider.contains("gps")||provider.contains("network")){
            return true;
        }
        return false;
    }



        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case REQUEST_RECORD_PERMISSION:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        speech.startListening(recognizerIntent);
                    } else {
                        Toast.makeText(NavegacionMenu.this, "Permission Denied!", Toast
                                .LENGTH_SHORT).show();
                    }
            }
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();

        }

        @Override
        protected void onStop() {
            super.onStop();
            if (speech != null) {
                speech.destroy();
                Log.i(LOG_TAG, "destroy");
            }
        }


        @Override
        public void onBeginningOfSpeech() {
            Log.i(LOG_TAG, "onBeginningOfSpeech");
            //progressBar.setIndeterminate(false);
            //progressBar.setMax(10);
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.i(LOG_TAG, "onBufferReceived: " + buffer);
        }

        @Override
        public void onEndOfSpeech() {
            Log.i(LOG_TAG, "onEndOfSpeech");
            //progressBar.setIndeterminate(true);
            toggleButton.setChecked(false);
        }

        @Override
        public void onError(int errorCode) {
            String errorMessage = getErrorText(errorCode);
            Log.d(LOG_TAG, "FAILED " + errorMessage);
            returnedText.setText(errorMessage);
            toggleButton.setChecked(false);
        }

        @Override
        public void onEvent(int arg0, Bundle arg1) {
            Log.i(LOG_TAG, "onEvent");
        }

        @Override
        public void onPartialResults(Bundle arg0) {
            Log.i(LOG_TAG, "onPartialResults");
        }

        @Override
        public void onReadyForSpeech(Bundle arg0) {
            Log.i(LOG_TAG, "onReadyForSpeech");
        }

        @Override
        public void onResults(Bundle results) {
            Log.i(LOG_TAG, "onResults");
            ArrayList<String> matches = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String text = "";
            palabraSpeechT=matches.get(0);
            /*
            for (String result : matches)
                text += result + "\n";
            */
            //returnedText.setText(text);
            //String text = palabraSpeechT;


            if (!TextUtils.isEmpty(palabraSpeechT)) {
                document.setContent(palabraSpeechT);

                final AnnotateTextRequest request = new AnnotateTextRequest();
                request.setDocument(document);
                request.setFeatures(features);

                new AsyncTask<Object, Void, AnnotateTextResponse>() {
                    @Override
                    protected AnnotateTextResponse doInBackground(Object... params) {
                        AnnotateTextResponse response = null;
                        try {
                            response = naturalLanguageService.documents().annotateText(request).execute();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return response;
                    }

                    @Override
                    protected void onPostExecute(AnnotateTextResponse response) {
                        super.onPostExecute(response);
                        if (response != null) {
                            Sentiment sent = response.getDocumentSentiment();
                            entityList.addAll(response.getEntities());
                            int longitud = entityList.size();
                            int count = 0;
                            //Entity et = entityList.get(count);
                            while (count<longitud) {
                                Entity et = entityList.get(count);
                                //String valor = entityList.ge
                                if (et.getType().equals("PERSON")) {
                                    nombreCVC.setText(et.getName());
                                    count = count + 1;

                                //if (et.getType().equals(""))
                                }else if (et.getType().equals("NUMBER")){
                                    telefonoVC.setText(et.getName());
                                    count = count + 1;

                                }else if (et.getType().equals("LOCATION")){
                                    direccionVC.setText(et.getName());
                                    count = count + 1;
                                }

                                else {
                                    count = count + 1;
                                }
                            }
                            //entityListAdapter.notifyDataSetChanged();
                            //sentiment.setText("Score : " + sent.getScore() + " Magnitude : " + sent.getMagnitude());
                        }
                    }
                }.execute();
            }
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
            //progressBar.setProgress((int) rmsdB);
        }

        public String getErrorText(int errorCode) {
            String message;
            switch (errorCode) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "Audio recording error";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "Client side error";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "Insufficient permissions";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "Network error";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "Network timeout";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "No match";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RecognitionService busy";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "error from server";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "No speech input";
                    break;
                default:
                    message = "Didn't understand, please try again.";
                    break;
            }
            return message;
        }




}
