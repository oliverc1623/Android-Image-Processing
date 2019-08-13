package com.example.imageprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 1;
    private static final int CAMERA_PIC_REQUEST = 2;
    private Button selectImgButton;
    private Button takePhotoButton;
    private ImageView imageView;
    private RadioButton radioButtonSharpen;
    private RadioButton radioButtonEdge;
    private RadioButton radioButtonBlur;
    private Kernel sharpenKernel;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectImage();
        }
    };
    private View.OnClickListener photoButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) { takePhoto(); }};
    private View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) { onRadioButtonClick(view); }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();

        selectImgButton.setOnClickListener(onClickListener);
        takePhotoButton.setOnClickListener(photoButtonOnClickListener);
        radioButtonSharpen.setOnClickListener(radioButtonClickListener);
        radioButtonEdge.setOnClickListener(radioButtonClickListener);
        radioButtonBlur.setOnClickListener(radioButtonClickListener);
    }

    private void wireWidgets() {
        selectImgButton = (Button)findViewById(R.id.button_img_select);
        takePhotoButton = (Button)findViewById(R.id.button_take_photo);
        imageView = (ImageView)findViewById(R.id.image_view);
        radioButtonSharpen = (RadioButton)findViewById(R.id.radio_button_sharpen);
        radioButtonEdge = (RadioButton)findViewById(R.id.radio_button_edge);
        radioButtonBlur = (RadioButton)findViewById(R.id.radio_button_blur);
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_IMAGE);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }

    private void onRadioButtonClick(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_button_sharpen:
                if (checked){
                    Toast.makeText(this, "Sharpen", Toast.LENGTH_SHORT).show();

                    
                }
                break;
            case R.id.radio_button_edge:
                if (checked){
                    Toast.makeText(this, "edge detection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.radio_button_blur:
                if (checked){
                    Toast.makeText(this, "Blur", Toast.LENGTH_SHORT).show();
                }
                break;
            default: {
                System.out.println("None Chosen");
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            System.out.println("Select Button INTENT RESULT");
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
                        imageView.setImageBitmap(bitmap);
                        sharpenKernel = new Kernel(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();
                imageView.setImageBitmap(bitmap);
            } else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
