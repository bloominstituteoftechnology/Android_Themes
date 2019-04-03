package com.example.applist;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private AppListing app;
    private TextView nameView;
    private TextView vView;
    private TextView domainView;
    private TextView emailView;
    private TextView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent i = getIntent();
        app = (AppListing)i.getSerializableExtra("key");

    nameView = findViewById(R.id.app_name);
    nameView.setText(app.getAppName());
    vView = findViewById(R.id.app_version);
    vView.setText(app.getAppVersion());
    domainView = findViewById(R.id.domain_name);
    domainView.setText(app.getDomainName());
    emailView = findViewById(R.id.contact_email);
    emailView.setText(app.getContactEmail());
    imageView = findViewById(R.id.image_view);
    imageView.setText(app.getImageURL());

    }
}
