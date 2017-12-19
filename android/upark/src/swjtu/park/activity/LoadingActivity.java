package swjtu.park.activity;

import com.baidu.mapapi.SDKInitializer;

import swjtu.park.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class LoadingActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		final View view = View.inflate(this, R.layout.activity_loading, null);
        
        //�������ȵĶ�����ʵ�ֽ�����ʾ����0.1��1.0(ȫ��)
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(1100);         //���ý���ʱ��2s
        view.startAnimation(aa);          //��ʼһ������
         
        setContentView(view);
         
        //���ö�����������������������ʱ�������µ�Activity
        aa.setAnimationListener(new AnimationListener() {
             
            @Override
            public void onAnimationStart(Animation animation) {
            }
             
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
             
            @Override
            public void onAnimationEnd(Animation animation) {
            	Boolean isFirstIn = false;
        		SharedPreferences pref = getSharedPreferences("park", MODE_PRIVATE);
        		//ȡ����Ӧ��ֵ�����û�и�ֵ��˵����δд�룬��true��ΪĬ��ֵ
        		isFirstIn = pref.getBoolean("isFirstIn", true);
            	if(isFirstIn){
            		startProtocolActivity();
            	}else{
            		startMainActivity();
            	}
            }
        });
    }
 
    private void startMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
 
    private void startProtocolActivity() {
        Intent intent = new Intent(this,ProtocolActivity.class);
        startActivity(intent);
        finish();
    }
    //���η��ؼ�
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return false;
	}
}
