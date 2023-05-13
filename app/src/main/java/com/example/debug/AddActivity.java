package com.example.debug;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.Manifest;
public class AddActivity extends AppCompatActivity {

    ImageView homeicon;
    ImageView offersicon;
    ImageView basketicon;
    ImageView logouticon;

    String Colector = "";
    EditText name, model,prodY ,overview, Amount;
    Button SubmitSave;



    // One Preview Image
    ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 10;
    String selectedType ="";
    String selectedCity ="";

    String URL ="";
    Bitmap photoBitmap;

    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> selectImageLauncher;
    DataBaseHelper dataBaseHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("userEmail");



        // Request permission to read external storage
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // Launch the image selection activity
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        selectImageLauncher.launch(i);
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                });

        // Launch the image selection activity and retrieve the selected image
        selectImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            photoBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });


        homeicon= (ImageView) findViewById(R.id.homeicon);
        homeicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, HomeActivity.class));
            }
        });

        offersicon= (ImageView) findViewById(R.id.offericon);
        offersicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, HomeActivity.class));
            }
        });

        basketicon= (ImageView) findViewById(R.id.basketicon);
        basketicon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this, HomeActivity.class));
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No",null);



        logouticon= (ImageView) findViewById(R.id.logouticon);
        logouticon.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view) {
                builder.show();

            }
        });



        name = findViewById(R.id.name);
        model = findViewById(R.id.model);
        Amount = findViewById(R.id.Amount);
        prodY=findViewById(R.id.prodY);
        overview = findViewById(R.id.overview);
        SubmitSave = findViewById(R.id.btnSubmit);

        dataBaseHelper = new DataBaseHelper(AddActivity.this);

        SubmitSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = name.getText().toString();
                String Model = model.getText().toString();
                String over = overview.getText().toString();
                String amount = Amount.getText().toString();
                String prod = prodY.getText().toString();


                if (Name.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the plate number field", Toast.LENGTH_SHORT).show();
                } else if (Model.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the model field", Toast.LENGTH_SHORT).show();
                } else if (over.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the overview field", Toast.LENGTH_SHORT).show();
                } else if (amount.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the rent amount field", Toast.LENGTH_SHORT).show();
                } else if (prod.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please fill the production year field", Toast.LENGTH_SHORT).show();
                }

                else {

                    Colector += "Name: " + Name + "\n";
                    Colector += "Model: " + Model + "\n";
                    Colector += "Description: " +over + "\n";


                    // create model
                    toolModel toolMod;
                    try {
                        toolMod = new toolModel(-1, 0,Name, Model, over, Integer.parseInt(amount), prod,0 );
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddActivity.this);
                        boolean b = dataBaseHelper.addOne(toolMod, userEmail);
                        if(b==true){

                            AlertDialog.Builder b1=  new AlertDialog.Builder(AddActivity.this);
                            b1.setTitle("Added successfully");
                            b1.setMessage("Your Tool is ready to rent!");

                            b1.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }
                            });

                            b1.show();







                        }
                    } catch (Exception e) {
                        Toast.makeText(AddActivity.this, "Enter Valid input", Toast.LENGTH_SHORT).show();


                    }


                }
            }


        });

        List<String> categoryType = new ArrayList<>();
        categoryType.add("Select Type");
        categoryType.add("Car");
        categoryType.add("Boat");
        categoryType.add("Motorcycle");



        // register the UI widgets with their appropriate IDs

        // handle the Choose Image button to trigger
        // the image chooser function



    }

  /*  void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }*/

    // this function is triggered when user
    // selects the image from the imageChooser
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && data != null){
            Uri uri=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream= BitmapFactory.decodeStream(inputStream);
                //  IVPreviewImage.setImageBitmap(decodeStream);
            } catch (Exception e) {
                Log.e("ex",e.getMessage());
            }
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}