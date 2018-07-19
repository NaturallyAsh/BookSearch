package com.example.ashleighwilson.booksearch;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static String ISBN = "";
    private EditText searchString;
    private ImageView btnSearch;
    private String QUERY_KEY;
    private Button qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        searchString = findViewById(R.id.seach_edit_text);
        btnSearch = findViewById(R.id.btn_search);
        qrScan = findViewById(R.id.qr_scanner);
        qrScan.setOnClickListener(this);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                QUERY_KEY = String.valueOf(searchString.getText());
                if (QUERY_KEY.trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), R.string.no_search_term,
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, BookActivity.class);
                    intent.putExtra("key", QUERY_KEY);
                    Log.i(TAG, "QUERY_KEY = " + QUERY_KEY);
                    startActivity(intent);
                    searchString.setText("");
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.qr_scanner)
        {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode,
                resultCode, intent);
        if (scanningResult != null)
        {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            if (scanContent != null && scanFormat != null && scanFormat.equalsIgnoreCase("EAN_13"))
            {
                String bookSearchString = "isbn:" + scanContent;
                QUERY_KEY = bookSearchString;

                Intent intent1 = new Intent(MainActivity.this, BookActivity.class);
                intent1.putExtra("key", QUERY_KEY);
                startActivity(intent1);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Not a valid scan!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No book scan data" +
                    "received", Toast.LENGTH_SHORT).show();
        }
    }


}
