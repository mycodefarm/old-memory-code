// CG2014112135_2View.cpp : implementation of the CCG2014112135_2View class
//

#include "stdafx.h"
#include "CG2014112135_2.h"

#include "CG2014112135_2Doc.h"
#include "CG2014112135_2View.h"
#include "MyCDC.h"
#include <math.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2View

IMPLEMENT_DYNCREATE(CCG2014112135_2View, CScrollView)

BEGIN_MESSAGE_MAP(CCG2014112135_2View, CScrollView)
	//{{AFX_MSG_MAP(CCG2014112135_2View)
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CScrollView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CScrollView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CScrollView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2View construction/destruction

CCG2014112135_2View::CCG2014112135_2View()
{
	// TODO: add construction code here

}

CCG2014112135_2View::~CCG2014112135_2View()
{
}

BOOL CCG2014112135_2View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CScrollView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2View drawing

void CCG2014112135_2View::OnDraw(CDC* pDC)
{
	CCG2014112135_2Doc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDC->TextOut(100,20,"如果中途出现问题，一般是stack overflow");
	LinePractice(pDC);
	CirClePractice(pDC);
	FillPractice(pDC);
	MyTaiji(pDC);
	MyStar(pDC);
}

void CCG2014112135_2View::OnInitialUpdate()
{
	CScrollView::OnInitialUpdate();

	CSize sizeTotal;
	// TODO: calculate the total size of this view
	sizeTotal.cx = sizeTotal.cy = 100;
	SetScrollSizes(MM_TEXT, sizeTotal);
}

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2View printing

BOOL CCG2014112135_2View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CCG2014112135_2View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CCG2014112135_2View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CCG2014112135_2View diagnostics

#ifdef _DEBUG
void CCG2014112135_2View::AssertValid() const
{
	CScrollView::AssertValid();
}

void CCG2014112135_2View::Dump(CDumpContext& dc) const
{
	CScrollView::Dump(dc);
}

CCG2014112135_2Doc* CCG2014112135_2View::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CCG2014112135_2Doc)));
	return (CCG2014112135_2Doc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////

void CCG2014112135_2View::LinePractice(CDC*pDC)
{
	pDC->TextOut(100,50,"DDA");
	((MyCDC*)pDC)->LineDDA(100,100,300,100,RGB(0,255,0));
	((MyCDC*)pDC)->LineDDA(100,100,300,50,RGB(0,255,0));
	((MyCDC*)pDC)->LineDDA(100,100,300,150,RGB(0,255,0));
	((MyCDC*)pDC)->LineDDA(100,100,120,150,RGB(0,255,0));
	((MyCDC*)pDC)->LineDDA(100,100,120,50,RGB(0,255,0));
	//Bresenham画线算法
	pDC->TextOut(100,150,"Bresenham");
	((MyCDC*)pDC)->LineBres(100,200,300,150,RGB(0,155,255));
	((MyCDC*)pDC)->LineBres(100,200,300,200,RGB(0,155,255));
	((MyCDC*)pDC)->LineBres(100,200,300,250,RGB(0,155,255));
	((MyCDC*)pDC)->LineBres(100,200,120,250,RGB(0,155,255));
	((MyCDC*)pDC)->LineBres(100,200,120,150,RGB(0,155,255));
	((MyCDC*)pDC)->LineBres(80,150,100,200,RGB(0,155,255));
	//center画线算法
	pDC->TextOut(100,250,"middle");
	((MyCDC*)pDC)->LineMiddle(100,300,300,300,RGB(200,25,100));
	((MyCDC*)pDC)->LineMiddle(100,300,300,250,RGB(200,25,100));
	((MyCDC*)pDC)->LineMiddle(100,300,300,350,RGB(200,25,100));
	((MyCDC*)pDC)->LineMiddle(100,300,120,350,RGB(200,25,100));
	((MyCDC*)pDC)->LineMiddle(100,300,120,250,RGB(200,25,100));
	((MyCDC*)pDC)->LineMiddle(100,300,100,350,RGB(200,25,100));
	((MyCDC*)pDC)->LineMiddle(100,300,100,250,RGB(200,25,100));
}

void CCG2014112135_2View::MyStar(CDC* pDC)
{
	//五角星
	CPoint pb[5],ps[5];//point big/small
	double RADIAN = 3.1415926/180;//radian
	int toLeft = 1050;//里左边的距离
	int toUp = 200;//里上面的距离
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
	
	 pDC->TextOut(toLeft+10,toUp-150,"My Star");
	 for(int j=0,k=1;j<5;j++){
		//间隔填充
		 POINT pp[3] = {{pb[j].x,pb[j].y},{toLeft,toUp},{ps[j].x,ps[j].y}};
		((MyCDC*)pDC)->ScanFill(pp,3,RGB(255,200,0));
		 
		 POINT pp2[3] = {{pb[j].x,pb[j].y},{toLeft,toUp},{ps[k].x,ps[k].y}};
		 ((MyCDC*)pDC)->ScanFill(pp2,3,RGB(255,81,0));

		//new try--LineBres
		((MyCDC*)pDC)->LineBres(pb[j].x,pb[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineBres(ps[j].x,ps[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineBres(pb[j].x,pb[j].y,ps[j].x,ps[j].y,RGB(255,81,0));

		((MyCDC*)pDC)->LineBres(pb[j].x,pb[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineBres(ps[k].x,ps[k].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineBres(pb[j].x,pb[j].y,ps[k].x,ps[k].y,RGB(255,81,0));


	/*	//new try--DDA
		((MyCDC*)pDC)->LineDDA(pb[j].x,pb[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineDDA(ps[j].x,ps[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineDDA(pb[j].x,pb[j].y,ps[j].x,ps[j].y,RGB(255,81,0));

		((MyCDC*)pDC)->LineDDA(pb[j].x,pb[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineDDA(ps[k].x,ps[k].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineDDA(pb[j].x,pb[j].y,ps[k].x,ps[k].y,RGB(255,81,0));*/

	/*	//new try--middle
		((MyCDC*)pDC)->LineMiddle(pb[j].x,pb[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineMiddle(ps[j].x,ps[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineMiddle(pb[j].x,pb[j].y,ps[j].x,ps[j].y,RGB(255,81,0));

		((MyCDC*)pDC)->LineMiddle(pb[j].x,pb[j].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineMiddle(ps[k].x,ps[k].y,toLeft,toUp,RGB(255,81,0));
		((MyCDC*)pDC)->LineMiddle(pb[j].x,pb[j].y,ps[k].x,ps[k].y,RGB(255,81,0));*/
		k = (k+1)%5;
	 }
}

void CCG2014112135_2View::CirClePractice(CDC* pDC)
{
	int tl = 400,tr=300;
	pDC->TextOut(tl,tr-30,"middle circle");
	pDC->TextOut(tl+150,tr-30,"breshsem circle");
	pDC->TextOut(tl+100,tr+80,"middle ellipse");
	((MyCDC*)pDC)->CircleMidP(tl+50,tr+50,50,RGB(110,110,255));
	((MyCDC*)pDC)->CircleBres(tl+200,tr+50,50,RGB(20,50,255));
	((MyCDC*)pDC)->EllipseMidP(tl+150,tr+200,150,80,RGB(20,155,255));
}

void CCG2014112135_2View::OnLinepractice(CDC* pDC) 
{
	LinePractice(pDC);
}

void CCG2014112135_2View::OnCirclePractice(CDC* pDC) 
{
			
}

void CCG2014112135_2View::FillPractice(CDC *pDC)
{
	int tl=700,tt=30;
	pDC->TextOut(tl+100,tt+20,"scan fill");
	POINT ps[3] = {{tl+50,tt+100},{tl+150,tt+200},{tl+200,tt+50}};
//	POINT ps[3] = {{100,100},{104,98},{104,100}};
	POINT ps2[4] = {{tl+200,tt+100},{tl+250,tt+50},{tl+300,tt+100},{tl+250,tt+150}};
	((MyCDC*)pDC)->ScanFill(ps,3,RGB(100,100,100));
	((MyCDC*)pDC)->ScanFill(ps2,4,RGB(100,10,10));
	
	pDC->TextOut(tl+10,tt+200,"Flood Fill 4");
	((MyCDC*)pDC)->CircleMidP(tl+50,tt+300,60,RGB(20,50,255));
	((MyCDC*)pDC)->FloodFill4(tl+50,tt+300,RGB(20,50,255),RGB(255,255,255));

	pDC->TextOut(tl+150,tt+250,"Boundary Fill 4--stack overflow so small one");
	((MyCDC*)pDC)->CircleMidP(tl+200,tt+300,10,RGB(10,0,0));
	((MyCDC*)pDC)->BoundaryFill4(tl+200,tt+300,RGB(100,100,100),RGB(10,0,0));
}

void CCG2014112135_2View::MyTaiji(CDC *pDC)
{
	int tl=400,tt=50;
	pDC->TextOut(tl,tt,"taiji");
	//big circle
	((MyCDC*)pDC)->CircleBres(tl+100,tt+100,100,RGB(0,0,0));
	//center line
	((MyCDC*)pDC)->LineBres(tl,tt+100,tl+200,tt+100,RGB(0,0,0));
	//fill up circle
	((MyCDC*)pDC)->BoundaryFill4(tl+100,tt+50,RGB(0,0,0),RGB(0,0,0));

	//small circle left
	((MyCDC*)pDC)->CircleBres(tl+50,tt+100,49,RGB(255,255,254));
	//fill
	((MyCDC*)pDC)->BoundaryFill4(tl+50,tt+100,RGB(255,255,255),RGB(255,255,254));
	//more small circle left 
	((MyCDC*)pDC)->CircleBres(tl+50,tt+100,10,RGB(0,0,0));
	((MyCDC*)pDC)->BoundaryFill4(tl+50,tt+100,RGB(0,0,0),RGB(0,0,0));

	//small circle right
	((MyCDC*)pDC)->CircleBres(tl+150,tt+100,49,RGB(0,0,0));
	//fill
	((MyCDC*)pDC)->FloodFill4(tl+150,tt+105,RGB(0,0,0),RGB(255,255,255));
	//more small circle right 
	((MyCDC*)pDC)->CircleBres(tl+150,tt+100,10,RGB(255,255,255));
	((MyCDC*)pDC)->BoundaryFill4(tl+150,tt+100,RGB(255,255,255),RGB(255,255,255));
}
