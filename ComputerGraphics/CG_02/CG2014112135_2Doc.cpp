// CG2014112135_2Doc.cpp : implementation of the CCG2014112135_2Doc class
//

#include "stdafx.h"
#include "CG2014112135_2.h"

#include "CG2014112135_2Doc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2Doc

IMPLEMENT_DYNCREATE(CCG2014112135_2Doc, CDocument)

BEGIN_MESSAGE_MAP(CCG2014112135_2Doc, CDocument)
	//{{AFX_MSG_MAP(CCG2014112135_2Doc)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2Doc construction/destruction

CCG2014112135_2Doc::CCG2014112135_2Doc()
{
	// TODO: add one-time construction code here

}

CCG2014112135_2Doc::~CCG2014112135_2Doc()
{
}

BOOL CCG2014112135_2Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}



/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2Doc serialization

void CCG2014112135_2Doc::Serialize(CArchive& ar)
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
// CCG2014112135_2Doc diagnostics

#ifdef _DEBUG
void CCG2014112135_2Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void CCG2014112135_2Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2Doc commands
