// GCircleDialog.cpp : implementation file
//

#include "stdafx.h"
#include "CG_2014112135_04.h"
#include "GCircleDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CGCircleDialog dialog


CGCircleDialog::CGCircleDialog(CWnd* pParent /*=NULL*/)
	: CDialog(CGCircleDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(CGCircleDialog)
	m_x = 0;
	m_y = 0;
	m_r = 0;
	//}}AFX_DATA_INIT
}


void CGCircleDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CGCircleDialog)
	DDX_Text(pDX, IDC_EDIT_centerX, m_x);
	DDV_MinMaxUInt(pDX, m_x, 0, 2000);
	DDX_Text(pDX, IDC_EDIT_centerY, m_y);
	DDV_MinMaxUInt(pDX, m_y, 0, 2000);
	DDX_Text(pDX, IDC_EDIT_R, m_r);
	DDV_MinMaxUInt(pDX, m_r, 0, 1000);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CGCircleDialog, CDialog)
	//{{AFX_MSG_MAP(CGCircleDialog)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CGCircleDialog message handlers
