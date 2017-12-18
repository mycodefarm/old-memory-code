// ListDialog.cpp : implementation file
//

#include "stdafx.h"
#include "CG_2014112135_04.h"
#include "ListDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// ListDialog dialog

ListDialog::ListDialog(CWnd* pParent /*=NULL*/)
	: CDialog(ListDialog::IDD, pParent)
{
	//{{AFX_DATA_INIT(ListDialog)
	//}}AFX_DATA_INIT
}
ListDialog::ListDialog(CObArray* a,CWnd* pParent /*=NULL*/)
	: CDialog(ListDialog::IDD, pParent){
	this->m_all = a;
	index = -1;
}

void ListDialog::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(ListDialog)
	DDX_Control(pDX, IDC_LIST_All_A, m_list);
	//}}AFX_DATA_MAP
}


BEGIN_MESSAGE_MAP(ListDialog, CDialog)
	//{{AFX_MSG_MAP(ListDialog)
	ON_LBN_SELCHANGE(IDC_LIST_All_A, OnSelchangeLISTAllA)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// ListDialog message handlers

BOOL ListDialog::OnInitDialog() 
{
	CDialog::OnInitDialog();
	
	int count = m_all->GetSize();
	for(int i=0;i<count;i++){
		//m_list.AddString(();
		m_list.InsertString(i,((CGShape*)(m_all->GetAt(i)))->ToString());
	}
	
	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void ListDialog::OnSelchangeLISTAllA() 
{
	index = m_list.GetCurSel();	
}
