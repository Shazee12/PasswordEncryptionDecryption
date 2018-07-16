package shahzaib.com.passwordencryptiondecryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private EditText edttext,edtpwd;
    private TextView txtenc;
    private Button btnenc,btndec;
    String outputenc;
    String AES ="AES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edttext = findViewById(R.id.txt1);
        edtpwd = findViewById(R.id.pwdtxt);

        txtenc = findViewById(R.id.txtenc);
        btnenc = findViewById(R.id.encbtn);
        btndec = findViewById(R.id.decbtn);

        btnenc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputenc = enrypt(edttext.getText().toString(),edtpwd.getText().toString());
                    txtenc.setText(outputenc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputenc = decrypt(outputenc,edtpwd.getText().toString());
                    txtenc.setText(outputenc);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private String decrypt(String data, String password) throws Exception {
        SecretKeySpec key = generatekey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeval = android.util.Base64.decode(data, android.util.Base64.DEFAULT);
        byte[] decVal = c.doFinal(decodeval);

        String decodedVal = new String(decVal);
        return decodedVal;

    }

    private String enrypt(String data, String password) throws Exception {
        SecretKeySpec key = generatekey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = android.util.Base64.encodeToString(encVal, android.util.Base64.DEFAULT);
        return encryptedValue;

    }

    private SecretKeySpec generatekey(String password) throws Exception {
       final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        messageDigest.update(bytes,0,bytes.length);
        byte[] key = messageDigest.digest();
        SecretKeySpec keySpec = new SecretKeySpec(key,"AES");
        return keySpec;
    }
}
