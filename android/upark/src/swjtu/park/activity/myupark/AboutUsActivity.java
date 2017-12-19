package swjtu.park.activity.myupark;

import swjtu.park.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
	
	private TextView tv_top;
	private ImageView im_top;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activty_aboutus);
		
		tv_top = (TextView) findViewById(R.id.tv_top);
		im_top = (ImageView) findViewById(R.id.im_top);
		
		tv_top.setText("关于我们");
		
		im_top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
}
