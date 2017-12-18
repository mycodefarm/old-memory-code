// TranslateDialog.cpp : implementation file
//

#include "stdafx.h"
#include "CG_2014112135_04.h"
#include "TranslateDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// TranslateDialog dialog


TranslateDialog::TranslateDialog(CWnd* pParent /*=NULL*/)
	: CDialog(TranslateDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(TranslateDialog)
	m_dx = 0;
	m_dy = 0;
	//}}AFX_DATA_INIT
}


void TranslateDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(TranslateDialog)
	DDX_Text(pDX, IDC_EDIT_dx, m_dx);
	DDV_MinMaxInt(pDX, m_dx, -1000, 1000);
	DDX_Text(pDX, IDC_EDIT_dy, m_dy);
	DDV_MinMaxInt(pDX, m_dy, -1000, 1000);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(TranslateDialog, CDialog)
	//{{AFX_MSG_MAP(TranslateDialog)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// TranslateDialog message handlers
