//
//  runtime.c
//
//
//  Created by Jacob Meyerson on 1/11/17.
//
//

// /opt/parallax/bin/propeller-elf-gcc -Wall -o obj/runtime.o -c runtime.c -lm

#include <stdio.h>
#include <stdlib.h> 
#include <unistd.h>
#include <math.h>
#include <string.h>
#include <ctype.h>

//Global Variables

int w = 0; //0 = Normal, 1 = 2nd, 2 = Alpha
int Second_cnt = 0;
int Alpha_cnt = 0;

double stack[64]; //stack
int cnt = 0; //stack counter
char number[64]; //store numbers from string
char operation[64]; //store operation name from string

char text[64]; //alpha string
char input_type[64]; //input string from buttons

double xcoordinate[64]; //x coordinate
double ycoordinate[64]; //y coordinate
int coordinate_cnt = 0; //coordinate counter to move through array

int button; //button number
double q; //number to push from buttons
double g; //used in operations to push new numbers
double x; //variable on calculator
double k = 15; //number of pixels from bottom of screen

unsigned int LRScroll = 0; //Left/ Right scroll tally
unsigned int UDScroll = 0; //Up/ Down scroll tally

int GoHome = 0; //1 = go to home screen, 0 = don't
int run_yequals = 1; //1 = run, 0 = don't run
int run_limit = 1; //1 = run, 0 = don't run
int run_derivative = 1; //1 = run, 0 = don't run

const double a = .00000001; //limit and derivative
double pi = 3.1415926535; //pi

void (*fncPtr)(); //function pointer for math functions

char fnc[64]; //function string

//Mode Settings
int RadDeg = 1;

//Window Settings
double min_x = -3;
double max_x = 4;
double min_y = -1;
double max_y = 12;
double step_x = .1;
double step_y = .1;
double tick_step_x = 1;
double tick_step_y = 1;

char zero = '0';
char one = '1';
char two = '2';
char three = '3';
char four = '4';
char five = '5';
char six = '6';
char seven = '7';
char eight = '8';
char nine = '9';
char decimal = '.';
char add = '+';
char subtract = '-';
char multiply = '*';
char divide = '/';
char power = "'^';
char Xx = 'x';
char space = ' ';
char logarithm[] = "log";
char ln[] = "ln";
char root[] = "root";
char swap[] = "swap";
char drop[] = "drop";
char roll[] = "roll";
char copy[] = "copy";
char sine[] = "sin";
char cosine[] = "cos";
char tangent[] = "tan";
char sec[] = "sec";
char csc[] = "csc";
char cot[] = "cot";
char arcsin[] = "arcsin";
char arccos[] = "arccos";
char arctan[] = "arctan";
char arcsec[] = "arcsec";
char arccsc[] = "arccsc";
char arccot[] = "arccot";
char togglesign[] = "togglesign";
char store[] = "store";
char functiontograph[] = "functiontograph";
char ee[] = "EE";

char A = 'A';
char B = 'B';
char C = 'C';
char D = 'D';
char E = 'E';
char F = 'F';
char G = 'G';
char H = 'H';
char I = 'I';
char J = 'J';
char K = 'K';
char L = 'L';
char M = 'M';
char N = 'N';
char O = 'O';
char P = 'P';
char Q = 'Q';
char R = 'R';
char S = 'S';
char T = 'T';
char U = 'U';
char V = 'V';
char W = 'W';
char X = 'X';
char Y = 'Y';
char Z = 'Z';
char apostraphe = '\'';
char period = '.';
char quote = '"';
char comma = ',';

char const_array[][32] = {"3.141592653589793", "2.718281828459045", "6.67E-11 N*m*m/kg*kg", "2.99E10 m/s", "6.63E-34 J*s", "6.262E-35 m*m*kg/s", "2.18E-8 kg", "5.39E-44 s", "1.88E-18 C", "1.42E32 K", "sqrt(-1)", "9.109E-31 kg", "1.67E-27 kg", "1.67E-27 kg", "6.022E23 = 1 mol", "-9.8 m/s/s", "14.7 lbs/in*in = 101, 325 Pa"};

char formula_array[][256] = {"V = (4/3)πr(^3)", "V = π(r^2)h", "V = (1/3)π(r^2)h", "V = (1/3)Bh", "x = Vix*t\nΔy = .5a(t^2) + Viy*t\n(Vfy^2) = (Viy^2) + 2gt\ng = (Vfy - Viy)/2", "μ = Ff/Fn", "Fc = m(v^2)/r\na = (v^2)/r", "Fg = (G*m1*m2)/(r^2)", "a/sin(A) = b/sin(B) = c/sin(C)", "(a^2) = (b^2) + (c^2) - 2bc*cos(A)", "E = I * R", "P1 * V1 = P2 * V2", "F = P*A)", "V1 * T2 = V2 * T1", "(sinx)^2 + (cosx)^2 = 1\nsin(a+-b) = sinacosb +- cosasinb\ncos(a+-b) = cosacosb -+ sinasinb\nsin(2x) = 2sinxcosx\ncos(2x) = (cosx)^2 - (sinx)^2\n(sinx)^2 = (1 - cos(2x))/2\n(cosx)^2 = (1 + cos(2x))/2", "d/dx x^n = n * x^(n-1) * dx\nd/dx a^x = a^x * lna * dx\nd/dx lnx = 1/x * dx", "d/dx sinx = cos dx\nd/dx cosx = -sinx dx\nd/dx tanx = (secx)^2 dx\nd/dx cscx = -cscx * cotx dx\nd/dx secx = secx * tanx dx\nd/dx cotx = -(cscx)^2 dx", "∫x^n dx = x^(n+1)/(n+1) + C; n ≠ 1\n∫1/x dx = ln|x| + C\n∫lnx dx = xln|x| - x + C\n∫e^(ax) dx = e^(ax)/a + C", "∫sinx dx = -cosx + C\n∫cosx dx = sinx + C\n∫tanx dx = -ln|cosx| + C\n∫secx dx = ln|secx + tanx| + C\n∫cscx dx = ln|cscx + cotx| + C\n∫cotx dx = ln|sinx| + C"};

char math_function_array[][32] = {"DecimalFraction", "Distance", "Midpoint", "Quadratic", "RadiansDegrees", "DegreesRadians", "lmt", "dydx"};

char DecFrac[] = "DecimalFraction";
char Dist[] = "Distance";
char Mdpt[] = "Midpoint";
char Quad[] = "Quadratic";
char RD[] = "RadiansDegrees";
char DR[] = "DegreesRadians";
char lmt[] = "lmt";
char dydx[] = "dydx";

//Functions
void push (double b);
double pop ();
void Swap ();
void Copy ();
void Drop ();
void Roll ();
void Add ();
void Subtract ();
void Multiply ();
void Divide ();
void Power ();
void Log ();
void Ln ();
void Root ();
void Sin ();
void Cos ();
void Tan ();
void Sec ();
void Csc ();
void Cot ();
void Arcsin ();
void Arccos ();
void Arctan ();
void Arcsec ();
void Arccsc ();
void Arccot ();
void ToggleSign ();
int GetButton ();
void Store ();
void FunctiontoGraph ();
void Graph ();
void Table ();
void DecimalFraction ();
void Distance ();
void Midpoint ();
void Quadratic ();
void RadiansDegrees ();
void DegreesRadians ();
void EE ();
double LeftLimit ();
double RightLimit ();
double Lmt (double L, double R);
void Limit ();
void limit ();
void TheDsCancel ();
void Derivative ();
void DyDx ();
void ConstMenu ();
void MathMenu ();
void ScrollCnt (int scratch);
void Interpreter ();
void Initialize ();
void NormalButton (int btn);
void SecondButton (int btn);
void AlphaButton (int btn);
void UpdateKeyboard ();
void UpdateKernel ();

//calculator functions-------------------------------------------

void push (double b)
{
    stack[cnt] = b;
    cnt++;
    printf("push = %lf\n", b);
}

double pop ()
{
    stack[cnt] = 0;
    double h = stack[cnt-1];
    cnt--;
    return h;
}

void Swap ()
{
    double f = stack[0];
    double k = stack[1];
    stack[0] = k;
    stack[1] = f;
}

void Copy ()
{
    double t = pop();
    push(t);
    push(t);
}

void Drop ()
{
    pop();
}

void Roll ()
{
    double u = pop();
    double v = pop();
    double z = pop();
    push(v);
    push(u);
    push(z);
}

void Add ()
{
    double a = pop();
    double b = pop();
    g = b + a;
    push(g);
}

void Subtract ()
{
    double a = pop();
    double b = pop();
    g = b - a;
    push(g);
}

void Multiply ()
{
    double a = pop();
    double b = pop();
    g = b * a;
    push(g);
}

void Divide ()
{
    double a = pop();
    double b = pop();
    g = b / a;
    push(g);
}

void Power ()
{
    double a = pop();
    double b = pop();
    g = pow(b, a);
    push(g);
}

void Log () //number, then base
{
    double a = pop();
    double b = pop();
    g = log(b)/log(a);
    push(g);
    //printf("Number = %lf\n", g);
}

void Ln () //works
{
    double a = pop();
    g = log(a);
    push(g);
    //printf("Number = %lf\n", g);
}

void Root () //number, then root
{
    double a = pop();
    double b = pop();
    g = pow((b), (1.0/a));
    push(g);
}

void Sin ()
{
    double f = pop();
    printf("pop = %lf\n", f);
    if (RadDeg == 0) //radians
    {
        double y = sin(f);
        push(y);
    }
    if (RadDeg == 1)
    {
        double y = sin((pi*f)/(180));
        push(y);
    }
}

void Cos ()
{
    double f= pop();
    if (RadDeg == 0) //radians
    {
        double y = cos(f);
        push(y);
    }
    if (RadDeg == 1)
    {
        double y = cos((pi*f)/(180));
        push(y);
    }
}

void Tan ()
{
    double f= pop();
    if (RadDeg == 0) //radians
    {
        double y = tan(f);
        push(y);
    }
    if (RadDeg == 1)
    {
        double y = tan((pi*f)/(180));
        push(y);
    }
}

void Sec ()
{
    double f= pop();
    if (RadDeg == 0) //radians
    {
        double y = 1/cos(f);
        push(y);
    }
    if (RadDeg == 1)
    {
        double y = 1/cos((pi*f)/(180));
        push(y);
    }
}

void Csc ()
{
    double f= pop();
    if (RadDeg == 0) //radians
    {
        double p = 1/sin(f);
        push(p);
    }
    if (RadDeg == 1)
    {
        double p = 1/sin((pi*f)/(180));
        push(p);
    }
}

void Cot ()
{
    double f= pop();
    if (RadDeg == 0) //radians
    {
        double y = 1/tan(f);
        push(y);
    }
    if (RadDeg == 1)
    {
        double y = 1/tan((pi*f)/(180));
        push(y);
    }
}

void Arcsin ()
{
    double f= pop();
    double y = asin(f);
    if (RadDeg == 0)
    {
        push(y);
    }
    if (RadDeg == 1)
    {
        double z = (y*180)/(pi);
        push(z);
    }
}

void Arccos ()
{
    double f= pop();
    double y = acos(f);
    if (RadDeg == 0)
    {
        push(y);
    }
    if (RadDeg == 1)
    {
        double z = (y*180)/(pi);
        push(z);
    }
}

void Arctan ()
{
    double f= pop();
    double y = atan(f);
    if (RadDeg == 0)
    {
        push(y);
    }
    if (RadDeg == 1)
    {
        double z = (y*180)/(pi);
        push(z);
    }
}

void Arcsec ()
{
    double f= pop();
    double y = acos(1/f);
    if (RadDeg == 0)
    {
        push(y);
    }
    if (RadDeg == 1)
    {
        double z = (y*180)/(pi);
        push(z);
    }
}

void Arccsc ()
{
    double f= pop();
    double y = asin(1/f);
    if (RadDeg == 0)
    {
        push(y);
    }
    if (RadDeg == 1)
    {
        double z = (y*180)/(pi);
        push(z);
    }
}

void Arccot ()
{
    double f = pop();
    double y = atan(1/f);
    if (RadDeg == 0)
    {
        push(y);
    }
    if (RadDeg == 1)
    {
        double z = (y*180)/(pi);
        push(z);
    }
}

void ToggleSign ()
{
    double a = pop();
    g = (-1) * (a);
    push(g);
    printf("g = %lf\n", g);
}

int GetButton ()
{
    int bttn;
    scanf("%i", &bttn);
    return bttn;
}

void Store ()
{
    double a = pop();
    x = a;
    printf("x == %lf\n", x);
}

void FunctiontoGraph ()
{
    run_yequals = 0;
    double p;
    for (p = min_x; p <= max_x; p = p + step_x)
    {
        x = p;
        Interpreter();
        printf("x = %lf\n", p);
        double l = pop();
        printf("y = %lf\n", l);
        xcoordinate[coordinate_cnt] = p;
        ycoordinate[coordinate_cnt] = l;
        coordinate_cnt++;
    }
    run_yequals = 1;
    Graph();
}

void Graph ()
{
    double delta_x = max_x - min_x;
    double delta_y = max_y - min_y;
    /*
    double ticks_x = (delta_x)/(tick_step_x);
    double ticks_y = (delta_y)/(tick_step_y);
    
    double tick_dist_x = (240)/(ticks_x);
    double tick_dist_y = (160-k)/(ticks_y);
    */
    double one_pix_x = (delta_x)/(240);
    printf("one pixel = %lf units\n", one_pix_x);
    double one_pix_y = (delta_y)/(160-k);
    if (one_pix_x * 240 >= delta_x)
    {
        one_pix_x = one_pix_x + .001;
    }
    if (one_pix_y * (160-k) >= delta_y)
    {
        one_pix_y = one_pix_y + .001;
    }
    
    double center_unit_x = (max_x + min_x)/2;
    double center_unit_y = (max_y + min_y)/2;
    
    int u;
    for (u = 0; u < coordinate_cnt; u++)
    {
        double h = xcoordinate[u];
        double j = ycoordinate[u];
        printf("(%lf, %lf)\n", h, j);
        double pix_x = (h - center_unit_x)/(one_pix_x) + 119;
        double pix_y = (j - center_unit_y)/(one_pix_y) + (((160-k)/2) - 1);
        printf("(%lf, %lf)\n", pix_x, pix_y);
        if (h == 0)
        {
            //draw vertical line
            printf("drawing vertical line\n");
        }
        if (j == 0)
        {
            printf("drawing horizontal line\n");
            //draw horizontal line
        }
        //plot tick marks
    }
    //draw lines in between all of the pixels
}

void Table ()
{
    //print table
}

void DecimalFraction ()
{
    double k = pop();
    int num = (k)*(pow(10, 8));
    int den = pow(10, 8);
    unsigned int h;
    for (h = fmax(num, den); h <= fmax(num, den); h--)
    {
        if (num % h == 0 && den % h == 0)
        {
            double n = (num)/(h);
            double d = (den)/(h);
            printf("%lf/%lf\n", n, d);
            //print number to screen as a fraction
            push((n/d));
            break;
        }
    }
}

void Distance ()
{
    double X2, X1, Y2, Y1, D1, D2, D3;
    Y2 = pop();
    X2 = pop();
    Y1 = pop();
    X1 = pop();
    D1 = (X2 - X1)*(X2 - X1);
    D2 = (Y2 - Y1)*(Y2 - Y1);
    D3 = sqrt((D1+D2));
    printf("D = %lf\n", D3);
}

void Midpoint ()
{
    double X2, X1, Y2, Y1, X3, Y3;
    Y2 = pop();
    X2 = pop();
    Y1 = pop();
    X1 = pop();
    X3 = (X1 + X2)/2;
    Y3 = (Y1 + Y2)/2;
    printf("The midpoint is {%lf, %lf}\n", X3, Y3);
}

void Quadratic ()
{
    double A, B, C, X1, Y1;
    C = pop();
    B = pop();
    A = pop();
    X1 = ((-B) + sqrt((B*B) - (4*A*C))/(2*A));
    Y1 = ((-B) - sqrt((B*B) - (4*A*C))/(2*A));
    printf("x = %lf\nx = %lf\n", X1, Y1);
}

void RadiansDegrees ()
{
    double rad = pop();
    double deg = (180*rad)/(pi);
    printf("%lf degrees\n", deg);
}

void DegreesRadians ()
{
    double deg = pop();
    double rad = (pi*deg)/(180);
    printf("%lf radians\n", rad);
}

void EE ()
{
    double p = pop();
    double j = pop();
    double t = pow(j, (10*p));
    push(t);
}

double LeftLimit ()
{
    double LHL;
    x = x - a;
    //printf("new x = %lf\n", x);
    Interpreter();
    x = x + a;
    double Fx = pop();
    printf("F(x) approaches %lf from the left\n", Fx);
    LHL = Fx;
    return LHL;
}

double RightLimit ()
{
    double RHL;
    x = x + a;
    Interpreter();
    x = x - a;
    double Gx = pop();
    printf("G(x) approaches %lf from the right\n", Gx);
    RHL = Gx;
    return RHL;
}

double Lmt (double L, double R)
{
    double Lim;
    if (fabs(R - L) < 0.1)
    {
        Lim = (L + R)/2;
        printf("Limit as x-> %lf = %lf\n", x, Lim);
    }
    
    else
    {
        printf("The limit does not exist\n");
    }
    return 0;
}

void Limit ()
{
    run_limit = 0;
    double L = LeftLimit();
    double R = RightLimit();
    Lmt(L, R);
    run_limit = 1;
}

void limit ()
{
    strncat(input_type, lmt, 3);
    strncat(input_type, &space, 1);
    GoHome = 1;
}

void TheDsCancel ()
{
    double Hx, Yx;
    double b = x;
    Interpreter();
    Hx = pop();
    x = x - a;
    Interpreter();
    Yx = pop();
    double ddx = (Hx - Yx)/(b - x);
    printf("Dy/dx = %lf\n", ddx);
}

void Derivative ()
{
    run_derivative = 0;
    TheDsCancel();
    run_derivative = 1;
}

void DyDx ()
{
    strncat(input_type, dydx, 4);
    strncat(input_type, &space, 1);
    GoHome = 1;
}

void ConstMenu ()
{
    while (!GoHome)
    {
        int Scratch = GetButton();
        if (Scratch == 45 || Scratch == 46 || Scratch == 47 || Scratch == 48)
        {
            ScrollCnt(Scratch);
        }
        //print cursor at UDScroll value
        if (Scratch == 42)
        {
            //display
            GoHome = 1;
            printf("Going home\n");
        }
        if (LRScroll == 0 && Scratch == 40) //Constants
        {
            printf("UDScroll = %i\n", UDScroll);
            printf ("%s\n", const_array[UDScroll]);
            UDScroll = 0;
        }
        if (LRScroll == 1 && Scratch == 40) //math formulas
        {
            printf("UDScroll = %i\n", UDScroll);
            printf ("%s\n", formula_array[UDScroll]);
            UDScroll = 0;
        }
    }
    GoHome = 0;
}

void MathMenu ()
{
    while (!GoHome)
    {
        int scratchfunct = GetButton();
        if (scratchfunct == 45 || scratchfunct == 46 || scratchfunct == 47 || scratchfunct == 48)
        {
            ScrollCnt(scratchfunct);
        }
        if (scratchfunct == 40)
        {
            printf("UDScroll = %i\n", UDScroll);
            printf ("%s\n", math_function_array[UDScroll]);
            strcpy(fnc, math_function_array[UDScroll]);
            if (!strcmp(fnc, DecFrac))
            {
                fncPtr = &DecimalFraction;
            }
            if (!strcmp(fnc, Dist))
            {
                fncPtr = &Distance;
            }
            if (!strcmp(fnc, Mdpt))
            {
                fncPtr = &Midpoint;
            }
            if (!strcmp(fnc, Quad))
            {
                fncPtr = &Quadratic;
            }
            if (!strcmp(fnc, RD))
            {
                fncPtr = &RadiansDegrees;
            }
            if (!strcmp(fnc, DR))
            {
                fncPtr = &DegreesRadians;
            }
            if (!strcmp(fnc, lmt))
            {
                fncPtr = &limit;
            }
            if (!strcmp(fnc, dydx))
            {
                fncPtr = &DyDx;
            }
            UDScroll = 0;
            (*fncPtr)();
        }
        if (scratchfunct == 42)
        {
            //display home screen
            GoHome = 1;
            printf("Going home\n");
        }
    }
}

void ScrollCnt (int scratch)
{
    switch (scratch)
    {
        case (45): LRScroll--;
            break;
        case (46): UDScroll--;
            break;
        case (47): LRScroll++;
            break;
        case (48): UDScroll++;
            break;
    }
}

void Interpreter ()
{
    memset(operation, 0, sizeof operation);
    int t;
    for (t = 0; t < strlen(input_type); t++)
    {
        char tok = input_type[t];
        
        if (tok != ' ')
        {
            strncat(operation, &tok, 1);
        }
        if (tok == ' ')
        {
            if (atof(operation))
            {
                q = atof(operation);
                push(q);
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, Xx))
            {
                push(x);
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, logarithm))
            {
                Log();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, ln))
            {
                Ln();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, root))
            {
                Root();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, swap))
            {
                Swap();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, drop))
            {
                Drop();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, roll))
            {
                Roll();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, copy))
            {
                Copy();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, sine))
            {
                Sin();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, cosine))
            {
                Cos();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, tangent))
            {
                Tan();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, sec))
            {
                Sec();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, csc))
            {
                Csc();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, cot))
            {
                Cot();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, arcsin))
            {
                Arcsin();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, arccos))
            {
                Arccos();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, arctan))
            {
                Arctan();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, arcsec))
            {
                Arcsec();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, arccsc))
            {
                Arccsc();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, arccot))
            {
                Arccot();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, togglesign))
            {
                ToggleSign();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, store))
            {
                Store();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, add))
            {
                Add();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, subtract))
            {
                Subtract();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, multiply))
            {
                Multiply();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, divide))
            {
                Divide();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, power))
            {
                Power();
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, functiontograph))
            {
                if (run_yequals == 1)
                {
                    FunctiontoGraph();
                }
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, lmt))
            {
                if (run_limit == 1)
                {
                    Limit();
                }
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, dydx))
            {
                printf("here\n");
                if (run_derivative == 1)
                {
                    Derivative();
                }
                memset(operation, 0, sizeof operation);
            }
            if (!strcmp(operation, ee))
            {
                EE();
                memset(operation, 0, sizeof operation);
            }
        }
    }
}

//runtime functions-------------------------------------------
void Initialize ()
{
    char strang[30] = "Fucking Fantastic Calculator\0";
    DisplayText(120, 80, 8, strang);
}

void NormalButton (int btn)
{
    switch (btn)
    {
            //NUMBERS--------------------------------------------
        case (13): strncat(input_type, &seven, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (14): strncat(input_type, &four, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (15): strncat(input_type, &one, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (16): strncat(input_type, &zero, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (21): strncat(input_type, &eight, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (22): strncat(input_type, &five, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (23): strncat(input_type, &two, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (24): strncat(input_type, &decimal, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (29): strncat(input_type, &nine, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (30): strncat(input_type, &six, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (31): strncat(input_type, &three, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (32): strncat(input_type, &space, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (40): strncat(input_type, &space, 1);
            printf("%s\n", input_type);
            Interpreter();
            memset(input_type, 0, sizeof input_type);
            break;
            //OPERATIONS-----------------------------------
        case (1): strncat(input_type, functiontograph, 15);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (2): strncat(input_type, swap, 4);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (4): strncat(input_type, root, 4);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (5): strncat(input_type, logarithm, 3);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (6): strncat(input_type, ln, 2);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (8): strncat(input_type, Xx, 4);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (10): strncat(input_type, copy, 4);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (11): strncat(input_type, sine, 3);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (12): strncat(input_type, arcsin, 6);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (18): strncat(input_type, drop, 4);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (19): strncat(input_type, cosine, 3);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (20): strncat(input_type, arccos, 6);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (26): strncat(input_type, roll, 4);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (27): strncat(input_type, tangent, 3);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (28): strncat(input_type, arctan, 6);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (34): memset(input_type, 0, sizeof input_type);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (35): strncat(input_type, &power, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (36): strncat(input_type, &add, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (37): strncat(input_type, &subtract, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (38): strncat(input_type, &multiply, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (39): strncat(input_type, &divide, 1);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
            DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
            //MENUS-------------------------------------
        case (7): MathMenu();
            break;
    }
}

void SecondButton (int btn)
{
    switch (btn)
    {
        case (5): strncat(input_type, ee, 2);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (7): ConstMenu();
            break;
        case (8): strncat(input_type, store, 5);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (11): strncat(input_type, csc, 3);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (12): strncat(input_type, arccsc, 6);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (19): strncat(input_type, sec, 3);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (20): strncat(input_type, arcsec, 6);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (27): strncat(input_type, cot, 3);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (28): strncat(input_type, arccot, 6);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (32): strncat(input_type, togglesign, 10);
            strcpy(INPUT_TYPE, input_type);
            strncat(INPUT_TYPE, null, 2);
           DisplayText(5, 133, 8, INPUT_TYPE);
            memset(INPUT_TYPE, 0, sizeof INPUT_TYPE);
            break;
        case (42): GoHome = 1;
            //display home screen
            GoHome = 0;
            break;
    }
}

void AlphaButton (int btn)
{
    switch (btn)
    {
        case (2): strncat(text, &A, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (3): strncat(text, &F, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (4): strncat(text, &K, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (5): strncat(text, &P, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (6): strncat(text, &U, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (7): strncat(text, &Z, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (10): strncat(text, &B, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (11): strncat(text, &G, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (12): strncat(text, &L, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (13): strncat(text, &Q, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (14): strncat(text, &V, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (15): strncat(text, &apostraphe, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (18): strncat(text, &C, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (19): strncat(text, &H, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (20): strncat(text, &M, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (21): strncat(text, &R, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (22): strncat(text, &W, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (23): strncat(text, &quote, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (24): strncat(text, &period, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (26): strncat(text, &D, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (27): strncat(text, &I, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (28): strncat(text, &N, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (29): strncat(text, &S, 1);
            strcpy(TEXT, TEXT);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (30): strncat(text, &X, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (31): strncat(text, &comma, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (32): strncat(text, &space, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (34): strncat(text, &E, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (35): strncat(text, &J, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (36): strncat(text, &O, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (37): strncat(text, &T, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
        case (38): strncat(text, &Y, 1);
            strcpy(TEXT, text);
            strncat(TEXT, null, 2);
            DisplayText(5, 133, 8, TEXT);
            break;
    }
}

void UpdateKeyboard ()
{
    int button = GetButton();
    if (button == 43)
    {
        w = 1;
        Second_cnt++;
        if (Second_cnt == 2)
        {
            w = 0;
            Second_cnt = 0;
        }
    }
    if (button == 44)
    {
        w = 2;
        Alpha_cnt++;
        if (Alpha_cnt == 2)
        {
            w = 0;
            Alpha_cnt = 0;
        }
    }
    if (button != 44 && button != 43)
    {
        switch (w)
        {
            case (0): NormalButton(button);
                break;
            case (1): SecondButton(button);
                break;
            case (2): AlphaButton(button);
                break;
        }
    }
}

void UpdateKernel ()
{
    /*
     talk to kernel to read battery voltage and other stuff like that
     */
}

int main (void)
{
    Initialize();
    while (1)
    {
        UpdateKeyboard();
        //UpdateKernel();
    }
}
