package kr.or.womanup.nambu.hjy.permissiontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String[] permissionList = {
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        textView = findViewById(R.id.textview);
        checkPermission();
    }

    public void checkPermission(){
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){ //마시멜로 이후 버전은 헤야하기 때문
            return;
        }
        int chk=0;
        for(String permission : permissionList){
            chk = checkCallingOrSelfPermission(permission); //denied = -1  허용=0
            if(chk == PackageManager.PERMISSION_DENIED){ //허가 안 받은게 있으면
                requestPermissions(permissionList,101); //배열 넘겨줌
                break;
            }
        }

    }

    @Override //허가 다시 물어보기
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode!=101){
            return;
        }
        textView.setText("");
        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                String result = permissions[i] + ": 허가 \n";
                textView.append(result);
            }else {
                String result = permissions[i] + ": 거부 \n";
                textView.append(result);
            }
        }
    }
}