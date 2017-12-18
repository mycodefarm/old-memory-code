// CG_2014112135_04.h : main header file for the CG_2014112135_04 application
//

#if !defined(AFX_CG_2014112135_04_H__5DB5F808_1EE6_4B84_819A_3ED063DA36C7__INCLUDED_)
#define AFX_CG_2014112135_04_H__5DB5F808_1EE6_4B84_819A_3ED063DA36C7__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // main symbols

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04App:
// See CG_2014112135_04.cpp for the implementation of this class
//

class CCG_2014112135_04App : public CWinApp
{
public:
	CCG_2014112135_04App();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG_2014112135_04App)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation
	//{{AFX_MSG(CCG_2014112135_04App)
	afx_msg void OnAppAbout();
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG_2014112135_04_H__5DB5F808_1EE6_4B84_819A_3ED063DA36C7__INCLUDED_)
