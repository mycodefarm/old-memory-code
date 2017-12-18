// MyCDC.cpp: implementation of the MyCDC class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "CG2014112135_2.h"
#include "MyCDC.h"
#include <vector>
#include <list>
#include <iterator>

using namespace std;

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MyCDC::MyCDC(CWnd* pWnd) : CClientDC(pWnd)
{

}

MyCDC::~MyCDC()
{

}

void MyCDC::swap(int &x,int &y){
	int t = x;
	x = y;
	y = t;
}

void MyCDC::drawCirclePoint(int x,int y,int xc,int yc,COLORREF color){
	SetPixel(x+xc,yc-y,color);
	SetPixel(x+xc,yc+y,color);
	SetPixel(xc-x,yc-y,color);
	SetPixel(xc-x,yc+y,color);
	SetPixel(xc+y,yc-x,color);
	SetPixel(xc+y,yc+x,color);
	SetPixel(xc-y,yc-x,color);
	SetPixel(xc-y,yc+x,color);
}

void MyCDC::LineDDA(int x1, int y1, int x2, int y2, COLORREF color)
{
	int dx = x2-x1;
	int dy = y2-y1;
	int steps,k;
	float xIncrement,yIncrement,x=x1,y=y1;

	if(abs(dx)>abs(dy)){
		steps = abs(dx);
	}else{
		steps = abs(dy);
	}

	xIncrement = dx/(float)steps;
	yIncrement = dy/(float)steps;

	SetPixel(ROUND(x),ROUND(y),color);

	for(k=0;k<steps;k++){
		x+=xIncrement;
		y+=yIncrement;
		SetPixel(ROUND(x),ROUND(y),color);
	}
}


void MyCDC::LineBres(int x1,int y1,int x2,int y2,COLORREF color){
	/* int dx, dy, e ,s;
	 float m;
      int x, y;
      if(x1>x2){
		swap(x1,x2);
        swap(y1,y2);
	  }
	  x = x1,y = y1;
      dx = x2 - x1;
      dy = y2 - y1;

      m =  abs((float)dy/dx);//斜率

	  if(dy>=0){
          s = 1;
	  }else{
          s = -1;
	  }
      if(m>=0&&m<=1)
      {
            dy = abs(dy);
            e = 2 * dy - dx;
            SetPixel(x, y,color);
            while(x < x2)
            {
                  x = x + 1;
                  if(e < 0)
                  {
                        e = e + 2 * dy;
                  }
                  else
                  {
                        y = y + s;
                        e = e + 2 * (dy - dx);
                  }
                  SetPixel(x, y, color);
            }
      }else 
      {
            dx = abs(dx);
            dy = abs(dy);
            if(m<1){
                  swap(dx,dy);
            }
            e = 2*dx-2*dy;
			SetPixel(x, y, color);
            while(x<x2)
            {
                  y+=s;
                  if(e<0)
                  {
                        e += 2*dx;
                  }else
                  {
                        x++;
                        e += 2*(dx-dy);
                  }
				  SetPixel(x, y, color);
            }
      }*/
	
	int dx = abs(x2-x1);
      int dy = abs(y2-y1);
      int m = 0;
      if(dx<dy){//|斜率|>=1
            m = 1;
            swap(x1,y1);
            swap(x2,y2);
            swap(dx,dy);
      }
	  if(x1>x2){
		swap(x1,x2);
		swap(y1,y2);
	  }
      int s1 = (x2-x1)>0?1:-1;
      int s2 = (y2-y1)>0?1:-1;
      int x = x1,y = y1;
      int d1 = 2*dy,d2 = 2*(dy-dx),e = 2*dy-dx;
      if(m==1){
            while(x<x2){
                  if(e<0){
                        e+=d1;
                  }else{
                        y+=s2;
                        e+=d2;
                  }
                  SetPixel(y,x,color);
                  x+=s1;
            }
      }else{
            while(x<x2){
                  if(e<0){
                        e+=d1;
                  }else{
                        y+=s2;
                        e+=d2;
                  }
                  SetPixel(x,y,color);
                  x+=s1;
            }
      }
	
}


void MyCDC::LineMiddle(int x1,int y1,int x2,int y2,COLORREF color){
	/*
	float k = 1.0*(y2 - y1) / (x2 - x1); //斜率
	int flag = 0;  //是否沿y = x 翻转
	if (k > 1 || k < -1)
	{
		flag = 1;
		swap(x1,y1);
		swap(x2,y2);
		k = 1.0*(y2 - y1) / (x2 - x1);
	}
	float d = 0.5 - k; //初始值
	if (x1 > x2)
	{
		swap(x1,x2);
		swap(y1,y2);
	}
	while (x1 < x2)//主位移，每次都像素+1
	{
		if (k > 0 && d < 0){  //正向
				++y1, ++d;
		}
		else if (k < 0 && d > 0){//负向
				--y1, --d;
		}
		d -= k;
		++x1;
		if (flag){
			SetPixel(y1, x1, color); //翻转像素点
		}
		else{
			SetPixel(x1, y1, color); 
		}
	}
	*/
	int a, b, d1, d2, d, x, y;
    float m;
    if (x2 < x1)
    {
        swap(x1,x2);
        swap(y1,y2);
    }
    a = y1 - y2, b = x2 - x1;
    if (b == 0)//vertical
    {
        m = -1 * a * 100;
    }
    else
    {
        m = (float)a / (x1 - x2);//斜率
        
    }
	x = x1, y = y1;
    SetPixel(x, y, color);
    if (m >= 0 && m <= 1)
    {
        d = 2 * a + b;
        d1 = 2 * a, d2 = 2 * (a + b);
        while (x < x2)
        {
            if (d <= 0)
            {
                x++, y++, d += d2;
            }
            else
            {
                x++, d += d1;
            }
            SetPixel(x, y, color);
        }
    }
    else if (m <= 0 && m >= -1)
    {
        d = 2 * a - b;
        d1 = 2 * a - 2 * b, d2 = 2 * a;
        while (x < x2)
        {
            if (d > 0)
            {
                x++, y--, d += d1;
            }
            else
            {
                x++, d += d2;
            }
            SetPixel(x, y, color);
        }
    }
    else if (m > 1)
    {
        d = a + 2 * b;
        d1 = 2 * (a + b), d2 = 2 * b;
        while (y < y2)
        {
            if (d > 0)
            {
                x++, y++, d += d1;
            }
            else
            {
                y++, d += d2;
            }
            SetPixel(x, y, color);
        }
    }
    else
    {
        d = a - 2 * b;
        d1 = -2 * b, d2 = 2 * (a - b);
        while (y > y2)
        {
            if (d <= 0)
            {
                x++, y--, d += d2;
            }
            else
            {
                y--, d += d1;
            }
            SetPixel(x, y, color);
        }
    }
}

 //中点画圆算法(xc,yc)为圆中心点坐标,r为圆半径color为绘制颜色 
 void MyCDC::CircleMidP(int xc,int yc,int r,COLORREF color){
	int d = 1-r;
	int x = 0,y=r;
	drawCirclePoint(x,y,xc,yc,color);
	while(x<y){
		if(d<0){
			d = d+2*x+3;
		}else{
			d = d+2*(x-y)+5;
			y--;
		}
		x++;
		drawCirclePoint(x,y,xc,yc,color);
	}
 }

 //Bresenham画圆算法(xc,yc)为圆中心点坐标,r为圆半径color为绘制颜色 
 void MyCDC::CircleBres(int xc, int yc, int r, COLORREF color){
	int x,y,d1,d2,direction,d;
	x = 0,y=r,d=2*(1-r);
	while(y>=0){
		SetPixel(xc+x,yc+y,color);
		SetPixel(xc-x,yc+y,color);
		SetPixel(xc-x,yc-y,color);
		SetPixel(xc+x,yc-y,color);
		if(d<0){
			d1 = 2*(d+y)-1;
			if(d1<=0){
				direction = 1;
			}else{
				direction = 2;
			}
		}else if(d>0){
			d2 = 2*(d-x)-1;
			if(d2<=0){
				direction = 2;
			}else{
				direction = 3;
			}
		}else{
			direction = 3;	
		}
		switch(direction){
		case 1: x++;d+=2*x+1;break;
		case 2: x++;y--;d+=2*(x-y+1);break;
		case 3: y--;d+=-2*y+1;break;
		}
	}
 }

 void MyCDC::GetYmm(POINT*ps,int &ymin,int &ymax,int n){
	int min = 100000000,max = 0;
	for(int i=0;i<n;i++){
		if(ps[i].y>max){
			max = ps[i].y;
		}
		if(ps[i].y<min){
			min = ps[i].y;
		}
	}
	ymin = min;
	ymax = max;
 }

 //扫描线多边形填充算法(nCount为多边形顶点数，lpPoints为多边形顶点坐标，color为填充颜色 
void MyCDC::ScanFill(POINT *pts,int cnt,COLORREF color){
	
	int ymin=0,ymax = 0;
	GetYmm(pts,ymin,ymax,cnt);
	vector<list<MET> > NET(ymax-ymin+1);
	//init NET
	int i = 0;
	MET e;
	for(i=0;i<cnt;i++){
		POINT ps = pts[i],pe=pts[(i+1)%cnt],pss=pts[(i-1+cnt)%cnt],pee=pts[(i+2)%cnt];
		
		if(pe.y!=ps.y){
			e.dx = float(pe.x-ps.x)/float(pe.y-ps.y);
			if(pe.y>ps.y){
				e.x0 = ps.x;
				if(pee.y>=pe.y){
					e.ymax = pe.y-1;
				}else{
					e.ymax = pe.y;
				}
				NET[ps.y-ymin].push_front(e);
			}else{
				e.x0 = pe.x;
				if(pss.y>=ps.y){
					e.ymax = ps.y - 1;
				}else{
					e.ymax = ps.y;
				}
				NET[pe.y-ymin].push_front(e);
			}
		}
	}	

	//AET
	list<MET> AET;
	for(int y=0;y<=ymax-ymin;y++){
		//delete i>=ymax
		list<MET>::iterator it;
		for(it=AET.begin();it!=AET.end();){
			if((*it).ymax<=y+ymin-1){
				AET.erase(it++);
			}else{
				(*it).x0+=(*it).dx;
				it++;
			}
		}
		//insert NET[y]
		if(!NET[y].empty()){
			//insert sort insert
			for(it=NET[y].begin();it!=NET[y].end();it++){
				AET.push_back(*it);
			}			
		}
		AET.sort();
		//set color
		list<MET>::iterator itn;
		for(it=AET.begin();it!=AET.end();){
			itn = it;
			itn++;
			if(itn!=AET.end()){
				for(int c=(*it).x0;c<(*itn).x0;c++){
					SetPixel(c,y+ymin,color);
				}
			}
			it++;
			if(it!=AET.end()){
				it++;
			}
		}
	}
}

//边界填充算法(x,y)为区域内部点坐标，fcolor为填充颜色，bcolor为边界颜色 //4-连通区域边界填充算法 
void MyCDC::BoundaryFill4(int x,int y,COLORREF fcolor,COLORREF bcolor){
	COLORREF current = GetPixel(x,y);
	if((current!=bcolor)&&(current!=fcolor)){
		SetPixel(x,y,fcolor);
		BoundaryFill4(x+1,y,fcolor,bcolor);
		BoundaryFill4(x-1,y,fcolor,bcolor);
		BoundaryFill4(x,y+1,fcolor,bcolor);
		BoundaryFill4(x,y-1,fcolor,bcolor);
	}
}


 //泛滥填充算法(x,y)为区域内部点坐标，fcolor为填充颜色，ocolor为被替换颜色 //4-连通区域泛滥填充算法 
void MyCDC::FloodFill4(int x,int y,COLORREF fcolor,COLORREF ocolor){
	COLORREF current = GetPixel(x,y);
	if(current==ocolor){
		SetPixel(x,y,fcolor);
		FloodFill4(x-1,y,fcolor,ocolor);
		FloodFill4(x+1,y,fcolor,ocolor);
		FloodFill4(x,y+1,fcolor,ocolor);
		FloodFill4(x,y-1,fcolor,ocolor);		
	}
}

void MyCDC::EllipsePlot(int xc, int yc,int x,int y,COLORREF color){
	SetPixel(xc+x,yc-y,color);
	SetPixel(xc+x,yc+y,color);
	SetPixel(xc-x,yc-y,color);
	SetPixel(xc-x,yc+y,color);
}
//中点椭圆算法(xc,yc)为椭圆中心点坐标,a、b为椭圆两半轴长度,color为绘制颜色 
void MyCDC::EllipseMidP(int xc,int yc,int a,int b,COLORREF color){
	 double sqa = a * a;
     double sqb = b * b;
 
     double d = sqb + sqa * (-b + 0.25);
     int x = 0;
     int y = b;
     EllipsePlot(xc, yc, x, y,color);
     while( sqb * (x + 1) < sqa * (y - 0.5)){    
         if (d < 0){
             d += sqb * (2 * x + 3);
         }
         else{ 
             d += (sqb * (2 * x + 3) + sqa * (-2 * y + 2));
             y--;   
         }
         x++; 
         EllipsePlot(xc, yc, x, y,color);
     }
     d = (b * (x + 0.5)) * 2 + (a * (y - 1)) * 2 - (a * b) * 2;
     while(y > 0){ 
         if (d < 0){
             d += sqb * (2 * x + 2) + sqa * (-2 * y + 3);
             x++; 
         }
         else{
             d += sqa * (-2 * y + 3); 
         }
         y--;
         EllipsePlot(xc, yc, x, y,color);
     }
}