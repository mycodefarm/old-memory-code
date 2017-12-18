// CG_2014112135_03Doc.cpp : implementation of the CCG_2014112135_03Doc class
//

#include "stdafx.h"
#include "CG_2014112135_03.h"

#include "CG_2014112135_03Doc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03Doc

IMPLEMENT_DYNCREATE(CCG_2014112135_03Doc, CDocument)

BEGIN_MESSAGE_MAP(CCG_2014112135_03Doc, CDocument)
	//{{AFX_MSG_MAP(CCG_2014112135_03Doc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03Doc construction/destruction

CCG_2014112135_03Doc::CCG_2014112135_03Doc()
{
	// TODO: add one-time construction code here

}

CCG_2014112135_03Doc::~CCG_2014112135_03Doc()
{
}

BOOL CCG_2014112135_03Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}



/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03Doc serialization

void CCG_2014112135_03Doc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03Doc diagnostics

#ifdef _DEBUG
void CCG_2014112135_03Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void CCG_2014112135_03Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03Doc commands
