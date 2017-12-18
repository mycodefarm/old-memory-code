// CG2014112135_2Doc.h : interface of the CCG2014112135_2Doc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CG2014112135_2DOC_H__C3E92183_85D0_4D03_9130_69ACADE73E92__INCLUDED_)
#define AFX_CG2014112135_2DOC_H__C3E92183_85D0_4D03_9130_69ACADE73E92__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CCG2014112135_2Doc : public CDocument
{
protected: // create from serialization only
	CCG2014112135_2Doc();
	DECLARE_DYNCREATE(CCG2014112135_2Doc)

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG2014112135_2Doc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CCG2014112135_2Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CCG2014112135_2Doc)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG2014112135_2DOC_H__C3E92183_85D0_4D03_9130_69ACADE73E92__INCLUDED_)
