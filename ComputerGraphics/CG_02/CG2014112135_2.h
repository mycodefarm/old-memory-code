// CG2014112135_2.h : main header file for the CG2014112135_2 application
//

#if !defined(AFX_CG2014112135_2_H__1ED9FBDF_646E_4E1C_9CA7_CFA9D3AC938F__INCLUDED_)
#define AFX_CG2014112135_2_H__1ED9FBDF_646E_4E1C_9CA7_CFA9D3AC938F__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // main symbols

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2App:
// See CG2014112135_2.cpp for the implementation of this class
//

class CCG2014112135_2App : public CWinApp
{
public:
	CCG2014112135_2App();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG2014112135_2App)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation
	//{{AFX_MSG(CCG2014112135_2App)
	afx_msg void OnAppAbout();
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG2014112135_2_H__1ED9FBDF_646E_4E1C_9CA7_CFA9D3AC938F__INCLUDED_)
