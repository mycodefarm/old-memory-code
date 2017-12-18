#if !defined(AFX_SCALEDIALOG_H__5F486E37_120C_4363_9111_F7C4AECA9D9C__INCLUDED_)
#define AFX_SCALEDIALOG_H__5F486E37_120C_4363_9111_F7C4AECA9D9C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// ScaleDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// ScaleDialog dialog

class ScaleDialog : public CDialog
{
// Construction
public:
	ScaleDialog(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(ScaleDialog)
	enum { IDD = IDD_DIALOG_Scale };
	float	m_sx;
	float	m_sy;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(ScaleDialog)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(ScaleDialog)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_SCALEDIALOG_H__5F486E37_120C_4363_9111_F7C4AECA9D9C__INCLUDED_)
