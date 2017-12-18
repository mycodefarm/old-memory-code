#if !defined(AFX_GCIRCLEDIALOG_H__8DECA83A_2492_4A80_9323_D23090BD3B49__INCLUDED_)
#define AFX_GCIRCLEDIALOG_H__8DECA83A_2492_4A80_9323_D23090BD3B49__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// GCircleDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CGCircleDialog dialog

class CGCircleDialog : public CDialog
{
// Construction
public:
	CGCircleDialog(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CGCircleDialog)
	enum { IDD = IDD_DIALOG_Circle };
	UINT	m_x;
	UINT	m_y;
	UINT	m_r;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CGCircleDialog)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CGCircleDialog)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_GCIRCLEDIALOG_H__8DECA83A_2492_4A80_9323_D23090BD3B49__INCLUDED_)
