#if !defined(AFX_LINEDIALOG_H__BD33A73B_849D_4B81_B08B_1E9428258A77__INCLUDED_)
#define AFX_LINEDIALOG_H__BD33A73B_849D_4B81_B08B_1E9428258A77__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// LineDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CLineDialog dialog

class CLineDialog : public CDialog
{
// Construction
public:
	CLineDialog(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(CLineDialog)
	enum { IDD = IDD_DIALOG_Line };
	UINT	m_x1;
	UINT	m_x2;
	UINT	m_y1;
	UINT	m_y2;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CLineDialog)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(CLineDialog)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_LINEDIALOG_H__BD33A73B_849D_4B81_B08B_1E9428258A77__INCLUDED_)
