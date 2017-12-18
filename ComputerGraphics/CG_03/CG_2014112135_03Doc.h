// CG_2014112135_03Doc.h : interface of the CCG_2014112135_03Doc class
//
/////////////////////////////////////////////////////////////////////////////

#if !defined(AFX_CG_2014112135_03DOC_H__7E7876E0_9E45_4476_B422_87CA0C494798__INCLUDED_)
#define AFX_CG_2014112135_03DOC_H__7E7876E0_9E45_4476_B422_87CA0C494798__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


class CCG_2014112135_03Doc : public CDocument
{
protected: // create from serialization only
	CCG_2014112135_03Doc();
	DECLARE_DYNCREATE(CCG_2014112135_03Doc)

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG_2014112135_03Doc)
	public:
	virtual BOOL OnNewDocument();
	virtual void Serialize(CArchive& ar);
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CCG_2014112135_03Doc();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	//{{AFX_MSG(CCG_2014112135_03Doc)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG_2014112135_03DOC_H__7E7876E0_9E45_4476_B422_87CA0C494798__INCLUDED_)
