// CG2014112135_1View.h : interface of the CCG2014112135_1View class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CG2014112135_1VIEW_H__0B468AF2_4EE9_4FC8_A074_4095E4193CFD__INCLUDED_)
#define AFX_CG2014112135_1VIEW_H__0B468AF2_4EE9_4FC8_A074_4095E4193CFD__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CCG2014112135_1View : public CView
{
protected: // create from serialization only
	CCG2014112135_1View();
	DECLARE_DYNCREATE(CCG2014112135_1View)

// Attributes
public:
	CCG2014112135_1Doc* GetDocument();

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG2014112135_1View)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
	//}}AFX_VIRTUAL

// Implementation
public:
	void drawEclipse(CDC* pDC);
	void drawPolyLine(CDC* pDC);
	void drawRectangle(CDC* pDC);
	void myStar(CDC* pDC);
	void myTaiji(CDC* pDC);
	virtual ~CCG2014112135_1View();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CCG2014112135_1View)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in CG2014112135_1View.cpp
inline CCG2014112135_1Doc* CCG2014112135_1View::GetDocument()
   { return (CCG2014112135_1Doc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG2014112135_1VIEW_H__0B468AF2_4EE9_4FC8_A074_4095E4193CFD__INCLUDED_)
