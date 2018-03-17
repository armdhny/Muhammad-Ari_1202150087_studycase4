package com.example.ari.muhammadari_1202150087_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ShowName extends AppCompatActivity {
    //Deklarasi Komponen yang akan digunakan
    private ListView mListView; //LISTVIEW
    private ProgressBar mProgressBar; //PROGRESS BAR
    private String [] mUsers= {
            "Adha","Mamang","Ari","Harri","Somad","Bobi",
            "Toti","Hylda","Salman"

    }; //ARRAY
    private AddItemToListView mAddItemToListView; //CLASS LISTVIEW
    private Button mStartAsyncTask; //BUTTON


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_name);

        //Inisialisasi Komponen View
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.listView);
        mStartAsyncTask = (Button) findViewById(R.id.button_startAsyncTask);

        /** membuat progressbar terlihat saat aplikasi berjalan
         */
        mListView.setVisibility(View.GONE);

        /**
         * menyiapkan adapter
         */
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                new ArrayList<String>()));


        /**
         * mulai tombol asynctask setelah diklik
         */
        mStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //proses adapter dengan asyncTask
                mAddItemToListView = new AddItemToListView();
                mAddItemToListView.execute();
            }
        });
    }

    /**
     * kelas bagian untuk proses asyntask
     */
    public class AddItemToListView  extends AsyncTask<Void, String, Void> {

        //inisialisasi Array Adapter dan ProgressDialog
        private ArrayAdapter<String> mAdapter;
        private int counter=1;
        ProgressDialog mProgressDialog = new ProgressDialog(ShowName.this);


        //Pada aplikasi, langkah ini digunakan untuk memunculkan
        // progress bar beserta propertynya
        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) mListView.getAdapter(); //casting suggestion

            //Progress dialog yang akan muncul
            // saat proses asynctask berjalan
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setMessage("Tunggu Sebentar..");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgress(0);

            //ini akan menghandle cancel asynctask saat klik tombol cancel
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAddItemToListView.cancel(true);
                    mProgressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            mProgressDialog.show();
        }

        //method berfungsi saat telah menekan tombol asyntask
        //method ini digunakan untuk mengatur waktu pemuatan data array mUsers
        @Override
        protected Void doInBackground(Void... params) {
            for (String item : mUsers){
                publishProgress(item);
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    mAddItemToListView.cancel(true);
                }
            }
            return null;
        }

        //Tahap ini digunakan untuk melakukan 'update UI' pada progress bar agar terlihat berjalan
        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);

            //penghitungan sudah sampai berapa data array dimuat
            Integer current_status = (int) ((counter/(float)mUsers.length)*100);
            mProgressBar.setProgress(current_status);

            //mengatur kemajuan hanya bekerja untuk loading horizontal
            mProgressDialog.setProgress(current_status);

            //untuk mengetahui berapa data yang telah terload
            mProgressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            //hide progresbar saat progress telah selesai
            mProgressBar.setVisibility(View.GONE);

            //Menghilangkan Progress Bar (Loading)
            mProgressDialog.dismiss();
            mListView.setVisibility(View.VISIBLE);
        }
    }
}
