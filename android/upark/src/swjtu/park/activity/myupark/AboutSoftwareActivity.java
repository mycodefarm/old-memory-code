package swjtu.park.activity.myupark;

import swjtu.park.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutSoftwareActivity extends Activity {

	private TextView tv_top;
	private ImageView im_top;
	private Button btn_updata, btn_clear, btn_share, btn_aboutus;
	private ButtonListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_aboutsoftware);
		
		listener = new ButtonListener();
		FindView();
		SetOnClickListener();
		tv_top.setText("关于软件");
	}

	private void FindView() {
		tv_top = (TextView) findViewById(R.id.tv_top);
		im_top = (ImageView) findViewById(R.id.im_top);
		
		btn_updata = (Button) findViewById(R.id.btn_updata);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_aboutus = (Button) findViewById(R.id.btn_aboutus);
	}

	private void SetOnClickListener() {	
		im_top.setOnClickListener(listener);
		btn_updata.setOnClickListener(listener);
		btn_clear.setOnClickListener(listener);
		btn_share.setOnClickListener(listener);
		btn_aboutus.setOnClickListener(listener);
	}

	// 定义内部类
	private class ButtonListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_updata:
				break;
			case R.id.btn_clear:
				ShowDialog(AboutSoftwareActivity.this);
				break;
			case R.id.btn_share:
				Share();
				break;
			case R.id.btn_aboutus:
				Intent intent = new Intent(AboutSoftwareActivity.this,
						AboutUsActivity.class);
				startActivity(intent);
				break;
			case R.id.im_top:
				finish();
				break;
			}
		}
	}

	private void ShowDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("提示");
		builder.setMessage("确定要清除所有缓存?");
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				DataCleanManager.cleanInternalCache(getBaseContext());
				Toast.makeText(getBaseContext(), "已清除缓存", Toast.LENGTH_SHORT)
						.show();
			}
		});
		builder.show();
	}

	private void Share() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
		intent.putExtra(Intent.EXTRA_TEXT,
				"I have successfully share my message through my app (分享自UPark)");

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));
	}
}
