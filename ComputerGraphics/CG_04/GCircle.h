// GCircle.h: interface for the CGCircle class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GCIRCLE_H__1781DDFC_9B71_4C78_B25E_FC12E20094C9__INCLUDED_)
#define AFX_GCIRCLE_H__1781DDFC_9B71_4C78_B25E_FC12E20094C9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GShape.h"
#include <math.h>
class CGCircle : public CGShape  
{
public:
	DECLARE_SERIAL(CGCircle);
	CGCircle();
	CGCircle(int cx,int cy,int r,int w=1,int s=PS_SOLID,COLORREF c=RGB(0,0,0));
	CGCircle(CGCircle& c);
	virtual ~CGCircle();

	virtual CString ToString();

	//serial
	void Serialize(CArchive& ar);
	//draw
	virtual void Draw(CDC*pDC);

	//translate
	virtual void Translate(int dx,int dy);
	virtual void Rotate(double xf,double yf,double zf,double angle);
	virtual void Scale(double xf,double yf,double zf);

	virtual void MirrorX();
	virtual void MirrorY();
	virtual void Mirror0();
	virtual void MirrorYX();//y=x
	virtual void MirrorY_X();//y=-x
	virtual void ShearX2Y(double angle);
	virtual void ShearY2X(double angle);//—”Y÷·πÿ”⁄X÷·¥Ì«–

	int cx,cy,r,rx,ry;
};

#endif // !defined(AFX_GCIRCLE_H__1781DDFC_9B71_4C78_B25E_FC12E20094C9__INCLUDED_)
