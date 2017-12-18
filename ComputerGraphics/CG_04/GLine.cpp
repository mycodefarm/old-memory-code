// GLine.cpp: implementation of the CGLine class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "CG_2014112135_04.h"
#include "GLine.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////
IMPLEMENT_SERIAL(CGLine,CGShape,1)
CGLine::CGLine()
{
	x1=x2=y1=y2 = 0;
}

CGLine::CGLine(int x1,int y1,int x2,int y2,int w,int s,COLORREF c){
	this->x1 = x1;
	this->x2 = x2;
	this->y1 = y1;
	this->y2 = y2;
}
CGLine::CGLine(CGLine& line){
	this->x1 = line.x1;
	this->x2 = line.x2;
	this->y1 = line.y1;
	this->y2 = line.y2;
}

CGLine::~CGLine()
{

}

void CGLine::Serialize(CArchive& ar){
	CGShape::Serialize(ar);
	if(ar.IsStoring()){
		ar<<x1<<y1<<x2<<y2;
	}else{
		ar>>x1>>y1>>x2>>y2;
	}
}
	//draw
void CGLine::Draw(CDC*pDC){
	CPen *OldPen,*newPen;
	newPen = new CPen(style,width,color);
	OldPen = pDC->SelectObject(newPen);
	pDC->MoveTo(x1,y1);
	pDC->LineTo(x2,y2);
	pDC->SelectObject(OldPen);
	delete newPen;
}

	//translate
void CGLine::Translate(int dx,int dy){
	x1 += dx;
	x2 += dx;
	y1 += dy;
	y2 += dy;
}
void CGLine::Rotate(double xf,double yf,double zf,double angle){
	double rad = angle*3.14159/180;
	int t = x1;
	x1 = (int)((x1-xf)*cos(rad)-(y1-yf)*sin(rad)+xf);
	y1 = (int)((y1-yf)*cos(rad)+(t-xf)*sin(rad)+yf);
	t = x2;
	x2 = (int)((x2-xf)*cos(rad)-(y2-yf)*sin(rad)+xf);
	y2 = (int)((y2-yf)*cos(rad)+(t-xf)*sin(rad)+yf);
}
void CGLine::Scale(double xf,double yf,double zf){
	x1 = x1*xf;
	y1 = y1*yf;
	x2*=xf;
	y2*=yf;
}

void CGLine::MirrorX(){
	y1 = -y1;
	y2 = -y2;
}
void CGLine::MirrorY(){
	x1 = -x1;
	x2 = -x2;
}
void CGLine::Mirror0(){
	y1 = -y1;
	y2 = -y2;
	x1 = -x1;
	x2 = -x2;
}
void CGLine::MirrorYX(){
	int t = x1;
	x1 = y1;
	y1 = t;
	t = x2;
	x2 = y2;
	y2 = t;
}//y=x
void CGLine::MirrorY_X(){
	int t = x1;
	x1 = -y1;
	y1 = -t;
	t = x2;
	x2 = -y2;
	y2 = -t;
}//y=-x
void CGLine::ShearX2Y(double angle){
	double rad = angle*3.14159/180;
	x1 = (int)(x1 + y1/tan(rad)); 
	x2 = (int)(x2 + y2/tan(rad)); 
}
void CGLine::ShearY2X(double angle){
	double rad = angle*3.14159/180;
	y1 = (int)(y1 + x1/tan(rad));
	y2 = (int)(y2 + x2/tan(rad));
}//—”Y÷·πÿ”⁄X÷·¥Ì«–


CString CGLine::ToString(){
	CString c;
	c.Format("Line[%d,%d,%d,%d]",x1,y1,x2,y2);
	return c;
}