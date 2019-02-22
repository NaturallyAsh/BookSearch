package com.example.ashleighwilson.booksearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = UserFragment.class.getSimpleName();

    public static String ISBN = "";
    @BindView(R.id.seach_edit_text)
    EditText searchString;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    String QUERY_KEY;
    @BindView(R.id.qr_scanner)
    Button qrScan;


    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.book_activity_main, container, false);
        ButterKnife.bind(this, rootView);

        init();

        return rootView;
    }


    private void init() {
        qrScan.setOnClickListener(this);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                QUERY_KEY = String.valueOf(searchString.getText());
                if (QUERY_KEY.trim().length() == 0)
                {
                    Toast.makeText(getContext(), R.string.no_search_term,
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(getActivity(), BookActivity.class);
                    intent.putExtra("key", QUERY_KEY);
                    Log.i(TAG, "QUERY_KEY = " + QUERY_KEY);
                    startActivity(intent);
                    searchString.setText("");
                }
            }
        });
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

                Intent intent1 = new Intent(getActivity(), BookActivity.class);
                intent1.putExtra("key", QUERY_KEY);
                startActivity(intent1);
            }
            else
            {
                Toast.makeText(getContext(), "Not a valid scan!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getContext(),"No book scan data" +
                    "received", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.qr_scanner)
        {
            IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
            scanIntegrator.initiateScan();
        }
    }
}
