// CG2014112135_1Doc.h : interface of the CCG2014112135_1Doc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CG2014112135_1DOC_H__A2F294BE_8EE1_4BBC_B5B4_6A7E05BDFCFC__INCLUDED_)
#define AFX_CG2014112135_1DOC_H__A2F294BE_8EE1_4BBC_B5B4_6A7E05BDFCFC__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CCG2014112135_1Doc : public CDocument
{
protected: // create from serialization only
	CCG2014112135_1Doc();
	DECLARE_DYNCREATE(CCG2014112135_1Doc)

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG2014112135_1Doc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CCG2014112135_1Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CCG2014112135_1Doc)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG2014112135_1DOC_H__A2F294BE_8EE1_4BBC_B5B4_6A7E05BDFCFC__INCLUDED_)
