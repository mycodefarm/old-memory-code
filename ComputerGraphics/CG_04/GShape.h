// GShape.h: interface for the CGShape class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_GSHAPE_H__574D6CD3_F631_414F_8C4F_F7685EFCABA4__INCLUDED_)
#define AFX_GSHAPE_H__574D6CD3_F631_414F_8C4F_F7685EFCABA4__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class CGShape : public CObject  
{
public:
	DECLARE_SERIAL(CGShape)
	CGShape();
	CGShape(int width,int style,COLORREF color);
	CGShape(const CGShape& s);
	virtual ~CGShape();

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

	COLORREF color;
	int width;
	int style;
};

#endif // !defined(AFX_GSHAPE_H__574D6CD3_F631_414F_8C4F_F7685EFCABA4__INCLUDED_)
