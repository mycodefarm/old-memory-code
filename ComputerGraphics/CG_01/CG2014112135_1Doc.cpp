// CG2014112135_1Doc.cpp : implementation of the CCG2014112135_1Doc class
//

#include "stdafx.h"
#include "CG2014112135_1.h"

#include "CG2014112135_1Doc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1Doc

IMPLEMENT_DYNCREATE(CCG2014112135_1Doc, CDocument)

BEGIN_MESSAGE_MAP(CCG2014112135_1Doc, CDocument)
	//{{AFX_MSG_MAP(CCG2014112135_1Doc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1Doc construction/destruction

CCG2014112135_1Doc::CCG2014112135_1Doc()
{
	// TODO: add one-time construction code here

}

CCG2014112135_1Doc::~CCG2014112135_1Doc()
{
}

BOOL CCG2014112135_1Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}



/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1Doc serialization

void CCG2014112135_1Doc::Serialize(CArchive& ar)
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
// CCG2014112135_1Doc diagnostics

#ifdef _DEBUG
void CCG2014112135_1Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void CCG2014112135_1Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1Doc commands
