// CG2014112135_2View.h : interface of the CCG2014112135_2View class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CG2014112135_2VIEW_H__28DAC686_2A56_4A00_89C6_AE98FE90FF8F__INCLUDED_)
#define AFX_CG2014112135_2VIEW_H__28DAC686_2A56_4A00_89C6_AE98FE90FF8F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CCG2014112135_2View : public CScrollView
{
protected: // create from serialization only
	CCG2014112135_2View();
	DECLARE_DYNCREATE(CCG2014112135_2View)

// Attributes
public:
	CCG2014112135_2Doc* GetDocument();

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG2014112135_2View)
	public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	protected:
	virtual void OnInitialUpdate(); // called first time after construct
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);
	//}}AFX_VIRTUAL

// Implementation
public:
	void MyTaiji(CDC*pDC);
	void FillPractice(CDC*pDC);
	void CirClePractice(CDC*pDC);
	void LinePractice(CDC*pDC);
	void MyStar(CDC* pDC);
	virtual ~CCG2014112135_2View();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CCG2014112135_2View)
	afx_msg void OnLinepractice(CDC* pDC);
	afx_msg void OnCirclePractice(CDC* pDC);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in CG2014112135_2View.cpp
inline CCG2014112135_2Doc* CCG2014112135_2View::GetDocument()
   { return (CCG2014112135_2Doc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG2014112135_2VIEW_H__28DAC686_2A56_4A00_89C6_AE98FE90FF8F__INCLUDED_)
