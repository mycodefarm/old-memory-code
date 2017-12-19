package swjtu.park.activity.myupark;

import java.util.ArrayList;

import swjtu.park.R;
import android.R.color;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HelpAndFeedbackActivity extends Activity {

	private TextView tv_top;
	private ImageView im_top;
	
	private LinearLayout btn_questionListLayout = null;
	private AutoCompleteTextView actv_search;
	private Button btn_search, btn_feedback;
	private ImageView im_search;
	private String[] autoStrs = new String[] { "a", "abc", "abcd", "abcde"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_helpandfeedback);

		tv_top = (TextView) findViewById(R.id.tv_top);
		im_top = (ImageView) findViewById(R.id.im_top);
		
		actv_search = (AutoCompleteTextView) findViewById(R.id.actv_search);
		btn_search = (Button) findViewById(R.id.button_search);
		btn_feedback = (Button) findViewById(R.id.btn_feedback);
		im_search = (ImageView) findViewById(R.id.image_search);
	
		tv_top.setText("帮助与反馈");
		
		im_top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//连接服务器进行搜索
			}
		});
		
		btn_feedback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//意见反馈按钮
		    	Uri uri = Uri.parse("http://www.baidu.com"); 
		    	Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
		    	startActivity(intent);
			}
		});
		
		// new ArrayAdapter对象并将autoStr字符串数组传入actv中
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_dropdown_item_1line, autoStrs);
				actv_search.setAdapter(adapter);
		
		btn_questionListLayout = (LinearLayout) findViewById(R.id.btn_questionListLayout);
		
		generateBtnList(getBtnContentList());

	}

	/*
	 * 添加常见问题
	 */
	private ArrayList<String> getBtnContentList() {
		ArrayList<String> btnContentList = new ArrayList<String>();

		btnContentList.add("aa");
		btnContentList.add("bb");
		btnContentList.add("cc");
		btnContentList.add("dd");

		return btnContentList;
	}

	private void generateBtnList(ArrayList<String> btnContentList) {
		if (null == btnContentList) {
			return;
		}

		btn_questionListLayout.removeAllViews();
		int index = 0;
		for (String btnContent : btnContentList) {

			Button codeBtn = new Button(this);

			setBtnAttribute(codeBtn, btnContent, index, color.transparent,
					Color.BLACK, 24);
			btn_questionListLayout.addView(codeBtn);
			index++;
		}
	}

	private void setBtnAttribute(Button codeBtn, String btnContent, int id,
			int backGroundColor, int textColor, int textSize) {
		if (null == codeBtn) {
			return;
		}

		codeBtn.setBackgroundColor((backGroundColor >= 0) ? backGroundColor
				: Color.TRANSPARENT);
		codeBtn.setTextColor((textColor >= 0) ? textColor : Color.BLACK);
		codeBtn.setTextSize((textSize > 16) ? textSize : 24);
		codeBtn.setId(id);
		codeBtn.setText(btnContent);
		codeBtn.setGravity(Gravity.LEFT);
		codeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// btn click process
			}
		});

		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

		codeBtn.setLayoutParams(rlp);
	}
}
