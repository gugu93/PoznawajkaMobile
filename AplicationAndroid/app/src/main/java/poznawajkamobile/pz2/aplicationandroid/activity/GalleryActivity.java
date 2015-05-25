package poznawajkamobile.pz2.aplicationandroid.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.kml.poznawajkamobile.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import poznawajkamobile.pz2.aplicationandroid.models.GaleryModel;
import poznawajkamobile.pz2.aplicationandroid.utils.Utils;
import poznawajkamobile.pz2.aplicationandroid.utils.services.AvatarService;
import poznawajkamobile.pz2.aplicationandroid.utils.services.GalerryService;


public class GalleryActivity extends AbstractActivity {
    private GalerryService galleryService;
    private Boolean mBoundGalerry = false;
    private ServiceConnection mConnectionGallery = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            GalerryService.GalleryBinder binder = (GalerryService.GalleryBinder) service;
            galleryService = binder.getService();
            mBoundGalerry = true;
            galleryService.start(getPreferences().getString("login"));
            initGallery(galleryService.list);

        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBoundGalerry = false;
        }
    };

    Integer[] imageIDs = {
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo4,
            R.drawable.photo5
    };



    public void initGallery(ArrayList<GaleryModel> list){
        if (list != null){

        }else {
            Gallery gallery = (Gallery) findViewById(R.id.gallery1);
            gallery.setAdapter(new ImageAdapter(this));
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    ImageView imageView = (ImageView) findViewById(R.id.image1);
                    imageView.setImageResource(imageIDs[position]);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBoundGalerry) {
            unbindService(mConnectionGallery);
            mBoundGalerry = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        Intent intentGalerryService = new Intent(this, GalerryService.class);
        bindService(intentGalerryService, mConnectionGallery, Context.BIND_AUTO_CREATE);
    }

    private void openAlert(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GalleryActivity.this);

        alertDialogBuilder.setTitle("Informacja!");
        alertDialogBuilder.setMessage("Czy chcesz ustawi? zdj?cie jako profilowe");
        // set positive button: Yes message
        alertDialogBuilder.setNegativeButton("Tak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        alertDialogBuilder.setPositiveButton("Nie", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        public ImageAdapter(Context c)
        {
            context = c;
            TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
            itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
            a.recycle();
        }
        // returns the number of images
        public int getCount() {
            return imageIDs.length;
        }
        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }
        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }
        // returns an ImageView view
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageIDs[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(300, 300));
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }

}
