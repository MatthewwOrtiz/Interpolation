

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {

	static int N=0;
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		String num ="";
		int c=0,d=0,check=0;
		//number of x's
		int n=4;
		System.out.print("Please enter the number of elements: ");
		n=in.nextInt();
		System.out.println("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
		N=n;
		double[] x = new double[n];String xString = ""; String yString="";
		double[][] y = new double[n*2][n*2];

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("input.txt"));
			String line = reader.readLine();
			while (line != null) {
				if (check==0){xString=line;}
				if (check==1){yString=line;}
				line = reader.readLine();
				check = 1;

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String remove = xString + ' ';
		for(int i=0;i<remove.length();i++)
		{
			if(c>=n){break;}
			if (remove.charAt(i)!=' '){num=num+remove.charAt(i);}
			if(remove.charAt(i)==' '){if(num==""){continue;}x[c]=Double.parseDouble(num); num="";c++;}
		}
		
		
		remove = yString + ' '; 
		for(int i=0;i<remove.length();i++)
		{	if(d>=n*2){break;}
			if (remove.charAt(i)!=' '){num=num+remove.charAt(i);}
			if(remove.charAt(i)==' '){if(num==""){continue;}y[d][0]=Double.parseDouble(num); num="";d++;}
		}
		
		ddifference(x,y,n);
		printTable(y,n,x);
		System.out.println();
		System.out.println("Newton's Form");
		printInter(y,x);
		System.out.println();
		System.out.println();
		System.out.println("Simplified Form");
		printPoly(poly(y,x),n);
		System.out.println();
		System.out.println();
		System.out.println("Lagrange Form");
		lagrange(y,x);
		
	}
	
	public static void ddifference(double a[], double b[][], int n)
	{
		for(int i =1; i<a.length;i++)
		{
			for(int j=0;j<a.length-i;j++)
			{
				b[j][i] = (b[j][i-1]-b[j+1][i-1])/(a[j]-a[i+j]);
			}
		}	
	}
	
	static double form(double v, double a[], double b[][])
	{
		double s = b[0][0];
		
		for(int i=1;i<b.length;i++)
		{
			s=s+(term(i,v,a)*b[0][i]);
		}
		return s;
	}
	
	static double term(int i, double v, double a[])
	{
		double p =1;
		for(int j=0; j<i;j++)
		{
			p=p*(v-a[j]);
		}
		return p;
	}
		
	static void printTable(double a[][],int n,double b[])
	{

		System.out.print("x");
		System.out.print("\t");
		for (int i=0;i<n;i++)
		{
			System.out.print("f"+i);
			System.out.print("\t");
		}
		
		System.out.println();
		System.out.println("______________________________________________");
		
		
		for (int i=0;i<n;i++)
		{
			System.out.print(b[i]);
			System.out.print("\t");
			for(int j=0;j<n-i;j++)
			{
				System.out.printf("%.2f", a[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("______________________________________________");
	}
	
	static void lagrange(double a[][], double b[])
	{
		int count =0;
		for(int i=0;i<b.length;i++)
		{
			double l=1;
			for(int j=0; j<b.length;j++)
			{
				if(j==i){continue;}
				l=l*(b[i]-b[j]);
				double yes = b[i]+b[j];
				System.out.print("(x-" + b[j]+")");
			}
			l=a[i][0]/l;
			System.out.print("(");System.out.printf("%.2f", l);System.out.print(")");
			if(i!=b.length-1){System.out.print("+");}

		}	
	}
	
	static void printInter(double a[][],double b[])
	{
		int c=3;
		for (int i=0;i<b.length;i++)
		{
			System.out.printf("%.2f",a[0][i]);
			
			for(int j=0;j<b.length-(b.length-i);j++)
			{
				if(i>=1){
				System.out.print("(x-" + b[j]+")");}
				c--;
			}
			if(i<b.length-1){System.out.print("+");}
		}
	}

	static double[] multi(double a[],double b[])
	{int total=a.length+b.length-1;
		  double[] p = new double[total];
		   
		   for (int i = 0; i<total; i++) 
		     p[i] = 0; 
		   for (int i=0; i<a.length; i++) 
		   { 
		     for (int j=0; j<b.length; j++) 
		         p[i+j] += a[i]*b[j]; 
		   } 
		  
		   return p;
		   }

	static double[] add(double a[],double b[])
	{
		int total=a.length+b.length-1;
		  double[] p = new double[total];
		   for (int i = 0; i<total; i++) 
		     p[i] = 0; 
		   
		   for (int i=0; i<N; i++) 
		   { 
		     p[i] = a[i]+b[i];
		   } 
		  
		   return p;
		   }
	
	static double[] poly(double a[][],double b[])
	{		 
		  double[] p = new double[b.length];
		  double[] q = new double[b.length];q[0]=b[0]*-1;q[1]=1;
		  double[] y = new double[b.length];
		  double[] xTotal= new double [b.length];
		  
		  //first pass
		  xTotal[0]=a[0][0];
			 //second pass
			 p[0] = a[0][1];
			 y=multi(q,p);
		  y=multi(q,p);

		  xTotal=add(xTotal,y);

		  for (int i = 1; i<b.length-1; i++) 
		   {  
			 double t = b[i]*-1;
			 double[] nextX={t,1};
			 q = multi(q,nextX); 
			 p[0] = a[0][i+1];
			 y=multi(q,p);
			 xTotal=add(xTotal,y);
		   }
		
		return xTotal;
	}
	
	static void printPoly(double poly[], int n) 
	{ 
	    for (int i=0; i<n; i++) 
	    { 
	    	System.out.printf("%.2f",poly[i]);
	       if (i != 0) 
	        System.out.print("x^" + i) ; 
	       if (i != n-1) 
	       System.out.print(" + "); 
	    } 
	} 	
		
	}
