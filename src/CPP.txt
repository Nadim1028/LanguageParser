#include<iostream>
#include<climits>
#include<algorithm>
using namespace std;

int p[100][100], r[100][100];
int rev(int m, int n)
{
    if(r[m][n]>-1) 
        return r[m][n];

    int x=INT_MIN, y=INT_MIN;

    for(int i=1; i<=m; i++)
        x=max(x, p[i][n]+rev(m-i, n));
    for(int i=1; i<=n; i++)
        y=max(y, p[m][i]+rev(m, n-i));

    return r[m][n]=max(x, y);
}

int main()
{
    int m, n;
    cin>>m>>n;
    for(int i=1; i<=m; i++)
        for(int j=1; j<=n; j++)
            cin>>p[i][j];

    for(int i=0; i<=m; i++)
        r[i][0]=0;
    for(int i=0; i<=n; i++)
        r[0][i]=0;
    for(int i=1; i<=m; i++)
        for(int j=1; j<=n; j++)
            r[i][j]=-1;

    cout<<"Max Revenue: "<<rev(m, n);
}