#if !defined(AFX_LISTDIALOG_H__93952921_92BA_4D54_972A_E0948463FE68__INCLUDED_)
#define AFX_LISTDIALOG_H__93952921_92BA_4D54_972A_E0948463FE68__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ListDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// ListDialog dialog
#include <afxtempl.h>//CObarray
#include "GShape.h"
class ListDialog : public CDialog
{
// Construction
public:
	ListDialog(CWnd* pParent = NULL);   // standard constructor
	ListDialog(CObArray *a,CWnd* pParent = NULL);

	CObArray* m_all;
	int index;
// Dialog Data
	//{{AFX_DATA(ListDialog)
	enum { IDD = IDD_DIALOG_ListAll };
	CListBox	m_list;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(ListDialog)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(ListDialog)
	virtual BOOL OnInitDialog();
	afx_msg void OnSelchangeLISTAllA();
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_LISTDIALOG_H__93952921_92BA_4D54_972A_E0948463FE68__INCLUDED_)
