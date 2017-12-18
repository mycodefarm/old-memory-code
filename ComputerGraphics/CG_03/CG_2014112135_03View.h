// CG_2014112135_03View.h : interface of the CCG_2014112135_03View class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CG_2014112135_03VIEW_H__E3CD80D9_2DC1_43AD_8A0F_531697ABDF89__INCLUDED_)
#define AFX_CG_2014112135_03VIEW_H__E3CD80D9_2DC1_43AD_8A0F_531697ABDF89__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CCG_2014112135_03View : public CView
{
protected: // create from serialization only
	CCG_2014112135_03View();
	DECLARE_DYNCREATE(CCG_2014112135_03View)

// Attributes
public:
	CCG_2014112135_03Doc* GetDocument();

	HGLRC  m_hGLRC;  //OpenGL 绘制描述表  
	CDC*  m_pDC;   //OpenGL 设备描述表  
	HPALETTE m_hPalette;  //OpenGL 调色板 
// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG_2014112135_03View)
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
	virtual ~CCG_2014112135_03View();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:
	BOOL RenderScene(void);     
	BOOL SetupPixelFormat(void);  
	void SetLogicalPalette(void); 

	BOOL WorkOne(void);
	BOOL WorkTwo(void);
	BOOL A0(void);
	BOOL A2(void);
	void drawString(const char* str);
// Generated message map functions
protected:
	//{{AFX_MSG(CCG_2014112135_03View)
	afx_msg void OnCancelMode();
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	afx_msg void OnCaptureChanged(CWnd *pWnd);
	afx_msg void OnDestroy();
	afx_msg void OnChar(UINT nChar, UINT nRepCnt, UINT nFlags);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnContextMenu(CWnd* pWnd, CPoint point);
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in CG_2014112135_03View.cpp
inline CCG_2014112135_03Doc* CCG_2014112135_03View::GetDocument()
   { return (CCG_2014112135_03Doc*)m_pDocument; }
#endif

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG_2014112135_03VIEW_H__E3CD80D9_2DC1_43AD_8A0F_531697ABDF89__INCLUDED_)
