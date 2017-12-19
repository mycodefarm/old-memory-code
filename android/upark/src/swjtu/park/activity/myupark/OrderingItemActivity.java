package swjtu.park.activity.myupark;

import swjtu.park.R;
import swjtu.park.ui.OderingItemExpandableAdapter;
import swjtu.park.ui.OderingItemExpandableListView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

/*
 * yyc修改
 */

public class OrderingItemActivity extends Activity {

	private TextView tv_top;
	private ImageView im_top;
	
	private OderingItemExpandableListView explistview;
	private String[][] childrenData = new String[3][3];
	private String[] groupData = new String[3];
	private int expandFlag = -1;// 控制列表的展开
	private OderingItemExpandableAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_orderinfo);
		initView();
		initData();
		explistview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				switch (groupPosition) {
				case 0:
					switch (childPosition) {
					case 0:
						Intent intent = new Intent(getBaseContext(),
								Odering.class);
						startActivity(intent);
						break;
					}
					break;
				case 1:
					switch (childPosition) {
					case 0:
						Intent intent = new Intent(getBaseContext(), Done.class);
						startActivity(intent);
					}
					break;
				case 2:
					break;
				}
				return false;
			}
		});
	}

	/**
	 * 初始化VIEW
	 */
	private void initView() {
		tv_top = (TextView) findViewById(R.id.tv_top);
		im_top = (ImageView) findViewById(R.id.im_top);
		
		explistview = (OderingItemExpandableListView) findViewById(R.id.explistview);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		groupData[0] = "已完成订单";
		groupData[1] = "预约中订单";
		groupData[2] = "推荐";

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				childrenData[i][j] = "订单" + (j + 1);
			}
		}
		
		tv_top.setText("订单详情");
		im_top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		// 设置悬浮头部VIEW
		explistview.setHeaderView(getLayoutInflater().inflate(
				R.layout.odering_item_group_head, explistview, false));
		adapter = new OderingItemExpandableAdapter(childrenData, groupData,
				getApplicationContext(), explistview);
		explistview.setAdapter(adapter);

		// 设置单个分组展开
		// explistview.setOnGroupClickListener(new GroupClickListener());
	}

	class GroupClickListener implements OnGroupClickListener {
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			if (expandFlag == -1) {
				// 展开被选的group
				explistview.expandGroup(groupPosition);
				// 设置被选中的group置于顶端
				explistview.setSelectedGroup(groupPosition);
				expandFlag = groupPosition;
			} else if (expandFlag == groupPosition) {
				explistview.collapseGroup(expandFlag);
				expandFlag = -1;
			} else {
				explistview.collapseGroup(expandFlag);
				// 展开被选的group
				explistview.expandGroup(groupPosition);
				// 设置被选中的group置于顶端
				explistview.setSelectedGroup(groupPosition);
				expandFlag = groupPosition;
			}
			return true;
		}
	}
}
