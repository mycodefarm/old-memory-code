// LineDialog.cpp : implementation file
//

#include "stdafx.h"
#include "CG_2014112135_04.h"
#include "LineDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CLineDialog dialog


CLineDialog::CLineDialog(CWnd* pParent /*=NULL*/)
	: CDialog(CLineDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(CLineDialog)
	m_x1 = 0;
	m_x2 = 0;
	m_y1 = 0;
	m_y2 = 0;
	//}}AFX_DATA_INIT
}


void CLineDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CLineDialog)
	DDX_Text(pDX, IDC_EDIT_x1, m_x1);
	DDV_MinMaxUInt(pDX, m_x1, 0, 2000);
	DDX_Text(pDX, IDC_EDIT_X2, m_x2);
	DDV_MinMaxUInt(pDX, m_x2, 0, 2000);
	DDX_Text(pDX, IDC_EDIT_y1, m_y1);
	DDV_MinMaxUInt(pDX, m_y1, 0, 2000);
	DDX_Text(pDX, IDC_EDIT_Y2, m_y2);
	DDV_MinMaxUInt(pDX, m_y2, 0, 2000);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(CLineDialog, CDialog)
	//{{AFX_MSG_MAP(CLineDialog)
		// NOTE: the ClassWizard will add message map macros here
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CLineDialog message handlers
