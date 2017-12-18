// CG2014112135_1View.cpp : implementation of the CCG2014112135_1View class
//

#include "stdafx.h"
#include "CG2014112135_1.h"

#include "CG2014112135_1Doc.h"
#include "CG2014112135_1View.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1View

IMPLEMENT_DYNCREATE(CCG2014112135_1View, CView)

BEGIN_MESSAGE_MAP(CCG2014112135_1View, CView)
	//{{AFX_MSG_MAP(CCG2014112135_1View)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1View construction/destruction

CCG2014112135_1View::CCG2014112135_1View()
{
	// TODO: add construction code here

}

CCG2014112135_1View::~CCG2014112135_1View()
{
}

BOOL CCG2014112135_1View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1View drawing




void CCG2014112135_1View::OnDraw(CDC* pDC)
{
	CCG2014112135_1Doc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	// TODO: add draw code for native data here
	

	
	//draw circle
//	pDC->Arc(100,100,300,300,300,200,300,200);
	//poly bezier
	//POINT p[4] = {{100,200},{150,0},{250,400},{300,200}};
	//pDC->PolyBezier(p,4);


	drawRectangle(pDC);
	drawPolyLine(pDC);
	myTaiji(pDC);
	myStar(pDC);
	drawEclipse(pDC);

//	pDC->Arc(200,200,400,400,400,300,200,300);
//	pDC->SelectObject(brush);
}




/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1View printing

BOOL CCG2014112135_1View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CCG2014112135_1View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CCG2014112135_1View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1View diagnostics

#ifdef _DEBUG
void CCG2014112135_1View::AssertValid() const
{
	CView::AssertValid();
}

void CCG2014112135_1View::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CCG2014112135_1Doc* CCG2014112135_1View::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CCG2014112135_1Doc)));
	return (CCG2014112135_1Doc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_1View message handlers

void CCG2014112135_1View::myTaiji(CDC* pDC)
{
	//五角星
	CPoint pb[5],ps[5];//point big/small
	double RADIAN = 3.1415926/180;//radian
	int toLeft = 150;//里左边的距离
	int toUp = 120;//里上面的距离
	int  R=100;//外接圆半径
	int r=(int)R*sin(18*RADIAN)/cos(36*RADIAN);//内接圆半径
 
	 for(int i=0;i<5;i++){
		 /* 外接圆的五个平均分布点的坐标*/
		 pb[i].x= (int)toLeft +R*cos((90+i*72)*RADIAN); 
		 pb[i].y = (int)toUp -R*sin((90+i*72)*RADIAN);
		 /* 内接圆的五个平均分布点的坐标*/
    	 ps[i].x = (int)toLeft +r*cos((54+i*72)*RADIAN); 
	     ps[i].y = (int)toUp -r*sin((54+i*72)*RADIAN);
	 }
	 CBrush *orangeBrush,*yellowBrush;
	 CPen *orangePen;
	 orangeBrush = new CBrush();
	 yellowBrush = new CBrush();
	 orangePen = new CPen();
	 orangeBrush->CreateSolidBrush(RGB(255,81,0));
	 yellowBrush->CreateSolidBrush(RGB(255,200,0));
	 orangePen->CreatePen(PS_SOLID,1,RGB(255,81,0));
	 pDC->SelectObject(orangePen);
	 for(int j=0,k=1;j<5;j++){
		/*pDC->Polygon(pb,5);
		pDC->Polygon(ps,5);*/

		//间隔填充
		 pDC->SelectObject(yellowBrush);
		 POINT pp[3] = {{pb[j].x,pb[j].y},{toLeft,toUp},{ps[j].x,ps[j].y}};
		pDC->Polygon(pp,3);
		 
		pDC->SelectObject(orangeBrush);
		 POINT pp2[3] = {{pb[j].x,pb[j].y},{toLeft,toUp},{ps[k].x,ps[k].y}};
		 k = (k+1)%5;
		 pDC->Polygon(pp2,3);
		// pDC->TextOut(ps[k].x,ps[k].y,"x");
	 }
}

void CCG2014112135_1View::myStar(CDC* pDC)
{
		//太极图
	CBrush *blackBrush,*whiteBrush;
	CPen *blackPen,*whitePen;

	//black
	blackBrush = new CBrush();
	blackBrush->CreateSolidBrush(RGB(0,0,0));
	blackPen = new CPen();
	blackPen->CreatePen(PS_SOLID,1,RGB(0,0,0));
	//white
	whiteBrush = new CBrush();
	whiteBrush->CreateSolidBrush(RGB(255,255,255));
	whitePen = new CPen();
	whitePen->CreatePen(PS_SOLID,1,RGB(255,255,255));

	//up circle
	CRect rect(200,200,400,400);
	pDC->SelectObject(blackBrush);
	pDC->SelectObject(blackPen);
	pDC->Pie(&rect,CPoint(400,300),CPoint(200,300));
	//down circle
	pDC->SelectObject(whiteBrush);
	pDC->Pie(&rect,CPoint(200,300),CPoint(400,300));

	//small circle
	CRect rect_left(200,250,300,350);
	pDC->SelectObject(whitePen);
	pDC->Ellipse(rect_left);
	//little circle left
	pDC->SelectObject(blackBrush);
	CRect lr(240,290,260,310);
	pDC->Ellipse(lr);
	//right
//	pDC->SelectObject(blackBrush);
	CRect rect_right(300,250,400,350);
	pDC->SelectObject(blackPen);
	pDC->Ellipse(rect_right);
	//little circle right
	pDC->SelectObject(whiteBrush);
	CRect rr(340,290,360,310);
	pDC->Ellipse(rr);

}

void CCG2014112135_1View::drawRectangle(CDC* pDC)
{
	CBrush *greenBrush;
	CPen *pen;
	pen = new CPen();
	greenBrush = new CBrush();
	greenBrush->CreateSolidBrush(RGB(0,120,120));
	pen->CreatePen(PS_DASH,1,RGB(100,200,300));
	CRect r(600,50,700,100);
	pDC->Rectangle(r);
	CRect r2(630,70,730,120);
	pDC->SelectObject(pen);
	pDC->Rectangle(r2);
	CRect r3(660,100,760,150);
	pDC->SelectObject(greenBrush);
	pDC->Rectangle(r3);

	CBrush *br;
	br = new CBrush();
	br->CreateHatchBrush(HS_CROSS,RGB(100,0,200));
	pDC->SelectObject(br);
	CRect r4(700,130,800,180);
	pDC->Rectangle(r4);
}

void CCG2014112135_1View::drawPolyLine(CDC* pDC)
{
	CBrush *greenBrush;
	CPen *pen;
	pen = new CPen();
	greenBrush = new CBrush();
	greenBrush->CreateSolidBrush(RGB(200,10,120));
	pen->CreatePen(PS_SOLID,2,RGB(100,200,300));

	POINT ps[8] = {{700,50},{750,50},{750,80},{730,20},{700,20},{600,10},{800,100},{750,100}};
	pDC->SelectObject(pen);
	pDC->Polyline(ps,8);
}

void CCG2014112135_1View::drawEclipse(CDC* pDC)
{
	CBrush *greenBrush;
	CPen *pen;
	pen = new CPen();
	greenBrush = new CBrush();
	greenBrush->CreateSolidBrush(RGB(50,10,120));
	pen->CreatePen(PS_DASH,1,RGB(100,200,300));
	CRect r(600,250,700,300);
	pDC->Ellipse(r);
	CRect r2(630,270,730,320);
	pDC->SelectObject(pen);
	pDC->Ellipse(r2);
	CRect r3(660,300,760,350);
	pDC->SelectObject(greenBrush);
	pDC->Ellipse(r3);

	CBrush *br;
	br = new CBrush();
	br->CreateHatchBrush(HS_CROSS,RGB(10,200,200));
	pDC->SelectObject(br);
	CRect r4(700,330,800,400);
	pDC->Ellipse(r4);	
}
