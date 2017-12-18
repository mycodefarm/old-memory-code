// GCircle.cpp: implementation of the CGCircle class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "CG_2014112135_04.h"
#include "GCircle.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
IMPLEMENT_SERIAL(CGCircle,CGShape,1)
CGCircle::CGCircle()
{
	cx = cy = 100;
	rx = ry = r = 50;
}

CGCircle::CGCircle(int cx,int cy,int r,int w,int s,COLORREF c){
	this->cx = cx;
	this->cy = cy;
	this->r = r;
	this->rx = r;
	this->ry = r;
}

CGCircle::CGCircle(CGCircle& c){
	this->cx = c.cx;
	this->cy = c.cy;
	this->r = c.r;
	this->rx = c.r;
	this->ry = c.r;
}

CGCircle::~CGCircle()
{

}

//serial
void CGCircle::Serialize(CArchive& ar){
	CGShape::Serialize(ar);
	if(ar.IsStoring()){
		ar<<cx<<cy<<r<<rx<<ry;
	}else{
		ar>>cx>>cy>>r>>rx>>ry;
	}
}
//draw
void CGCircle::Draw(CDC*pDC){
	CPen *OldPen,*newPen;
	newPen = new CPen(style,width,color);
	OldPen = pDC->SelectObject(newPen);
	pDC->Ellipse(cx-rx,cy-ry,cx+rx,cy+ry);
	pDC->SelectObject(OldPen);
	delete newPen;
}

//translate
void CGCircle::Translate(int dx,int dy){
	cx += dx;
	cy += dy;
}
void CGCircle::Rotate(double xf,double yf,double zf,double angle){
	double rad = angle*3.14159/180;
	int t = cx;
	cx = (int)((cx-xf)*cos(rad)-(cy-yf)*sin(rad)+xf);
	cy = (int)((cy-yf)*cos(rad)+(t-xf)*sin(rad)+yf);
}
void CGCircle::Scale(double xf,double yf,double zf){
	rx = (int)(rx*xf);
	ry = (int)(ry*yf);
}

void CGCircle::MirrorX(){
	cy = -cy;
}
void CGCircle::MirrorY(){
	cx = -cx;
}
void CGCircle::Mirror0(){
	cx = -cx;
	cy = -cy;
}
void CGCircle::MirrorYX(){
	int t = cx;
	cx = cy;
	cy = t;
}//y=x
void CGCircle::MirrorY_X(){
	int t = cx;
	cx = -cy;
	cy = -t;
}//y=-x
void CGCircle::ShearX2Y(double angle){
	double rad = angle*3.14159/180;
	cx = (int)(cx + cy/tan(rad)); 
}
void CGCircle::ShearY2X(double angle){
	double rad = angle*3.14159/180;
	cy = (int)(cy + cx/tan(rad));
}//—”Y÷·πÿ”⁄X÷·¥Ì«–

CString CGCircle::ToString(){
	CString s;
	s.Format("Circle[%d,%d,%d]",cx,cy,r);
	return s;
}