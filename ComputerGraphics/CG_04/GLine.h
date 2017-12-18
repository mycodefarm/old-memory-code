// GLine.h: interface for the CGLine class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GLINE_H__F534F357_8FCB_49DC_AEFD_F9E7FB59EC34__INCLUDED_)
#define AFX_GLINE_H__F534F357_8FCB_49DC_AEFD_F9E7FB59EC34__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "GShape.h"
#include <math.h>

class CGLine : public CGShape  
{
public:
	DECLARE_SERIAL(CGLine);
	CGLine();
	CGLine(int x1,int y1,int x2,int y2,int w=1,int s=PS_SOLID,COLORREF c=RGB(0,0,0));
	CGLine(CGLine& line);
	virtual ~CGLine();

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

	int x1,x2,y1,y2;
};

#endif // !defined(AFX_GLINE_H__F534F357_8FCB_49DC_AEFD_F9E7FB59EC34__INCLUDED_)
