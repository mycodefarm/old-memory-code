package swjtu.park.activity;

import swjtu.park.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProtocolActivity extends Activity implements OnClickListener{
	
	private Button btn_ok,btn_cancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_protocol);
		
		btn_ok = (Button)findViewById(R.id.btn_proto_ok);
		btn_cancel = (Button)findViewById(R.id.btn_proto_cancel);
		
		btn_cancel.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_proto_ok:
			//先标记不是第一次进入了
	    	SharedPreferences pref = getSharedPreferences("park", MODE_PRIVATE);
	    	Editor editor = pref.edit();
	    	editor.putBoolean("isFirstIn", false);
	    	editor.commit();
	    	//
	    	Intent intent = new Intent(this,MainActivity.class);
	        startActivity(intent);
	        finish();
			break;
		case R.id.btn_proto_cancel:
			finish();
			break;
		default:break;
		}
	}
	
	
}
