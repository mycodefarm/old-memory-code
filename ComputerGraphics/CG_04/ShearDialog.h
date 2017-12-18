#if !defined(AFX_SHEARDIALOG_H__EE894018_A0D7_474E_9A53_69DB8D05E49D__INCLUDED_)
#define AFX_SHEARDIALOG_H__EE894018_A0D7_474E_9A53_69DB8D05E49D__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ShearDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// ShearDialog dialog

class ShearDialog : public CDialog
{
// Construction
public:
	ShearDialog(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(ShearDialog)
	enum { IDD = IDD_DIALOG_Shear };
	int		m_angle;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(ShearDialog)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(ShearDialog)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SHEARDIALOG_H__EE894018_A0D7_474E_9A53_69DB8D05E49D__INCLUDED_)
