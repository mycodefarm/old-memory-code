// GPolygon.cpp: implementation of the CGPolygon class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "cg_2014112135_04.h"
#include "GPolygon.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
IMPLEMENT_SERIAL(CGPolygon,CGShape,1)
CGPolygon::CGPolygon()
{
//	{{0,0},{100,100},{50,50}};
//	cnt = 3;
}
CGPolygon::CGPolygon(POINT *p,int cnt,int w,int s,COLORREF c){
	this->p = new POINT[cnt];
	for(int i=0;i<cnt;i++){
		this->p[i] = p[i];
	}
	this->cnt = cnt;
}
CGPolygon::CGpolygon(CGPolygon& p){
	this->p = p.p;
	this->cnt = p.cnt;
}

CGPolygon::~CGPolygon()
{

}


void CGPolygon::Serialize(CArchive& ar){
	CGShape::Serialize(ar);
	if(ar.IsStoring()){
		ar<<*p<<cnt;
	}else{
		ar>>*p>>cnt;
	}
}
	//draw
void CGPolygon::Draw(CDC*pDC){
	CPen *OldPen,*newPen;
	newPen = new CPen(style,width,color);
	OldPen = pDC->SelectObject(newPen);
	pDC->Polygon(p,cnt);
	pDC->SelectObject(OldPen);
	delete newPen;
}

	//translate
void CGPolygon::Translate(int dx,int dy){
	for(int i=0;i<cnt;i++){
		p[i].x += dx;
		p[i].y += dy;
	}
}
void CGPolygon::Rotate(double xf,double yf,double zf,double angle){
	double rad = angle*3.14159/180;
	for(int i=0;i<cnt;i++){
		int t = p[i].x;
		p[i].x = (int)((p[i].x-xf)*cos(rad)-(p[i].y-yf)*sin(rad)+xf);
		p[i].y = (int)((p[i].y-yf)*cos(rad)+(t-xf)*sin(rad)+yf);
	}
}
void CGPolygon::Scale(double xf,double yf,double zf){
	int i = 0;
	for(i=0;i<cnt;i++){
		p[i].x *= xf;
		p[i].y *= yf;
	}
}

void CGPolygon::MirrorX(){
	for(int i=0;i<cnt;i++){
		p[i].y = -p[i].y;
	}
}
void CGPolygon::MirrorY(){
	for(int i=0;i<cnt;i++){
		p[i].x = -p[i].x;
	}
}
void CGPolygon::Mirror0(){
	for(int i=0;i<cnt;i++){
		p[i].y = -p[i].y;
		p[i].x = -p[i].x;
	}
}
void CGPolygon::MirrorYX(){
	for(int i=0;i<cnt;i++){
		int t = p[i].x;
		p[i].x = p[i].y;
		p[i].y = t;
	}
}//y=x
void CGPolygon::MirrorY_X(){
	for(int i=0;i<cnt;i++){
		int t = p[i].x;
		p[i].x = -p[i].y;
		p[i].y = -t;
	}
}//y=-x

void CGPolygon::ShearX2Y(double angle){
	double rad = angle*3.14159/180;
	for(int i=0;i<cnt;i++){
		p[i].x = (int)(p[i].x+p[i].y/tan(rad));
	}
}
void CGPolygon::ShearY2X(double angle){
	double rad = angle*3.14159/180;
	for(int i=0;i<cnt;i++){
		p[i].y = (int)(p[i].y+p[i].x/tan(rad));
	}
}//延Y轴关于X轴错切

CString CGPolygon::ToString(){
	CString c;
	c.Format("Polygon[%d个点]",cnt);
	return c;
}