package com.example.p14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Build;

public class P14 extends Activity {
	  private SharedPreferences Contacts;
	  private ListView contactList;
	  public static List<Map<String, String>> list = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_p14_new);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_p14_new);
		Button newContact = (Button)findViewById(R.id.button1);
		newContact.setOnClickListener(btnEditOnClickListener);
		Contacts=getSharedPreferences("contacts", Context.MODE_PRIVATE);
		contactList = (ListView)findViewById(R.id.listView1);
		//contactList.getBackground().setAlpha(100); //v.getBackground().setAlpha(100);//0~255透明度值 使對話窗背景透明
		getActionBar().setDisplayHomeAsUpEnabled(true);
		BindData();

    }
    /** 綁定列表資料 */
    private void BindData() {
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(new BaseAdapter_addpic(this, list));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.p14, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_p14_new, container, false);
            return rootView;
        }
    }
    
	//把聯絡人寫進永久檔案(Contacts.xml) shareperferences Contacts中, 再從shareperference讀檔案寫成一個新的Hashmap map, 然後塞進 List list中
	public void save(String name, String phone){
			
		Editor editor = Contacts.edit(); 
		editor.putString(name, name);
		editor.putString(phone, phone);
		editor.commit();
		
		Map<String,String> map = new HashMap<String,String>();
		  map.put("contactNames",Contacts.getString(name, "").toString());
		  map.put("contactPhones",Contacts.getString(phone, "").toString());
		  list.add(map);
	}
	
	//把List list整個傳進 ListView contactList中並顯示
		private void showContacts(List<Map<String, String>> list, ListView contactList){
			
			 SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.list_view,
					 new String[]{"contactNames","contactPhones"},
					 new int[] {R.id.textView1, R.id.textView2} );
			 contactList.setAdapter(adapter);
			
		}

		//"新增"按鈕
		Button.OnClickListener btnEditOnClickListener
		 = new Button.OnClickListener(){
		  @Override
		  public void onClick(View arg0) {
			  newContact();
		  }};
		  
		  
		  
		  
		  //顯示新增聯絡人對話窗
		 private void newContact(){		  
			  
			   final Dialog keyinContact = new Dialog(this);		
				keyinContact.setContentView(R.layout.dialog_new_contact); //自訂layout
				keyinContact.setTitle(R.string.new_contact);
			
				
				Button OK = (Button)keyinContact.findViewById(R.id.button1);
				Button NO = (Button)keyinContact.findViewById(R.id.button2);
			
				
				OK.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
									
						EditText name = (EditText)keyinContact.findViewById(R.id.editText1); 
						EditText cellphone = (EditText)keyinContact.findViewById(R.id.editText2);
						String contactName = name.getText().toString(); //拿資料
						String contactPhone = cellphone.getText().toString();
					
						save(contactName, contactPhone);
						showContacts(list, contactList);
						keyinContact.dismiss();	//使對話窗消失
						Toast.makeText(P14.this, "已新增" + contactName , Toast.LENGTH_SHORT)
						//.setGravity(Gravity.CENTER, 0, 0)//使出現在螢幕中央  有點問題
						.show(); //快顯通知新增了誰  用R.string.built會有問題還不知道為什麼
					}
				});
				NO.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						keyinContact.dismiss();
						Toast.makeText(P14.this, R.string.no, Toast.LENGTH_SHORT).show();
					}
				});		
				keyinContact.show();
			}	  

}
