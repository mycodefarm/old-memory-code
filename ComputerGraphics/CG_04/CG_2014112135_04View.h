// CG_2014112135_04View.h : interface of the CCG_2014112135_04View class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CG_2014112135_04VIEW_H__F6F1539C_B8A5_4B42_A473_E5DFE80E1703__INCLUDED_)
#define AFX_CG_2014112135_04VIEW_H__F6F1539C_B8A5_4B42_A473_E5DFE80E1703__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CCG_2014112135_04View : public CView
{
protected: // create from serialization only
	CCG_2014112135_04View();
	DECLARE_DYNCREATE(CCG_2014112135_04View)

// Attributes
public:
	CCG_2014112135_04Doc* GetDocument();

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG_2014112135_04View)
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
	virtual ~CCG_2014112135_04View();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CCG_2014112135_04View)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in CG_2014112135_04View.cpp
inline CCG_2014112135_04Doc* CCG_2014112135_04View::GetDocument()
   { return (CCG_2014112135_04Doc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG_2014112135_04VIEW_H__F6F1539C_B8A5_4B42_A473_E5DFE80E1703__INCLUDED_)
