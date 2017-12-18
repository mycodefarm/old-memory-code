// GShape.cpp: implementation of the CGShape class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "CG_2014112135_04.h"
#include "GShape.h"

#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

IMPLEMENT_SERIAL(CGShape,CObject,1)

CGShape::CGShape()
{
	this->width = 1;
	this->style = PS_SOLID;
	this->color = RGB(0,0,0);
}

CGShape::~CGShape()
{

}
CGShape::CGShape(int width,int style,COLORREF color){
	this->width = width;
	this->style = style;
	this->color = color;
}
CGShape::CGShape(const CGShape&s){
	this->style = s.style;
	this->width = s.width;
	this->color = s.color;
}

	//serial
void CGShape::Serialize(CArchive& ar){
	CObject::Serialize(ar);
	if(ar.IsStoring()){
		ar<<color<<style<<width;
	}else{
		ar>>color>>style>>width;
	}
}
	//draw
void CGShape::Draw(CDC*pDC){}

	//translate
void CGShape::Translate(int dx,int dy){

}
void CGShape::Rotate(double xf,double yf,double zf,double angle){
	
}
void CGShape::Scale(double xf,double yf,double zf){

}

void CGShape::MirrorX(){}
void CGShape::MirrorY(){}
void CGShape::Mirror0(){}
void CGShape::MirrorYX(){}//y=x
void CGShape::MirrorY_X(){}//y=-x
void CGShape::ShearX2Y(double angle){}
void CGShape::ShearY2X(double angle){}//—”Y÷·πÿ”⁄X÷·¥Ì«–

CString CGShape::ToString(){
	CString c;
	return c;
}
