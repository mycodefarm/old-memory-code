#if !defined(AFX_TRANSLATEDIALOG_H__FA752F7B_2D4A_4E85_BC1A_0F0C56252769__INCLUDED_)
#define AFX_TRANSLATEDIALOG_H__FA752F7B_2D4A_4E85_BC1A_0F0C56252769__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// TranslateDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// TranslateDialog dialog

class TranslateDialog : public CDialog
{
// Construction
public:
	TranslateDialog(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(TranslateDialog)
	enum { IDD = IDD_DIALOG_Translate };
	int		m_dx;
	int		m_dy;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(TranslateDialog)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(TranslateDialog)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_TRANSLATEDIALOG_H__FA752F7B_2D4A_4E85_BC1A_0F0C56252769__INCLUDED_)
