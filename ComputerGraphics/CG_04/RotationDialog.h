#if !defined(AFX_ROTATIONDIALOG_H__3F8E7276_F998_44FC_803A_0AC4F1F7B7D2__INCLUDED_)
#define AFX_ROTATIONDIALOG_H__3F8E7276_F998_44FC_803A_0AC4F1F7B7D2__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// RotationDialog.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// RotationDialog dialog

class RotationDialog : public CDialog
{
// Construction
public:
	RotationDialog(CWnd* pParent = NULL);   // standard constructor

// Dialog Data
	//{{AFX_DATA(RotationDialog)
	enum { IDD = IDD_DIALOG_Rotation };
	double	m_angle;
	int		m_rx;
	int		m_ry;
	//}}AFX_DATA


// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(RotationDialog)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:

	// Generated message map functions
	//{{AFX_MSG(RotationDialog)
		// NOTE: the ClassWizard will add member functions here
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_ROTATIONDIALOG_H__3F8E7276_F998_44FC_803A_0AC4F1F7B7D2__INCLUDED_)
