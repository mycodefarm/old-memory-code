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
        
        //设置亮度的动画，实现渐变显示，从0.1到1.0(全亮)
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(1100);         //设置渐变时间2s
        view.startAnimation(aa);          //开始一个动画
         
        setContentView(view);
         
        //设置动画监听器，当动画结束的时候，启动新的Activity
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
        		//取得相应的值，如果没有该值，说明还未写入，用true作为默认值
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
    //屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return false;
	}
}
