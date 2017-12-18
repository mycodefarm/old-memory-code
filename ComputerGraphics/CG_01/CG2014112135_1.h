// CG2014112135_1.h : main header file for the CG2014112135_1 application
//

#if !defined(AFX_CG2014112135_1_H__513097B8_D85F_4D10_9185_A6BA1CAFD8B6__INCLUDED_)
#define AFX_CG2014112135_1_H__513097B8_D85F_4D10_9185_A6BA1CAFD8B6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"       // main symbols

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1App:
// See CG2014112135_1.cpp for the implementation of this class
//

class CCG2014112135_1App : public CWinApp
{
public:
	CCG2014112135_1App();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCG2014112135_1App)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation
	//{{AFX_MSG(CCG2014112135_1App)
	afx_msg void OnAppAbout();
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CG2014112135_1_H__513097B8_D85F_4D10_9185_A6BA1CAFD8B6__INCLUDED_)
