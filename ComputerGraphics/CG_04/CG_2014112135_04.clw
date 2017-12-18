; CLW file contains information for the MFC ClassWizard

[General Info]
Version=1
LastClass=CCG_2014112135_04Doc
LastTemplate=CDialog
NewFileInclude1=#include "stdafx.h"
NewFileInclude2=#include "cg_2014112135_04.h"
LastPage=0

ClassCount=12
Class1=CCG_2014112135_04App
Class2=CAboutDlg
Class3=CCG_2014112135_04Doc
Class4=CCG_2014112135_04View
Class5=CGCircleDialog
Class6=CLineDialog
Class7=ListDialog
Class8=CMainFrame
Class9=ScaleDialog
Class10=TranslateDialog

ResourceCount=9
Resource1=IDR_MAINFRAME
Resource2=IDD_DIALOG_Line
Resource3=IDD_DIALOG_Circle
Resource4=IDD_DIALOG_Translate
Resource5=IDD_ABOUTBOX
Resource6=IDD_DIALOG_ListAll
Resource7=IDD_DIALOG_Scale
Class11=RotationDialog
Resource8=IDD_DIALOG_Rotation
Class12=ShearDialog
Resource9=IDD_DIALOG_Shear

[CLS:CCG_2014112135_04App]
Type=0
BaseClass=CWinApp
HeaderFile=CG_2014112135_04.h
ImplementationFile=CG_2014112135_04.cpp

[CLS:CAboutDlg]
Type=0
BaseClass=CDialog
HeaderFile=CG_2014112135_04.cpp
ImplementationFile=CG_2014112135_04.cpp

[CLS:CCG_2014112135_04Doc]
Type=0
BaseClass=CDocument
HeaderFile=CG_2014112135_04Doc.h
ImplementationFile=CG_2014112135_04Doc.cpp
LastObject=ID_Line
Filter=N
VirtualFilter=DC

[CLS:CCG_2014112135_04View]
Type=0
BaseClass=CView
HeaderFile=CG_2014112135_04View.h
ImplementationFile=CG_2014112135_04View.cpp

[CLS:CGCircleDialog]
Type=0
BaseClass=CDialog
HeaderFile=GCircleDialog.h
ImplementationFile=GCircleDialog.cpp

[CLS:CLineDialog]
Type=0
BaseClass=CDialog
HeaderFile=LineDialog.h
ImplementationFile=LineDialog.cpp
LastObject=IDM_Translate

[CLS:ListDialog]
Type=0
BaseClass=CDialog
HeaderFile=ListDialog.h
ImplementationFile=ListDialog.cpp
LastObject=ID_APP_ABOUT
Filter=D
VirtualFilter=dWC

[CLS:CMainFrame]
Type=0
BaseClass=CFrameWnd
HeaderFile=MainFrm.h
ImplementationFile=MainFrm.cpp

[CLS:ScaleDialog]
Type=0
BaseClass=CDialog
HeaderFile=ScaleDialog.h
ImplementationFile=ScaleDialog.cpp

[CLS:TranslateDialog]
Type=0
BaseClass=CDialog
HeaderFile=TranslateDialog.h
ImplementationFile=TranslateDialog.cpp

[DLG:IDD_ABOUTBOX]
Type=1
Class=CAboutDlg
ControlCount=4
Control1=IDC_STATIC,static,1342177283
Control2=IDC_STATIC,static,1342308480
Control3=IDC_STATIC,static,1342308352
Control4=IDOK,button,1342373889

[DLG:IDD_DIALOG_Circle]
Type=1
Class=CGCircleDialog
ControlCount=9
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_STATIC,static,1342308352
Control4=IDC_STATIC,static,1342308352
Control5=IDC_STATIC,static,1342308352
Control6=IDC_EDIT_centerX,edit,1350631552
Control7=IDC_EDIT_centerY,edit,1350631552
Control8=IDC_EDIT_R,edit,1350631552
Control9=IDC_STATIC,static,1342308352

[DLG:IDD_DIALOG_Line]
Type=1
Class=CLineDialog
ControlCount=11
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_EDIT_x1,edit,1350631552
Control4=IDC_STATIC,static,1342308352
Control5=IDC_STATIC,static,1342308352
Control6=IDC_STATIC,static,1342308352
Control7=IDC_STATIC,static,1342308352
Control8=IDC_EDIT_X2,edit,1350631552
Control9=IDC_EDIT_y1,edit,1350631552
Control10=IDC_EDIT_Y2,edit,1350631552
Control11=IDC_STATIC,static,1342308352

[DLG:IDD_DIALOG_ListAll]
Type=1
Class=ListDialog
ControlCount=4
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_LIST_All_A,listbox,1352728835
Control4=IDC_STATIC,static,1342308352

[DLG:IDD_DIALOG_Scale]
Type=1
Class=ScaleDialog
ControlCount=6
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_STATIC,static,1342308352
Control4=IDC_STATIC,static,1342308352
Control5=IDC_EDIT_sx,edit,1350631552
Control6=IDC_EDIT_sy,edit,1350631552

[DLG:IDD_DIALOG_Translate]
Type=1
Class=TranslateDialog
ControlCount=6
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_STATIC,static,1342308352
Control4=IDC_STATIC,static,1342308352
Control5=IDC_EDIT_dx,edit,1350631552
Control6=IDC_EDIT_dy,edit,1350631552

[TB:IDR_MAINFRAME]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_EDIT_CUT
Command5=ID_EDIT_COPY
Command6=ID_EDIT_PASTE
Command7=ID_FILE_PRINT
Command8=ID_APP_ABOUT
CommandCount=8

[MNU:IDR_MAINFRAME]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_FILE_SAVE_AS
Command5=ID_FILE_PRINT
Command6=ID_FILE_PRINT_PREVIEW
Command7=ID_FILE_PRINT_SETUP
Command8=ID_FILE_MRU_FILE1
Command9=ID_APP_EXIT
Command10=ID_EDIT_UNDO
Command11=ID_EDIT_CUT
Command12=ID_EDIT_COPY
Command13=ID_EDIT_PASTE
Command14=ID_VIEW_TOOLBAR
Command15=ID_VIEW_STATUS_BAR
Command16=ID_APP_ABOUT
Command17=ID_Line
Command18=ID_Circle
Command19=IDM_Polygon
Command20=IDM_ListAll
Command21=IDM_Translate
Command22=IDM_Scale
Command23=IDM_Rotation
Command24=IDM_mirrorX
Command25=IDM_mirrorY
Command26=IDM_Mirror0
Command27=IDM_MirrorYX
Command28=IDM_MirrorY_X
Command29=IDM_ShearXY
Command30=IDM_ShearYX
CommandCount=30

[ACL:IDR_MAINFRAME]
Type=1
Class=?
Command1=ID_FILE_NEW
Command2=ID_FILE_OPEN
Command3=ID_FILE_SAVE
Command4=ID_FILE_PRINT
Command5=ID_EDIT_UNDO
Command6=ID_EDIT_CUT
Command7=ID_EDIT_COPY
Command8=ID_EDIT_PASTE
Command9=ID_EDIT_UNDO
Command10=ID_EDIT_CUT
Command11=ID_EDIT_COPY
Command12=ID_EDIT_PASTE
Command13=ID_NEXT_PANE
Command14=ID_PREV_PANE
CommandCount=14

[DLG:IDD_DIALOG_Rotation]
Type=1
Class=RotationDialog
ControlCount=9
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_STATIC,static,1342308352
Control4=IDC_STATIC,static,1342308352
Control5=IDC_STATIC,static,1342308352
Control6=IDC_STATIC,static,1342308352
Control7=IDC_EDIT_RX,edit,1350631552
Control8=IDC_EDIT_RY,edit,1350631552
Control9=IDC_EDIT_RAngle,edit,1350631552

[CLS:RotationDialog]
Type=0
HeaderFile=RotationDialog.h
ImplementationFile=RotationDialog.cpp
BaseClass=CDialog
Filter=D
VirtualFilter=dWC
LastObject=IDC_EDIT_RAngle

[DLG:IDD_DIALOG_Shear]
Type=1
Class=ShearDialog
ControlCount=4
Control1=IDOK,button,1342242817
Control2=IDCANCEL,button,1342242816
Control3=IDC_STATIC,static,1342308352
Control4=IDC_EDIT_Shear,edit,1350631552

[CLS:ShearDialog]
Type=0
HeaderFile=ShearDialog.h
ImplementationFile=ShearDialog.cpp
BaseClass=CDialog
Filter=D
VirtualFilter=dWC
LastObject=IDC_EDIT_Shear

