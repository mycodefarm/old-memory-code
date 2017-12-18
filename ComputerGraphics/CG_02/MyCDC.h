// MyCDC.h: interface for the MyCDC class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MYCDC_H__82860363_910F_4DC9_B91D_1EF1D038DC98__INCLUDED_)
#define AFX_MYCDC_H__82860363_910F_4DC9_B91D_1EF1D038DC98__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
#define ROUND(a) ((int)(a+0.5))
class MET{
public:
	float x0;
	float dx;
	int ymax;

	bool operator<(const MET &t1) const{ 
        return (x0 < t1.x0);
    }
};

class MyCDC : public CClientDC  
{
public:
	MyCDC(CWnd*);
	virtual ~MyCDC();

public: 
//DDA画线算法(x1,y1)(x2,y2)为线段两端点坐标,color为绘制颜色
 void LineDDA(int x1,int y1,int x2,int y2,COLORREF color);

 //Bresenham画线算法(x1,y1)(x2,y2)为线段两端点坐标,color为绘制颜色 
void LineBres(int x1,int y1,int x2,int y2,COLORREF color);

//中电画线算法
void LineMiddle(int x1,int y1,int x2,int y2,COLORREF color);

 //Bresenham画圆算法(xc,yc)为圆中心点坐标,r为圆半径color为绘制颜色 
void CircleBres(int xc, int yc, int r, COLORREF color);

 //中点画圆算法(xc,yc)为圆中心点坐标,r为圆半径color为绘制颜色 
void CircleMidP(int xc,int yc,int r,COLORREF color);

 //中点椭圆算法(xc,yc)为椭圆中心点坐标,rx、ry为椭圆两半轴长度,color为绘制颜色 
void EllipseMidP(int xc,int yc,int rx,int ry,COLORREF color);

 //扫描线多边形填充算法(nCount为多边形顶点数，lpPoints为多边形顶点坐标，color为填充颜色 
void ScanFill(const LPPOINT pts,int cnt,COLORREF color);

 //边界填充算法(x,y)为区域内部点坐标，fcolor为填充颜色，bcolor为边界颜色 //4-连通区域边界填充算法 
void BoundaryFill4(int x,int y,COLORREF fcolor,COLORREF bcolor);

 //泛滥填充算法(x,y)为区域内部点坐标，fcolor为填充颜色，ocolor为被替换颜色 //4-连通区域泛滥填充算法 
void FloodFill4(int x,int y,COLORREF fcolor,COLORREF ocolor);

 //Cohen-Sutherland线段裁剪算法
// void ClipLineCS(POINT2D& p1,POINT2D& p2,POINT& winMin,POINT& winMax);

 //梁友栋-Barsky线段裁剪算法 
//void ClipLineLB(POINT2D& p1,POINT2D& p2,POINT& winMin,POINT& winMax);

 //
private:
	void swap(int &x,int &y);
	void drawCirclePoint(int x,int y,int xc,int yc,COLORREF color);
	void GetYmm(POINT*ps,int &ymin,int &ymax,int n);
	void EllipsePlot(int xc, int yc,int x,int y,COLORREF color);
 /*void insertEdge(LPEDGE list, LPEDGE edge); 
int  yNext(int k,int cnt, LPPOINT pts); 
void makeEdgeRec(POINT& lower,POINT& upper,int yComp,LPEDGE edge,LPEDGE* edges); 
void buildEdgeList(int cnt,LPPOINT pts,LPEDGE* edges); 
void buildActiveList(int scan,LPEDGE active,LPEDGE* edges); 
void fillScan(int scan,LPEDGE active,COLORREF color); 
void deleteAfter(LPEDGE q); 
void updateActiveList(int scan,LPEDGE active); 
void resortActiveList(LPEDGE active);
 bool clipTest(double p,double q,double *u1,double* u2); */
};

#endif // !defined(AFX_MYCDC_H__82860363_910F_4DC9_B91D_1EF1D038DC98__INCLUDED_)
