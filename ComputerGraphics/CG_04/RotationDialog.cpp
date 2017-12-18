// RotationDialog.cpp : implementation file
//

#include "stdafx.h"
#include "cg_2014112135_04.h"
#include "RotationDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// RotationDialog dialog


RotationDialog::RotationDialog(CWnd* pParent /*=NULL*/)
	: CDialog(RotationDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(RotationDialog)
	m_angle = 0.0;
	m_rx = 0;
	m_ry = 0;
	//}}AFX_DATA_INIT
}


void RotationDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(RotationDialog)
	DDX_Text(pDX, IDC_EDIT_RAngle, m_angle);
	DDX_Text(pDX, IDC_EDIT_RX, m_rx);
	DDV_MinMaxInt(pDX, m_rx, -1000, 1000);
	DDX_Text(pDX, IDC_EDIT_RY, m_ry);
	DDV_MinMaxInt(pDX, m_ry, -1000, 1000);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(RotationDialog, CDialog)
	//{{AFX_MSG_MAP(RotationDialog)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// RotationDialog message handlers
