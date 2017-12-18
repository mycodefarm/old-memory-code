// ShearDialog.cpp : implementation file
//

#include "stdafx.h"
#include "cg_2014112135_04.h"
#include "ShearDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// ShearDialog dialog


ShearDialog::ShearDialog(CWnd* pParent /*=NULL*/)
	: CDialog(ShearDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(ShearDialog)
	m_angle = 0;
	//}}AFX_DATA_INIT
}


void ShearDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(ShearDialog)
	DDX_Text(pDX, IDC_EDIT_Shear, m_angle);
	DDV_MinMaxInt(pDX, m_angle, -360, 360);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(ShearDialog, CDialog)
	//{{AFX_MSG_MAP(ShearDialog)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// ShearDialog message handlers
