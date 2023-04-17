package com.example.wishhair.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.wishhair.R;

public class TermsActivity extends AppCompatActivity {

    CheckBox termMember, termInfo, termServe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity_terms);

        Button btn_back = findViewById(R.id.botBar_btn_back);
        btn_back.setOnClickListener(view -> finish());

        termMember = findViewById(R.id.sign_terms_checkMember);
        termInfo = findViewById(R.id.sign_terms_checkInfo);
        termServe = findViewById(R.id.sign_terms_checkServe);

        Button btn_next = findViewById(R.id.botBar_btn_next);
        btn_next.setOnClickListener(view -> {
            if (termMember.isChecked() && termInfo.isChecked() && termServe.isChecked()) {
                Intent intent = new Intent(TermsActivity.this, EmailCertActivity.class);
                startActivity(intent);
//                    finish();
            } else {
                Toast.makeText(TermsActivity.this, "약관에 모두 동의해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
