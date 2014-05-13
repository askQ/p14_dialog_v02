package com.example.p14;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class Teat2 extends Activity {
 
    private ArrayList<HashMap<String, String>> list;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_p14_new);
 
        list = new ArrayList<HashMap<String, String>>();
        BindData();
    }
 
    /** �j�w�C���� */
    private void BindData() {
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(new BaseAdapter_addpic(this, list));
    }
 
    /** �s�W�Ϥ� */
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        Intent destIntent = Intent.createChooser(intent, "����ɮ�");
        startActivityForResult(destIntent, 0);
    }
 
    /** ��ܹϤ���^�I�禡 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 
        super.onActivityResult(requestCode, resultCode, data);
 
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
 
            if (uri != null) {
                //���o��l���ɦW��
                String fileName = uri.getPath().substring(uri.getPath().lastIndexOf("/"));
 
                //SD Card �ت���Ƨ�
                String extStorage = Environment.getExternalStorageDirectory().getAbsolutePath() + "/thumbnail";
 
                File dir = new File(extStorage);
                if (!dir.exists())
                    dir.mkdirs();
 
                File file = new File(extStorage, fileName);
 
                try {
                    Bitmap bitmap = getBitmap(uri);
                    OutputStream outStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                } catch (Exception e) {
 
                }
 
                // �N�ɦW��ƥ[�J�C��
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("uri", uri.toString());
                item.put("filename", file.toString());
                list.add(item);
 
                BindData();
            }
        }
    }
 
    /** ���o�Y�� */
    public Bitmap getBitmap(Uri uri) {
        try {
            InputStream in = getContentResolver().openInputStream(uri);
 
            // �Ĥ@�� decode,�u���o�Ϥ����e,�٥����J�O����
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, opts);
            in.close();
 
            // ���o�ʺA�p���Y�Ϫ��e�� SampleSize (2������̨�)
            int sampleSize = computeSampleSize(opts, -1, 128 * 128);
 
            // �ĤG�� decode,���w���˼ƫ�,�����Y��
            in = getContentResolver().openInputStream(uri);
            opts = new BitmapFactory.Options();
            opts.inSampleSize = sampleSize;
 
            Bitmap bmp = BitmapFactory.decodeStream(in, null, opts);
            in.close();
 
            return bmp;
        } catch (Exception err) {
            return null;
        }
    }
 
    public static int computeSampleSize(BitmapFactory.Options options,
            int minSideLength, int maxNumOfPixels) {
 
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
 
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
 
        return roundedSize;
    }
 
    private static int computeInitialSampleSize(BitmapFactory.Options options,
            int minSideLength, int maxNumOfPixels) {
 
        double w = options.outWidth;
        double h = options.outHeight;
 
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
 
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
 
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
 
    /** �������� */
    public void removeItem(int position) {
        list.remove(position);
        BindData();
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.p14, menu);
        return true;
    }
}