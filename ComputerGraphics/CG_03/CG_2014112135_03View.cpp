// CG_2014112135_03View.cpp : implementation of the CCG_2014112135_03View class
//

#include "stdafx.h"
#include "CG_2014112135_03.h"

#include "CG_2014112135_03Doc.h"
#include "CG_2014112135_03View.h"
#include <math.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define ColoredVertex(c, v) do{ glColor3fv(c); glVertex3fv(v); }while(0)  
#define MAX_CHAR  128

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03View

IMPLEMENT_DYNCREATE(CCG_2014112135_03View, CView)

BEGIN_MESSAGE_MAP(CCG_2014112135_03View, CView)
	//{{AFX_MSG_MAP(CCG_2014112135_03View)
	ON_WM_CANCELMODE()
	ON_WM_CREATE()
	ON_WM_CAPTURECHANGED()
	ON_WM_DESTROY()
	ON_WM_CHAR()
	ON_WM_SIZE()
	ON_WM_CONTEXTMENU()
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03View construction/destruction

CCG_2014112135_03View::CCG_2014112135_03View()
{
	// TODO: add construction code here

}

CCG_2014112135_03View::~CCG_2014112135_03View()
{
}

BOOL CCG_2014112135_03View::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs
	//���ô�������  
	cs.style |= WS_CLIPCHILDREN | WS_CLIPSIBLINGS; 

	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03View drawing

void CCG_2014112135_03View::OnDraw(CDC* pDC)
{
	CCG_2014112135_03Doc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	// TODO: add draw code for native data here

	//���Ƴ���  
	RenderScene(); 

//	WorkOne();
//	WorkTwo();
}

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03View printing

BOOL CCG_2014112135_03View::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CCG_2014112135_03View::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CCG_2014112135_03View::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03View diagnostics

#ifdef _DEBUG
void CCG_2014112135_03View::AssertValid() const
{
	CView::AssertValid();
}

void CCG_2014112135_03View::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CCG_2014112135_03Doc* CCG_2014112135_03View::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CCG_2014112135_03Doc)));
	return (CCG_2014112135_03Doc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CCG_2014112135_03View message handlers

void CCG_2014112135_03View::OnCancelMode() 
{
	CView::OnCancelMode();
	
	// TODO: Add your message handler code here
	
}

int CCG_2014112135_03View::OnCreate(LPCREATESTRUCT lpCreateStruct) 
{
	if (CView::OnCreate(lpCreateStruct) == -1)
		return -1;
	
	// TODO: Add your specialized creation code here
	
	//��ʼ��OpenGL 
	m_pDC = new CClientDC(this);  //�ڿͻ�����ͼ������豸������DC
	if(!SetupPixelFormat())   
		return -1;  
	m_hGLRC = wglCreateContext(m_pDC->GetSafeHdc());  
	wglMakeCurrent(m_pDC->GetSafeHdc(),m_hGLRC); 

	return 0;
}

void CCG_2014112135_03View::OnCaptureChanged(CWnd *pWnd) 
{
	
	CView::OnCaptureChanged(pWnd);
}

void CCG_2014112135_03View::OnDestroy() 
{
	CView::OnDestroy();
	
	// TODO: Add your message handler code here

	//ɾ����ɫ�塢��Ⱦ������  
	::wglMakeCurrent(NULL,NULL);  
	::wglDeleteContext(m_hGLRC);  
	if(m_hPalette)   
		DeleteObject(m_hPalette);  
	if(m_pDC)   
		delete m_pDC; 
	
}

void CCG_2014112135_03View::OnChar(UINT nChar, UINT nRepCnt, UINT nFlags) 
{
	// TODO: Add your message handler code here and/or call default
	
	CView::OnChar(nChar, nRepCnt, nFlags);
}

void CCG_2014112135_03View::OnSize(UINT nType, int cx, int cy) 
{
	CView::OnSize(nType, cx, cy);
	
	// TODO: Add your message handler code here
	
	//��Ӵ��������ǵ��ӿڱ任  
	glViewport(0,0,cx,cy);
}

void CCG_2014112135_03View::OnContextMenu(CWnd* pWnd, CPoint point) 
{	
}

//my work two
BOOL CCG_2014112135_03View::WorkTwo(void){
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //�����ɫ�������Ȼ���
	glShadeModel(GL_FLAT);
	
	glPushMatrix();
	A0();
	A2();
	glFlush();
//	glPopMatrix();  
	SwapBuffers(m_pDC->GetSafeHdc());
	return TRUE;
}

BOOL CCG_2014112135_03View::A0(void){
	GLfloat 
	PointA[] = {0,0,0},
	PointB[] = {-1,0,0},
	PointC[] = {-0.5,0,sqrt(3.0f)/2},
	PointD[] = {-0.5,3.0/4,sqrt(3.0f)/4};

    GLfloat  
        ColorR[] = { 1, 0, 0 },  
        ColorG[] = { 0, 1, 0 },  
        ColorB[] = { 0, 0, 1 },  
        ColorY[] = { 1, 1, 0 };  

//	glRotatef(60, 0, 1, 0); 
//	glRotatef(30, 1, 0, 0);
//	glRotatef(45, 0, 0, 1);
    glBegin(GL_TRIANGLES);  
    // ƽ��ABC  
    ColoredVertex(ColorR, PointA);  
    ColoredVertex(ColorR, PointB);  
    ColoredVertex(ColorR, PointC);  
    // ƽ��ACD  
    ColoredVertex(ColorB, PointA);  
    ColoredVertex(ColorB, PointC);  
    ColoredVertex(ColorB, PointD);  
    // ƽ��CBD  
    ColoredVertex(ColorY, PointC);  
    ColoredVertex(ColorY, PointB);  
    ColoredVertex(ColorY, PointD);  
    // ƽ��BAD  
    ColoredVertex(ColorG, PointB);  
    ColoredVertex(ColorG, PointA);  
    ColoredVertex(ColorG, PointD);  
    glEnd();   

    glEnable(GL_DEPTH_TEST);  	
	return TRUE;
}

BOOL CCG_2014112135_03View::A2(void){
	
	double h = 0.8;//height
	double b = 0.5;
	double x = 0.5;//center:(0.5,0,0)
	double r = 3.14159/180;
	int i = 0;
	GLfloat point[12][3];
	for(i=0;i<12;i++){
		point[i][0] = x - b*cos(i*60*r);//x
		point[i][1] = 0;//y
		point[i][2] = b*sin(i*60*r);//z
	}
	for(i=6;i<12;i++){
		point[i][1] = h;
	}/*
	{{0,0,0},{0.25,0,sqrt(3.0)/4},{0.75,0,sqrt(3.0)/4},{1,0,0},{0.75,0,-sqrt(3.0)/4},{0.25,0,-sqrt(3.0)/4},
			{0,0.5,0},{0.25,0.5,sqrt(3.0)/4},{0.75,0.5,sqrt(3.0)/4},{1,0.5,0},{0.75,0.5,-sqrt(3.0)/4},{0.25,0.5,-sqrt(3.0)/4}};
	*/
     GLfloat color[6][3] =
	{{0.0f,0.0f,1.0f},
	{0.0f,1.0f,0.0f},
	{1.0f,0.0f,0.0f},
	{1.0f,1.0f,0.0f},
	{1.0f,0.0f,0.0f},
	{0.0f,1.0f,0.0f}}; 
   
	glRotatef(30, 0, 1, 0); 
	glRotatef(30, 1, 0, 0);
//	glRotatef(45, 0, 0, 1);
	
    glBegin(GL_POLYGON);  
    // bottom  
	for(i=0;i<6;i++){
		ColoredVertex(color[0], point[i]);
	}
	glEnd();
	glBegin(GL_POLYGON);
	// up  
	for(i=6;i<12;i++){
		ColoredVertex(color[1], point[i]);
	}
	glEnd();
    //
	
	// else  
	for(i=0;i<6;i+=2){
		glBegin(GL_QUADS);
		ColoredVertex(color[2], point[i]);
		ColoredVertex(color[2], point[i+1]);
		ColoredVertex(color[2], point[i+7]);
		ColoredVertex(color[2], point[i+6]);
		glEnd();
	}
	for(i=1;i<5;i+=2){
		glBegin(GL_QUADS);
		ColoredVertex(color[3], point[i]);
		ColoredVertex(color[3], point[i+1]);
		ColoredVertex(color[3], point[i+7]);
		ColoredVertex(color[3], point[i+6]);
		glEnd();
	}

	glBegin(GL_QUADS);
	ColoredVertex(color[3], point[5]);
	ColoredVertex(color[3], point[0]);
	ColoredVertex(color[3], point[6]);
	ColoredVertex(color[3], point[11]);
	glEnd();
       

    glEnable(GL_DEPTH_TEST);  

	glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(-0.9f, 0.9f);
    drawString("change window size to see");

	return TRUE;
}


//my work
BOOL CCG_2014112135_03View::WorkOne(void){

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //�����ɫ�������Ȼ���
//	glPushMatrix();

	/* draw some points  */
	glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(-0.9f, 0.95f);
    drawString("points");
  glBegin(GL_POINTS);
    glColor3f(1.0,0.0,0.0);
    glVertex2f(-0.9,0.95);
    glColor3f(1.0,1.0,0.0);
    glVertex2f(-0.9,0.9);
    glColor3f(0.0,1.0,1.0);
    glVertex2f(-0.9,0.9);
	glVertex2f(-0.9,0.8);
	glVertex2f(-0.9,0.7);
	glVertex2f(-0.9,0.6);
	glVertex2f(-0.9,0.5);
  glEnd();

  /* draw some line_segments */
  	glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(-0.9f, 0.8f);
    drawString("line");
  glBegin(GL_LINES);
    glColor3f(1.0,1.0,0.0);
    glVertex2f(-0.9,0.8);
    glVertex2f(-0.7,0.7);
    glColor3f(1.0,0.0,1.0);
    glVertex2f(-0.8,0.9);
    glVertex2f(-0.8,0.6);
  glEnd();

  /* draw one closed_line  */
  	
 glBegin(GL_LINE_LOOP);
   glColor3f(0.0,1.0,1.0);
   glVertex2f(0.7,0.7);
   glVertex2f(0.8,0.8);
   glVertex2f(0.9,0.65);
   glVertex2f(1.0,0.75);
   glVertex2f(1.0,0.6);
   glVertex2f(0.75,0.6);
 glEnd();
 glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(0.7f, 0.8f);
    drawString("closed line");

 /* draw one filled_polygon  */
 
 glBegin(GL_POLYGON);
   glColor3f(0.5,0.3,0.7);
   glVertex2f(-0.7,0.2);
   glVertex2f(-0.8,0.3);
   glVertex2f(-1,0.5);
   glVertex2f(-0.75,-0.2);
   glVertex2f(-0.6,-0.1);
 glEnd();
 	glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(-0.7f, 0.2f);
    drawString("filled polygon");

/* draw some filled_quandrangles  */
 	
 glBegin(GL_QUADS);
   glColor3f(0.7,0.5,0.2);
   glVertex2f(0.0,0.2);
   glVertex2f(-0.1,0.3);
   glVertex2f(-0.33,0.5);
   glVertex2f(-0.5,-0.1);

   glColor3f(0.5,0.7,0.2);
   glVertex2f(0.3,0.2);
   glVertex2f(0.2,0.3);
   glVertex2f(0.0,0.5);
   glVertex2f(0.25,-0.1);
 glEnd();
 glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(0.0f, 0.2f);
    drawString("filled quandrangles");

 /* draw some filled_strip_quandrangles  */
 	
 glBegin(GL_QUAD_STRIP);
   glVertex2f(0.6,-0.2);
   glVertex2f(0.55,0.1);
   glVertex2f(0.8,-0.1);

   glColor3f(0.8,0.0,0.0);
   glVertex2f(0.9,0.2);
   glVertex2f(0.9,-0.2);

   glColor3f(0.0,0.0,0.8);
   glVertex2f(0.9,0.2);
   glVertex2f(0.95,-0.1);

   glColor3f(0.0,0.8,0.0);
   glVertex2f(1.0,0.1);
 glEnd();
 glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(0.6f, -0.1f);
    drawString("filled strip quandrangles");

 /* draw some filled_triangles  */
 	
 glBegin(GL_TRIANGLES);
   glColor3f(0.2,0.5,0.7);
   glVertex2f(-0.9,-0.5);
   glVertex2f(-0.95,-0.75);
   glVertex2f(-0.85,-0.6);

   glColor3f(0.2,0.7,0.5);
   glVertex2f(-0.8,-0.7);
   glVertex2f(-0.7,-0.45);
   glVertex2f(-0.55,-0.9);
 glEnd();
 glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(-0.9f, -0.5f);
    drawString("filled triangles");

/* draw some filled_strip_triangles  */
 	
 glBegin(GL_TRIANGLE_STRIP);
   glVertex2f(-0.1,-0.8);
   glVertex2f(-0.25,-0.5);
   glColor3f(0.8,0.8,0.0);
   glVertex2f(0.1,-0.7);
   glColor3f(0.0,0.8,0.8);/*
   glVertex2f(0.2,-0.4);
   glColor3f(0.8,0.0,0.8);
   glVertex2f(0.4,-0.6);*/
 glEnd();
 glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(-0.1f, -0.8f);
    drawString("filled strip triangles");

 /* draw some filled_fan_triangles */
 	
	glBegin(GL_TRIANGLE_FAN);
		glVertex2f(0.7,-0.6);
		glVertex2f(0.8,-0.3);
		glColor3f(0.8,0.2,0.5);
		glVertex2f(0.9,-0.45);
		glColor3f(0.2,0.5,0.8);
		glVertex2f(1.0,-0.75);
		glColor3f(0.8,0.5,0.2);
		glVertex2f(0.85,-0.9);
	glEnd();
	glColor3f(1.0f, 1.0f, 1.0f);
    glRasterPos2f(0.7f, -0.6f);
    drawString("filled fan triangles");
//	glPopMatrix();
	glFlush();
	SwapBuffers(m_pDC->GetSafeHdc());

	return TRUE;
}

BOOL CCG_2014112135_03View::RenderScene(void)
{
	//���� OpenGL ��ͼ��������ͼ�λ���
	//������Ҫ�����޸�,Ҳ���������������ش˺���
	//�������Ի���һ��������
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //�����ɫ�������Ȼ���
	glPushMatrix();
	static GLfloat vtx[6][3] =
	//��������������
	{{0.5f, 0.5f, 0.0f},
	{-0.5f,0.5f, 0.0f},
	{0.0f, 0.5f, 0.5f},
	{0.5f, -0.5f,
	0.0f},{-0.5f,-0.5f, 0.0f},
	{0.0f, -0.5f,
	0.5f}};
	GLfloat color[6][3] =
	//������������ɫ
	{{0.0f,0.0f,1.0f},
	{0.0f,1.0f,1.0f},
	{1.0f,1.0f,1.0f},
	{1.0f,0.0f,1.0f},
	{1.0f,0.0f,0.0f},
	{0.0f,1.0f,0.0f}};
	GLfloat norm[5][3] =
	//���������淨��
	{{0.0f, 1.0f, 0.0f},
	//����
	{0.0f, -1.0f,
	0.0f},
	//����
	{0.0f, 0.0f, -1.0f}, //����
	{0.707f,0.0f, 0.707f}, //���
	{-0.707f,0.0f,0.707f}};//�Ҳ�
	glRotatef(30.0f,1.0f,0.0f,0.0f);
	glBegin(GL_TRIANGLES);
	glNormal3fv(norm[0]); //����
	glColor3fv(color[0]);
	glVertex3fv(vtx[0]);
	glColor3fv(color[1]);
	glVertex3fv(vtx[1]);
	glColor3fv(color[2]);
	glVertex3fv(vtx[2]);
	glNormal3fv(norm[1]); //����
	glColor3fv(color[3]);
	glVertex3fv(vtx[3]);
	glColor3fv(color[5]);
	glVertex3fv(vtx[5]);
	glColor3fv(color[4]);
	glVertex3fv(vtx[4]);
	glEnd();
	glBegin(GL_QUADS);
	glNormal3fv(norm[2]); //����
	glColor3fv(color[0]);
	glVertex3fv(vtx[0]);
	glColor3fv(color[3]);
	glVertex3fv(vtx[3]);
	glColor3fv(color[4]);
	glVertex3fv(vtx[4]);
	glColor3fv(color[1]);
	glVertex3fv(vtx[1]);
	glNormal3fv(norm[3]); //���
	glColor3fv(color[0]);
	glVertex3fv(vtx[0]);
	glColor3fv(color[2]);glVertex3fv(vtx[2]);
	glColor3fv(color[5]);
	glVertex3fv(vtx[5]);
	glColor3fv(color[3]);
	glVertex3fv(vtx[3]);
	glNormal3fv(norm[4]); //�Ҳ�
	glColor3fv(color[2]);
	glVertex3fv(vtx[2]);
	glColor3fv(color[1]);
	glVertex3fv(vtx[1]);
	glColor3fv(color[4]);
	glVertex3fv(vtx[4]);
	glColor3fv(color[5]);
	glVertex3fv(vtx[5]);
	glEnd();
	glPopMatrix();
	glFlush();
	SwapBuffers(m_pDC->GetSafeHdc());
	return TRUE;
}

BOOL CCG_2014112135_03View::SetupPixelFormat(void)
{
	PIXELFORMATDESCRIPTOR pfd = {
		sizeof(PIXELFORMATDESCRIPTOR),
		// pfd �ṹ�Ĵ�С
		1,
		// �汾��
		PFD_DRAW_TO_WINDOW |
		// ֧���ڴ����л�ͼ
		PFD_SUPPORT_OPENGL |
		// ֧�� OpenGL
		PFD_DOUBLEBUFFER,
		// ˫����ģʽ
		PFD_TYPE_RGBA,
		// RGBA ��ɫģʽ
		24,
		// 24 λ��ɫ���
		0, 0, 0, 0, 0, 0,
		// ������ɫλ
		0,
		// û�з�͸���Ȼ���
		0,
		// ������λλ
		0,
		// ���ۼӻ���
		0, 0, 0, 0,
		// �����ۼ�λ
		32,
		// 32 λ��Ȼ���
		0,
		// ��ģ�建��
		0,
		// �޸�������
		PFD_MAIN_PLANE,
		// ����
		0,
		// ����
		0, 0, 0
		// ���Բ�,�ɼ��Ժ������ģ
	};
	int iPixelFormat;
	// Ϊ�豸������õ���ƥ������ظ�ʽ
	if((iPixelFormat = ChoosePixelFormat(m_pDC->GetSafeHdc(), &pfd)) == 0)
	{
		MessageBox("ChoosePixelFormat Failed", NULL, MB_OK);
		return FALSE;
	}
	// ������ƥ������ظ�ʽΪ��ǰ�����ظ�ʽ
	if(SetPixelFormat(m_pDC->GetSafeHdc(), iPixelFormat, &pfd) == FALSE)
	{
		MessageBox("SetPixelFormat Failed", NULL, MB_OK);
		return FALSE;
	}
//	if(pfd.dwFlags & PFD_NEED_PALETTE)
//	SetLogicalPalette();//�����߼���ɫ��
	return TRUE;
}
void CCG_2014112135_03View::SetLogicalPalette(void)
{
	struct
	{
		WORD Version;
		WORD NumberOfEntries;
		PALETTEENTRY aEntries[256];
	} logicalPalette = { 0x300, 256 };
	BYTE reds[] = {0, 36, 72, 109, 145, 182, 218, 255};
	BYTE greens[] = {0, 36, 72, 109, 145, 182, 218, 255};
	BYTE blues[] = {0, 85, 170, 255};
	for (int colorNum=0; colorNum<256; ++colorNum)
	{
		logicalPalette.aEntries[colorNum].peRed =
		reds[colorNum & 0x07];
		logicalPalette.aEntries[colorNum].peGreen =
		greens[(colorNum >> 0x03) & 0x07];
		logicalPalette.aEntries[colorNum].peBlue =
		blues[(colorNum >> 0x06) & 0x03];
		logicalPalette.aEntries[colorNum].peFlags = 0;
	}
	m_hPalette = CreatePalette ((LOGPALETTE*)&logicalPalette);
}

void CCG_2014112135_03View::drawString(const char* str) {
    static int isFirstCall = 1;
    static GLuint lists;

    if( isFirstCall ) { // ����ǵ�һ�ε��ã�ִ�г�ʼ��
                        // Ϊÿһ��ASCII�ַ�����һ����ʾ�б�
        isFirstCall = 0;

        // ����MAX_CHAR����������ʾ�б���
        lists = glGenLists(MAX_CHAR);

        // ��ÿ���ַ��Ļ������װ����Ӧ����ʾ�б���
        wglUseFontBitmaps(wglGetCurrentDC(), 0, MAX_CHAR, lists);
    }
    // ����ÿ���ַ���Ӧ����ʾ�б�����ÿ���ַ�
    for(; *str!='\0'; ++str)
        glCallList(lists + *str);
}
