package com.example.p14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseAdapter_addpic extends BaseAdapter {
	 
    private P14 mainActivity;
    private LayoutInflater myInflater;
    private ArrayList<HashMap<String, String>> list = null;
    private ViewTag viewTag;

    public BaseAdapter_addpic(P14 context, ArrayList<HashMap<String, String>> list) {
    	//取得 MainActivity 實體
        this.mainActivity = context;
        this.myInflater = LayoutInflater.from(context);
        this.list = list;
    }
 
    public int getCount() {
        return list.size();
    }
 
    public Object getItem(int position) {
        return list.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (convertView == null) {
        	 // 取得listItem容器 view
            convertView = myInflater.inflate(R.layout.list_view, null);
 
         // 建構listItem內容view
            viewTag = new ViewTag(
                    (ImageView) convertView.findViewById(R.id.imageView1),
                    (Button) convertView.findViewById(R.id.button1));
 
            // 設置容器內容
            convertView.setTag(viewTag);
 
        } else {
            viewTag = (ViewTag) convertView.getTag();
        }
 
      //取得縮圖檔名，設定 ImageView
        String fileName = list.get(position).get("filename");
        Bitmap bm = BitmapFactory.decodeFile(fileName);
        viewTag.iv1.setImageBitmap(bm);
 
        //設定按鈕監聽事件及傳入 MainActivity 實體
        viewTag.btn1.setOnClickListener(new ItemButton_Click(this.mainActivity, position));
 
        return convertView;
    }
 
    public class ViewTag {
        ImageView iv1;
        TextView btn1;
 
        public ViewTag(ImageView imageView1, Button button1) {
            this.iv1 = imageView1;
            this.btn1 = button1;
        }
    }
 
    //�ۭq���s��ť�ƥ�
    class ItemButton_Click implements OnClickListener {
        private int position;
        private Teat2 mainActivity;
 
        ItemButton_Click(Teat2 context, int pos) {
            this.mainActivity = context;
            position = pos;
        }
 
        public void onClick(View v) {
            this.mainActivity.removeItem(position);
        }
    }
}