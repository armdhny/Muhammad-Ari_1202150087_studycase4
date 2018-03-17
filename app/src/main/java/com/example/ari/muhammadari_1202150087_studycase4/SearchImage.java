package com.example.ari.muhammadari_1202150087_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SearchImage extends AppCompatActivity {
    //Deklarasi Komponen yang akan digunakan
    //EditText
    private EditText txtImageURL;
    //Button
    private Button btnImageLoad;
    //ImageView (gambar)
    private ImageView lblImage;
    //ProgressDialog(Loading)
    private ProgressDialog loading;
    //Penunjuk jika gambar sudah di load.
    private int ImageIsLoaded=0;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_search_image);

        //Inisialisasi Komponen View
        txtImageURL=(EditText)findViewById(R.id.searchimage);
        btnImageLoad=(Button)findViewById(R.id.btnSearch);
        lblImage=(ImageView)findViewById(R.id.imgview);

        btnImageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGambar();
            }
        });
        if(saved!=null){
            //Cek apakah didalm bundle nilai kunci sebagai penunjuk data sudah di load sebelumnya
            if(saved.getInt("IMAGE_IS_LOADED")!=0 && !saved.getString("EXTRA_TEXT_URL").isEmpty()){
                txtImageURL.setText(""+saved.getString("EXTRA_TEXT_URL"));
                showGambar();
            }
        }
    }
    /*Method yg digunakan untuk melakukan penyimpanan pada suatu bundle*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Simpan untuk URL TEXT agar input tidak dilakukan kembali
        outState.putString("EXTRA_TEXT_URL",txtImageURL.getText().toString());
        //Simpan untuk respon pada savedinstancestate
        outState.putInt("IMAGE_IS_LOADED",ImageIsLoaded);
    }

    public void showGambar() {
        //Mengambil nilai input (String)
        String ImgUrl = txtImageURL.getText().toString();
        //Aksi Asynctask untuk melakukan pencarian/load gambar dari internet
        new LoadImageTask().execute(ImgUrl);
    }
    /*Asynctask ini mendeklrasai dengan parameter String Integer,Bitmap
    String untuk Alamat gambar
    integer presentase proses
    Bitmap untuk gambar

    * */
    public class LoadImageTask extends AsyncTask <String, Integer, Bitmap>{
        /*Method2 dibawah ini digunakan untuk memunculkan progress
        saat akan memunculkan gambar
        * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading=new ProgressDialog(SearchImage.this);
            loading.setMessage("Tunggu Sebentar ...");
            loading.setMax(100);
            loading.incrementProgressBy(1);
            loading.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e("doInBackground() - ", e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {
                Thread.sleep(1000);
                loading.setMessage("Memuat...");
                loading.incrementProgressBy(values[0]);
                loading.setProgress(values[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //Set ImageView
            lblImage.setImageBitmap(bitmap);
            //Parameter Sudah di load
            ImageIsLoaded=1;
            //Menghilangkan Loading(ProgressBar)
            loading.dismiss();
        }
    }
}
