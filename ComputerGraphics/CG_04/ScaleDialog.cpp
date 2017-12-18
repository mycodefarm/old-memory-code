// ScaleDialog.cpp : implementation file
//

#include "stdafx.h"
#include "CG_2014112135_04.h"
#include "ScaleDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// ScaleDialog dialog


ScaleDialog::ScaleDialog(CWnd* pParent /*=NULL*/)
	: CDialog(ScaleDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(ScaleDialog)
	m_sx = 0.0f;
	m_sy = 0.0f;
	//}}AFX_DATA_INIT
}


void ScaleDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(ScaleDialog)
	DDX_Text(pDX, IDC_EDIT_sx, m_sx);
	DDV_MinMaxFloat(pDX, m_sx, 0.f, 10.f);
	DDX_Text(pDX, IDC_EDIT_sy, m_sy);
	DDV_MinMaxFloat(pDX, m_sy, 0.f, 10.f);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(ScaleDialog, CDialog)
	//{{AFX_MSG_MAP(ScaleDialog)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// ScaleDialog message handlers
