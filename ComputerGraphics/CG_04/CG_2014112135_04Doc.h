// CG_2014112135_04Doc.h : interface of the CCG_2014112135_04Doc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CG_2014112135_04DOC_H__83D2489B_E401_4BF7_92E7_4048DC9079FF__INCLUDED_)
#define AFX_CG_2014112135_04DOC_H__83D2489B_E401_4BF7_92E7_4048DC9079FF__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "LineDialog.h"
#include "GLine.h"
#include "GCircle.h"
#include "GCircleDialog.h"
#include "TranslateDialog.h"
#include "ScaleDialog.h"
#include "ListDialog.h"
#include "RotationDialog.h"
#include "GPolygon.h"
#include <afxtempl.h>//CObarray
#include "GShape.h"
#include "ShearDialog.h"
class CCG_2014112135_04Doc : public CDocument
{
protected: // create from serialization only
	CCG_2014112135_04Doc();
	DECLARE_DYNCREATE(CCG_2014112135_04Doc)

// Attributes
public:
	CObArray m_all;

	CGShape *curr;
// Operations
public:
	////jimo///
	void Draw(CDC* pDC);
	void AddShape(CGShape* s);

	int type;//0：对所有图形变换，1：对单个变换
	/////jimo////
// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG_2014112135_04Doc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CCG_2014112135_04Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CCG_2014112135_04Doc)
	afx_msg void OnLine();
	afx_msg void OnCircle();
	afx_msg void OnTranslate();
	afx_msg void OnmirrorX();
	afx_msg void OnScale();
	afx_msg void OnListAll();
	afx_msg void OnmirrorY();
	afx_msg void OnMirror0();
	afx_msg void OnMirrorYX();
	afx_msg void OnMirrorY_X();
	afx_msg void OnSelchangeLISTAll();
	afx_msg void OnRotation();
	afx_msg void OnPolygon();
	afx_msg void OnShearXY();
	afx_msg void OnShearYX();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG_2014112135_04DOC_H__83D2489B_E401_4BF7_92E7_4048DC9079FF__INCLUDED_)
