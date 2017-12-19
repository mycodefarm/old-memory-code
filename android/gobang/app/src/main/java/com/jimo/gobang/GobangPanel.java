package com.jimo.gobang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimo on 2016/4/30.
 */
public class GobangPanel extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;//棋盘的线条格子数，可以自己设置
    private int MAX_COUNT_IN_LINE = 5;//五子一线

    private Paint mPaint = null;

    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;//黑棋

    private float ratioPieceOfLineHeight = 3*1.0f/4;//有一个比例

    private boolean mIsWhite = true;//当前是白棋下
    private ArrayList<Point> mWhiteList = new ArrayList<>();
    private ArrayList<Point> mBlackList = new ArrayList<>();

    private boolean mIsOver;//游戏结束
    private boolean mIsWhiteWinner;//是否白子赢了

    public GobangPanel(Context context, AttributeSet attrs) {
        super(context, attrs);

       // setBackgroundColor(0x44ff0000);
        init();
    }

    private void init() {

        mPaint = new Paint();

        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);//画线
        mPaint.setStrokeWidth(4.0f);

        //引入棋子资源
        mWhitePiece = BitmapFactory.decodeResource(getResources(),R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(),R.drawable.stone_b1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize,heightSize);

        if(widthMode==MeasureSpec.UNSPECIFIED){
            width = heightSize;
        }else if(heightMode==MeasureSpec.UNSPECIFIED){
            width = widthSize;
        }

        setMeasuredDimension(width,width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        mPanelWidth = w;
        mLineHeight = mPanelWidth*1.0f/MAX_LINE;

        //当屏幕宽度变化，重新设置棋子大小为lineHeight的3/4
        int pieceWidth = (int)(mLineHeight*ratioPieceOfLineHeight);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece,pieceWidth,pieceWidth,false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece,pieceWidth,pieceWidth,false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(mIsOver){//游戏结束
            return false;
        }
        int action = event.getAction();//表明手势交给我们操作
        if(action==MotionEvent.ACTION_UP){

            int x = (int) event.getX();
            int y = (int) event.getY();

            Point p = getValidPoint(x,y);

            //有棋子的地方不能再点了
            if(mWhiteList.contains(p)||mBlackList.contains(p)){
               return false;
            }

            if(mIsWhite){
                mWhiteList.add(p);
            }else{
                mBlackList.add(p);
            }
            invalidate();
            mIsWhite = !mIsWhite;
        }

        return true;
    }

    //取得合法的点，即0，0，0，1之类的
    private Point getValidPoint(int x, int y) {

        return new Point((int)(x/mLineHeight),(int)(y/mLineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBoard(canvas);

        drawPieces(canvas);

        checkGameOver();
    }

    private void checkGameOver() {

        boolean whiteWin = checkFiveInLine(mWhiteList);
        boolean blackWin = checkFiveInLine(mBlackList);

        if(whiteWin || blackWin){
            mIsOver = true;
            mIsWhiteWinner = whiteWin;

            String text = mIsWhiteWinner?"白棋胜了":"黑棋胜了";
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveInLine(List<Point> points) {

        for(Point p:points){
            int x = p.x;
            int y = p.y;
            
            boolean win = checkHorizontal(x,y,points);
            if(win){
                return true;
            }
            win = checkVertical(x,y,points);
            if(win){
                return true;
            }
            win = checkLeftDiagonal(x,y,points);
            if(win){
                return true;
            }
            win = checkRightDigonal(x,y,points);
            if(win){
                return true;
            }
        }
        return false;
    }

    private boolean checkHorizontal(int x, int y, List<Point> points) {
        
        int count = 1;
        //横向左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x-i,y))){
                count++;
            }else{
                break;
            }
        }
        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        //横向右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x+i,y))){
                count++;
            }else{
                break;
            }
        }
        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        return false;
    }

    private boolean checkVertical(int x, int y, List<Point> points) {

        int count = 1;
        //横向左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x,y+i))){
                count++;
            }else{
                break;
            }
        }
        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        //横向右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x,y-i))){
                count++;
            }else{
                break;
            }
        }
        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        return false;
    }

    //左斜线
    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {

        int count = 1;
        //左下
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x-i,y+i))){
                count++;
            }else{
                break;
            }
        }
        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        //右上
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x+i,y-i))){
                count++;
            }else{
                break;
            }
        }
        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        return false;
    }

    private boolean checkRightDigonal(int x, int y, List<Point> points) {

        int count = 1;
        //横向左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x-i,y-i))){
                count++;
            }else{
                break;
            }
        }
        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        //横向右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if(points.contains(new Point(x+i,y+i))){
                count++;
            }else{
                break;
            }
        }
        if(count==MAX_COUNT_IN_LINE){
            return true;
        }
        return false;
    }

    private void drawPieces(Canvas canvas) {

        for (int i = 0; i < mWhiteList.size(); i++) {
            Point whitePoint = mWhiteList.get(i);

            //因为point例存的是整数的点
            canvas.drawBitmap(mWhitePiece,(whitePoint.x+(1-ratioPieceOfLineHeight)/2)*mLineHeight,
                    (whitePoint.y+(1-ratioPieceOfLineHeight)/2)*mLineHeight,null);
        }

        for (int i = 0; i < mBlackList.size(); i++) {
            Point mBlackPoint = mBlackList.get(i);

            canvas.drawBitmap(mBlackPiece,(mBlackPoint.x+(1-ratioPieceOfLineHeight)/2)*mLineHeight,
                    (mBlackPoint.y+(1-ratioPieceOfLineHeight)/2)*mLineHeight,null);
        }
    }

    private void drawBoard(Canvas canvas) {

        int w = mPanelWidth;
        float lineHieght = mLineHeight;

        for(int i=0;i<MAX_LINE;i++){
            int startX = (int)(lineHieght/2);
            int endX = (int)(w - lineHieght/2);
            int y = (int)(startX + i*lineHieght);

            canvas.drawLine(startX,y,endX,y,mPaint);//画横线
        }

        for(int i=0;i<MAX_LINE;i++){
            int startX = (int)(lineHieght/2+i*lineHieght);
            int endY = (int)(w - lineHieght/2);
            int y = (int)(lineHieght/2);
            canvas.drawLine(startX,y,startX,endY,mPaint);//画竖线
        }
    }

    /*
存储View

千万不要忘了给view写id
 */
    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_LIST = "instance_white_list";
    private static final String INSTANCE_BLACK_LIST = "instance_black_list";

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE,super.onSaveInstanceState());//不要忘了
        bundle.putBoolean(INSTANCE_GAME_OVER,mIsOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_LIST,mWhiteList);
        bundle.putParcelableArrayList(INSTANCE_BLACK_LIST,mBlackList);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if(state instanceof Bundle){
            Bundle bundle = (Bundle)state;
            mIsOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteList = bundle.getParcelableArrayList(INSTANCE_WHITE_LIST);
            mBlackList = bundle.getParcelableArrayList(INSTANCE_BLACK_LIST);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));//不要忘了

            return ;
        }
        super.onRestoreInstanceState(state);
    }

    /*
    再来一局
     */
    public void start(){
        mWhiteList.clear();
        mBlackList.clear();
        mIsOver = false;
        mIsWhiteWinner = false;
        invalidate();
    }
}










