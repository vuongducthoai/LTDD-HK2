package com.example.tuan9;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button btnPaired;
    ListView listDanhSach;
    public static int REQUEST_BLUETOOTH = 1;

    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_adress";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
       //and xa
        btnPaired = (Button) findViewById(R.id.btnTimThietBi);
        listDanhSach = (ListView) findViewById(R.id.listTb);
        //Kiem tra thiet bi
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if(myBluetooth == null){
            //Show a message, that the device has not bluetooth adapter
            Toast.makeText(getApplicationContext(), "Thiê bị BlueTooth chưa bật", Toast.LENGTH_LONG).show();
            //finsh apk
            finish();
        } else if (!myBluetooth.isEnabled()){
            //Ask to the user turn the bluetooth on
            Intent turnBton = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Thiết bị Bluetooth đã bật", Toast.LENGTH_LONG).show();
                //startForResult.launch(turnBton);
                startActivityForResult(turnBton, REQUEST_BLUETOOTH);
            }
            //Ket thuc kiem tra thiet bi co bluetooth
            //thuc hien tim thiet bi
            btnPaired.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    pairedDevicesList();
                }
            });

        }
    }

    private void pairedDevicesList() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){
            pairedDevices = myBluetooth.getBondedDevices();
            ArrayList list = new ArrayList();

            if(pairedDevices.size() > 0){
                for(BluetoothDevice bt : pairedDevices){
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getApplicationContext(), "Danh sách thiết bị Bluetooth đã bật",Toast.LENGTH_LONG).show();
                        list.add(Integer.parseInt(bt.getName()), "\n" + bt.getAddress()); // Get the device's name and the address
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Không tìm thấy thiết bị kết nối.", Toast.LENGTH_LONG).show();
            }

            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            listDanhSach.setAdapter(adapter);
            listDanhSach.setOnItemClickListener(myClickListener);
        }
    }

    private AdapterView.OnItemClickListener myClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            //Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            //Make an intent to start next activity
            Intent i = new Intent(MainActivity.this, BlueControl.class);

            //Change the activity
            i.putExtra(EXTRA_ADDRESS, address);
            startActivity(i);
        }
    };
}