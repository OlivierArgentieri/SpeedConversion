package com.example.siosl.webservice;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import android.app.Activity;

public class WS extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ws);

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            String[] arraySpinner = {"centimetersPersecond", "metersPersecond", "feetPersecond", "feetPerminute", "milesPerhour", "kilometersPerhour", "furlongsPermin", "knots", "leaguesPerday", "Mach"};

            Spinner s = (Spinner) findViewById(R.id.ValeurDepart);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
            s.setAdapter(adapter);

            Spinner s2 = (Spinner) findViewById(R.id.ValeurFin);
            s2.setAdapter(adapter);


        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_w, menu);
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

        public void buttonOnClick(View v) throws IOException, XmlPullParserException {

                    String SOAP_ACTION = "http://www.webserviceX.NET/ConvertSpeed";
                    String METHOD_NAME = "ConvertSpeed";
                    String NAMESPACE = "http://www.webserviceX.NET/";
                    String URL = "http://www.webservicex.net/ConvertSpeed.asmx?WSDL";
                    Spinner Depart = (Spinner) findViewById(R.id.ValeurDepart);
                    Spinner Fin = (Spinner) findViewById(R.id.ValeurFin);
                    EditText LeNb = (EditText) findViewById(R.id.Vitesse);
                    TextView txt = (TextView) findViewById(R.id.result);



                    // Créé requéte Soap
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                    request.addProperty("speed", LeNb.getText().toString());
                    request.addProperty("FromUnit", Depart.getSelectedItem().toString());
                    request.addProperty("ToUnit", Fin.getSelectedItem().toString());

                    // créé l'enveloppe : les données demander seront dans cette enveloppe
                    SoapSerializationEnvelope enveloppe = new SoapSerializationEnvelope(SoapEnvelope.VER10);

                    // préparation de la requéte
                    enveloppe.setOutputSoapObject(request);
                    enveloppe.dotNet = true;

                    try {
                        HttpTransportSE androidTransport = new HttpTransportSE(URL);
                        // envoie de la requéte
                        androidTransport.call(SOAP_ACTION, enveloppe);
                        SoapObject result = (SoapObject) enveloppe.bodyIn;

                        if (result != null) {
                            txt.setText(result.getProperty(0).toString());
                        } else {
                            txt.setText("Object result null !!" + request.getNamespace() + " " + SOAP_ACTION);
                        }


                    } catch (Exception e) {
                        txt.setText("Error");
                    }





        }

    }




