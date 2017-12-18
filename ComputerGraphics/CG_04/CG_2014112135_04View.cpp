// CG_2014112135_04View.cpp : implementation of the CCG_2014112135_04View class
//

#include "stdafx.h"
#include "CG_2014112135_04.h"

#include "CG_2014112135_04Doc.h"
#include "CG_2014112135_04View.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04View

IMPLEMENT_DYNCREATE(CCG_2014112135_04View, CView)

BEGIN_MESSAGE_MAP(CCG_2014112135_04View, CView)
	//{{AFX_MSG_MAP(CCG_2014112135_04View)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04View construction/destruction

CCG_2014112135_04View::CCG_2014112135_04View()
{
	// TODO: add construction code here

}

CCG_2014112135_04View::~CCG_2014112135_04View()
{
}

BOOL CCG_2014112135_04View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04View drawing

void CCG_2014112135_04View::OnDraw(CDC* pDC)
{
	CCG_2014112135_04Doc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	// TODO: add draw code for native data here

	pDoc->Draw(pDC);//add by jimo
}

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04View printing

BOOL CCG_2014112135_04View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CCG_2014112135_04View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CCG_2014112135_04View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04View diagnostics

#ifdef _DEBUG
void CCG_2014112135_04View::AssertValid() const
{
	CView::AssertValid();
}

void CCG_2014112135_04View::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CCG_2014112135_04Doc* CCG_2014112135_04View::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CCG_2014112135_04Doc)));
	return (CCG_2014112135_04Doc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04View message handlers
