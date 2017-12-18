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
//DDA�����㷨(x1,y1)(x2,y2)Ϊ�߶����˵�����,colorΪ������ɫ
 void LineDDA(int x1,int y1,int x2,int y2,COLORREF color);

 //Bresenham�����㷨(x1,y1)(x2,y2)Ϊ�߶����˵�����,colorΪ������ɫ 
void LineBres(int x1,int y1,int x2,int y2,COLORREF color);

//�е续���㷨
void LineMiddle(int x1,int y1,int x2,int y2,COLORREF color);

 //Bresenham��Բ�㷨(xc,yc)ΪԲ���ĵ�����,rΪԲ�뾶colorΪ������ɫ 
void CircleBres(int xc, int yc, int r, COLORREF color);

 //�е㻭Բ�㷨(xc,yc)ΪԲ���ĵ�����,rΪԲ�뾶colorΪ������ɫ 
void CircleMidP(int xc,int yc,int r,COLORREF color);

 //�е���Բ�㷨(xc,yc)Ϊ��Բ���ĵ�����,rx��ryΪ��Բ�����᳤��,colorΪ������ɫ 
void EllipseMidP(int xc,int yc,int rx,int ry,COLORREF color);

 //ɨ���߶��������㷨(nCountΪ����ζ�������lpPointsΪ����ζ������꣬colorΪ�����ɫ 
void ScanFill(const LPPOINT pts,int cnt,COLORREF color);

 //�߽�����㷨(x,y)Ϊ�����ڲ������꣬fcolorΪ�����ɫ��bcolorΪ�߽���ɫ //4-��ͨ����߽�����㷨 
void BoundaryFill4(int x,int y,COLORREF fcolor,COLORREF bcolor);

 //��������㷨(x,y)Ϊ�����ڲ������꣬fcolorΪ�����ɫ��ocolorΪ���滻��ɫ //4-��ͨ����������㷨 
void FloodFill4(int x,int y,COLORREF fcolor,COLORREF ocolor);

 //Cohen-Sutherland�߶βü��㷨
// void ClipLineCS(POINT2D& p1,POINT2D& p2,POINT& winMin,POINT& winMax);

 //���Ѷ�-Barsky�߶βü��㷨 
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
