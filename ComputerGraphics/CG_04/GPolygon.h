// GPolygon.h: interface for the CGPolygon class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GPOLYGON_H__BDAF20C0_80CC_413B_8FE3_1F9BC0B4E324__INCLUDED_)
#define AFX_GPOLYGON_H__BDAF20C0_80CC_413B_8FE3_1F9BC0B4E324__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GShape.h"
#include <math.h>
class CGPolygon : public CGShape  
{
public:
	DECLARE_SERIAL(CGPolygon);
	CGPolygon();
	CGPolygon(POINT *p,int cnt,int w=1,int s=PS_SOLID,COLORREF c=RGB(0,0,0));
	CGpolygon(CGPolygon& p);
	virtual ~CGPolygon();

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

	POINT *p;
	int cnt;
};

#endif // !defined(AFX_GPOLYGON_H__BDAF20C0_80CC_413B_8FE3_1F9BC0B4E324__INCLUDED_)
