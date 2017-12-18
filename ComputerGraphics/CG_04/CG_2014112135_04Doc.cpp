// CG_2014112135_04Doc.cpp : implementation of the CCG_2014112135_04Doc class
//

#include "stdafx.h"
#include "CG_2014112135_04.h"

#include "CG_2014112135_04Doc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04Doc

IMPLEMENT_DYNCREATE(CCG_2014112135_04Doc, CDocument)

BEGIN_MESSAGE_MAP(CCG_2014112135_04Doc, CDocument)
	//{{AFX_MSG_MAP(CCG_2014112135_04Doc)
	ON_COMMAND(ID_Line, OnLine)
	ON_COMMAND(ID_Circle, OnCircle)
	ON_COMMAND(IDM_Translate, OnTranslate)
	ON_COMMAND(IDM_mirrorX, OnmirrorX)
	ON_COMMAND(IDM_Scale, OnScale)
	ON_COMMAND(IDM_ListAll, OnListAll)
	ON_COMMAND(IDM_mirrorY, OnmirrorY)
	ON_COMMAND(IDM_Mirror0, OnMirror0)
	ON_COMMAND(IDM_MirrorYX, OnMirrorYX)
	ON_COMMAND(IDM_MirrorY_X, OnMirrorY_X)
	ON_LBN_SELCHANGE(IDC_LIST_All_A, OnSelchangeLISTAll)
	ON_COMMAND(IDM_Rotation, OnRotation)
	ON_COMMAND(IDM_Polygon, OnPolygon)
	ON_COMMAND(IDM_ShearXY, OnShearXY)
	ON_COMMAND(IDM_ShearYX, OnShearYX)
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04Doc construction/destruction

CCG_2014112135_04Doc::CCG_2014112135_04Doc()
{
	type = 0;
/*	CGLine *l = new CGLine();
	l->x1 = 100;
	l->x2 = 300;
	l->y1 = 100;
	l->y2 = 300;
	m_all.Add(l);*/

}

CCG_2014112135_04Doc::~CCG_2014112135_04Doc()
{
	int count = m_all.GetSize(),i=0;
	for(i=0;i<count;i++){
		delete m_all.GetAt(i);
	}
}

BOOL CCG_2014112135_04Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}

//===================================================
void CCG_2014112135_04Doc::Draw(CDC* pDC){
	int count = 0,i=0;
	switch(type){
		case 0:
			count = m_all.GetSize();
			for(i=0;i<count;i++){
				((CGShape*)(m_all.GetAt(i)))->Draw(pDC);
			}
			break;
		case 1:
			curr->Draw(pDC);
			break;
	}
}
void CCG_2014112135_04Doc::AddShape(CGShape* s){
	if(s!=NULL){
		type = 0;
		m_all.Add(s);
	}
}



/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04Doc serialization

void CCG_2014112135_04Doc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
		m_all.Serialize(ar);
	}
	else
	{
		// TODO: add loading code here
		m_all.Serialize(ar);
	}
}

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04Doc diagnostics

#ifdef _DEBUG
void CCG_2014112135_04Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void CCG_2014112135_04Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_04Doc commands

void CCG_2014112135_04Doc::OnLine() 
{
	CLineDialog d;
	if(d.DoModal()==IDOK){
		CGLine *line = new CGLine(d.m_x1,d.m_y1,d.m_x2,d.m_y2);
		AddShape(line);
		this->UpdateAllViews(NULL);
	}
}

void CCG_2014112135_04Doc::OnCircle() 
{
	CGCircleDialog d;
	if(d.DoModal()==IDOK){
		CGCircle *c = new CGCircle(d.m_x,d.m_y,d.m_r);
		AddShape(c);
		this->UpdateAllViews(NULL);
	}
}

void CCG_2014112135_04Doc::OnListAll() 
{
	type = 0;
	ListDialog d(&m_all);
	int re = d.DoModal();
	if(re==IDOK){
	//	CString s;
	//	s.Format("index:%d",d.index);
	//	MessageBox(NULL,s,"index",MB_OK);
		int i = d.index;
		if(i>=0){
			type = 1;
			int size = m_all.GetSize();
			curr = (CGShape*)m_all.GetAt(i);
		}
	}
	this->UpdateAllViews(NULL);
}

void CCG_2014112135_04Doc::OnTranslate() 
{
	TranslateDialog d;
	if(d.DoModal()==IDOK){
		int count = 0,i=0;
		switch(type){
			case 0:
				count = m_all.GetSize();
				for(i=0;i<count;i++){
					((CGShape*)(m_all.GetAt(i)))->Translate(d.m_dx,d.m_dy);
				}
				break;
			case 1:
				curr->Translate(d.m_dx,d.m_dy);
				break;
		}
		this->UpdateAllViews(NULL);
	}
}

void CCG_2014112135_04Doc::OnScale() 
{
	ScaleDialog d;
	if(d.DoModal()==IDOK){
		int count = 0,i=0;
		switch(type){
			case 0:
				count = m_all.GetSize();
				for(i=0;i<count;i++){
					((CGShape*)(m_all.GetAt(i)))->Scale(d.m_sx,d.m_sy,0);
				}
				break;
			case 1:
				curr->Scale(d.m_sx,d.m_sy,0);
				break;
		}
		this->UpdateAllViews(NULL);
	}
}

void CCG_2014112135_04Doc::OnmirrorX() 
{
//	int nWidth=GetSystemMetrics(SM_CXSCREEN);  //屏幕宽度    
//	int nHeight=GetSystemMetrics(SM_CYSCREEN); //屏幕高度
//	int cx = GetSystemMetrics(SM_CXFULLSCREEN);
//	int cy = GetSystemMetrics(SM_CYFULLSCREEN);
/*	CRect r;
	GetClientRect(NULL,r);//获得窗体的大小
	CString strScreen;
	strScreen.Format(_T("%d,%d"),r.Width(),r.Height());
	MessageBox(	NULL,strScreen,"ddd",MB_OK);*/
	int count = 0,i=0;
	switch(type){
		case 0:
			count = m_all.GetSize();
			for(i=0;i<count;i++){
				((CGShape*)(m_all.GetAt(i)))->MirrorX();
			}
			break;
		case 1:
			curr->MirrorX();
			break;
	}
	this->UpdateAllViews(NULL);
}

void CCG_2014112135_04Doc::OnmirrorY() 
{
	int count = 0,i=0;
	switch(type){
		case 0:
			count = m_all.GetSize();
			for(i=0;i<count;i++){
				((CGShape*)(m_all.GetAt(i)))->MirrorY();
			}
			break;
		case 1:
			curr->MirrorY();
			break;
	}
	this->UpdateAllViews(NULL);		
}

void CCG_2014112135_04Doc::OnMirror0() 
{
	int count = 0,i=0;
	switch(type){
		case 0:
			count = m_all.GetSize();
			for(i=0;i<count;i++){
				((CGShape*)(m_all.GetAt(i)))->Mirror0();
			}
			break;
		case 1:
			curr->Mirror0();
			break;
	}
	this->UpdateAllViews(NULL);	
}

void CCG_2014112135_04Doc::OnMirrorYX() 
{
	int count = 0,i=0;
	switch(type){
		case 0:
			count = m_all.GetSize();
			for(i=0;i<count;i++){
				((CGShape*)(m_all.GetAt(i)))->MirrorYX();
			}
			break;
		case 1:
			curr->MirrorYX();
			break;
	}
	this->UpdateAllViews(NULL);	
}


void CCG_2014112135_04Doc::OnMirrorY_X() 
{
	int count = 0,i=0;
	switch(type){
		case 0:
			count = m_all.GetSize();
			for(i=0;i<count;i++){
				((CGShape*)(m_all.GetAt(i)))->MirrorY_X();
			}
			break;
		case 1:
			curr->MirrorY_X();
			break;
	}
	this->UpdateAllViews(NULL);		
}

void CCG_2014112135_04Doc::OnSelchangeLISTAll() 
{

}

void CCG_2014112135_04Doc::OnRotation() 
{
	RotationDialog d;
	int i = 0,count = 0;
	if(d.DoModal()==IDOK){
		switch(type){
			case 0:
				count = m_all.GetSize();
				for(i=0;i<count;i++){
					((CGShape*)(m_all.GetAt(i)))->Rotate((double)d.m_rx,(double)d.m_ry,0,d.m_angle);
				}
				break;
			case 1:
				curr->Rotate((double)d.m_rx,(double)d.m_ry,0,d.m_angle);
				break;
		}
		this->UpdateAllViews(NULL);		
	}
}

void CCG_2014112135_04Doc::OnPolygon() 
{
	POINT pp[4] = {{100,100},{200,100},{200,200},{100,200}};
	CGPolygon *p2 = new CGPolygon(pp,4);
	AddShape(p2);
	POINT pp2[5] = {{300,100},{250,150},{280,200},{320,200},{350,150}};
	CGPolygon *p3 = new CGPolygon(pp2,5);
	AddShape(p3);
	this->UpdateAllViews(NULL);
}

void CCG_2014112135_04Doc::OnShearXY() 
{
	ShearDialog d;
	int i = 0,count = 0;
	if(d.DoModal()==IDOK){
		switch(type){
			case 0:
				count = m_all.GetSize();
				for(i=0;i<count;i++){
					((CGShape*)(m_all.GetAt(i)))->ShearX2Y(d.m_angle);
				}
				break;
			case 1:
				curr->ShearX2Y(d.m_angle);
				break;
		}
		this->UpdateAllViews(NULL);		
	}				
}

void CCG_2014112135_04Doc::OnShearYX() 
{
	ShearDialog d;
	int i = 0,count = 0;
	if(d.DoModal()==IDOK){
		switch(type){
			case 0:
				count = m_all.GetSize();
				for(i=0;i<count;i++){
					((CGShape*)(m_all.GetAt(i)))->ShearY2X(d.m_angle);
				}
				break;
			case 1:
				curr->ShearY2X(d.m_angle);
				break;
		}
		this->UpdateAllViews(NULL);		
	}				
}
