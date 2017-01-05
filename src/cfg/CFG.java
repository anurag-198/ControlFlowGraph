package cfg;

import java.io.*;
import java.util.*;

public class CFG {
    static File fp = new File("test1.c");
    
    static PrintWriter pw;
    static Scanner sc;
    static int[][] adj = new int[100][100];
    static int vis[] = new int[100];
    static String arr[] = new String[100];
    static int id = 0;
    static int count = 0;
    static String[] stmt = new String[100];
    static Stack st = new Stack();
    static int INF = 10000007;
    static int skip = 0;
    static String str = "";
    
    static void module(Vector v)
    {
        int pr;
        
        str = "";
        
        while (true) {
            if (skip == 1) 
                skip = 0;
            
            else 
                str = sc.nextLine();
            
            if (str.contains("{") || str.equals(""))
                continue ;
            
            if (str.contains("}")) {
                adj[id - 1][id] = 0;
                //System.out.println("module  " + (id-1));
                
                //st.push(id - 1);
                
                return ;
            }
            
            if (str.contains("if")) {
                stmt[id] = str;
                
                adj[id][id + 1] = 1;
                
                id++;
                fnc_if(id - 1);
            }
            
            else if (str.contains("while") || str.contains("for")) {
                stmt[id] = str;
                adj[id][id + 1] = 1;
                pr = id;

                id++;

                fnc_while(id - 1);

                //adj[pr][id] = 1;
            }
            
            else if (str.contains("switch")) {
                stmt[id] = str;
                pr = id;
                
                fnc_switch(pr);
            }
            
            else {
                stmt[id] = str;
                
                adj[id][id + 1] = 1;
                
                id++;
                
                str = sc.nextLine();
                skip = 1;
                
                if (str.contains("}")) {
                    adj[id - 1][id] = 0;
                    st.push(id - 1);
                    
                    return ;
                }
            }
        }
    }
    
    static void fnc_if(int pr)
    {
        
        Vector v = new Vector();
        
        st.push(INF);
        module(v);
        
        //st.push(id - 1);
        
        str = sc.nextLine();
        skip = 1;
        
        while (str.contains("else") && str.contains("if")) {
            skip = 0;
            
            adj[pr][id] = 1;
            pr = id;
            
            stmt[id] = str;
            adj[id][id + 1] = 1;
            id++;
            
            module(v);
            
            st.push(id - 1);
            
            str = sc.nextLine();
            skip = 1;
        }
        
        if (str.contains("else")) {
            skip = 0;
            adj[pr][id] = 1;
            pr = -1;
            
            module(v);
            
            st.push(id - 1);
            
            str = sc.nextLine();
            skip = 1;
        }
        
        if (pr != -1)
            st.push(pr);
        
        //System.out.println("aasaa " + id);
        if (str.contains("}")) {
            int t1;
            
            while (true) {
                t1 = (int) st.pop();
                
                if (t1 == INF)
                    break ;
                
                v.addElement(t1);
            }
            
            int i;
            for (i = 0; i < v.size(); i++) {
                //System.out.println((int)v.elementAt(i));
                st.push((int)v.elementAt(i));
            }
            
            return ;
        }
        int t1;
        while (true) {
            
            t1 = (int)st.pop();
            //System.out.println("stack " + t1);
            if (t1 == INF)
                break ;
            
            adj[t1][id] = 1;
        }
    }
    
    static void fnc_switch(int pr)
    {
        Vector v = new Vector();
        
        //System.out.println("switch");
        
        st.push(INF);
        
        while (true) {
            str = sc.nextLine();
            
            if (str.contains("{"))
                continue ;
            
            if (str.contains("}"))
                break ;
            
            adj[pr][id] = 1;
            
            module(v);
            
            //st.push(id - 1);
        }
        
        while (true) {
            int t1 = (int) st.pop();
            
            if (t1 == INF)
                break ;
            
            adj[t1][id] = 1;
        }
    }
    
    static void fnc_while(int pr)
    {
        Vector v = new Vector();
        
        st.push(INF);
        
        module(v);
        
        //st.push(id - 1);
        
        while (true) {
            int t1 = (int)st.pop();
           // System.out.println("st " + t1);
            if (t1 == INF)
                break;
            
            adj[t1][pr] = 1;
        }
        
        st.push(pr);
    }
    
    public static void indPath(int root, int n, int path_length)
    {
        
        if(root >= n) {
            printarray(path_length);
            return;
        }        
        else if (vis[root] == 1 ) {
            arr[path_length] = stmt[root].trim();
            printarray(path_length+1);
            return;
        }
        else {
            for(int j = 0; j < n; j++) {
                if(adj[root][j] == 1) {
                    arr[path_length] = stmt[root].trim();
                    vis[root] = 1;
                    indPath(j, n, path_length + 1);
                    vis[root] = 0;
                }
            }
            if(root == n - 1) {
                printarray(path_length);
            }
        }        
    }
    
    public static void printarray(int n)
    {
        count ++;
        System.out.print(count + ")\t");
        for(int i = 0 ; i < n; i++) {
            System.out.print(arr[i] + " ====> ");
        }
        System.out.println("End");
    }
    
    public static String transformforand(String[] string, int no)
    {
        int j = 0;
        String ans;
        String b = "";
        ans = string[0].substring(0,string[0].indexOf("&&"));
        ans += ")\n";
        ans += "{\n";
        ans += "    " + "if (" + string[0].substring(string[0].indexOf("&&") + 2, string[0].length() - 1) + ")\n";
        b += "{\n";
        
        for (j = 2; j < no; j++) {
            b += string[j] + "\n";
        }
        b += "}\n";
        
        ans += b;
        return ans;
        
    }
    
     public static String transform(String string)
    {
        int questionMark = string.indexOf("?");
        int colon = string.indexOf(":", questionMark);

        if (questionMark == -1 || colon == -1)
        {
            return string;
        }

        String condition = string.substring(0, questionMark);
        String expressions = string.substring(questionMark + 1, string.length());
        String trueExpression = null;
        String falseExpression = null;

        // While looking in pairs, find the location where the colon occurs before the question mark.
        questionMark = expressions.indexOf("?");
        colon = expressions.indexOf(":");
        while ((questionMark != -1 && colon != -1) && (questionMark < colon))
        {
            questionMark = expressions.indexOf("?", questionMark + 1);
            colon = expressions.indexOf(":", colon + 1);
        }


        trueExpression = expressions.substring(0, colon);
        falseExpression = expressions.substring(colon + 1, expressions.length());

 

        return ("if (" + condition + ") \n{\n" + transform(trueExpression) + "\n} \nelse \n{\n" + transform(falseExpression) + "\n}");
    }
    
    public static void main(String args[]) throws FileNotFoundException {
      
      Scanner sc1 = new Scanner(fp);
      pw = new PrintWriter("output.c");
      int flag = 0;
      while (sc1.hasNextLine()) {
          String str = sc1.nextLine();
          System.out.println(str);
          if(flag == 1 && !str.contains("*/")){
                continue;
          }
          else if(str.contains("*/")){
              flag = 0;
              continue;
          }
          if (str.contains("//")) {
            if (str.indexOf("//") == 0) {
                  continue;
            }
            else {
                  String str1 = str.substring(0, str.indexOf("//"));
                  pw.print(str1);
                  pw.print("\n");
            }
          }
          else if(str.contains("/*") && str.contains("*/")){
             // continue;
              if(str.indexOf("/*") == 0){
                continue;
              }
              else{
                 String str1 = str.substring(0,str.indexOf("/*"));
                  if(str1.trim().length() == 0){
                      continue;
                  }
                
                  pw.print(str1);
                  pw.print("\n");
              }
          }
          else if(str.contains("/*")) {
              flag = 1;
              continue;
          }
          else {
            pw.print(str);
            pw.print("\n");
          }
     }
     
      
      pw.close();
      
      File f3 = new File("output.c");
      Scanner sc2 = new Scanner(f3);
      
      PrintWriter pw1 = new PrintWriter("output1.c");
      
      while (sc2.hasNextLine()) {
          String newln = sc2.nextLine();
          System.out.println(newln);
          if (newln.contains("?") && (newln.contains(":"))) {
              pw1.print(transform(newln));
              continue;
          }
          else if ((newln.contains("&&")) && (newln.contains("if"))) {
              //String line = newln + "\n";
              String[] line = new String[100];
              line[0] = newln;
              int no = 1;
              while (sc2.hasNextLine()) {
               String part = sc2.nextLine();               
               if (part.contains("}")) {
                   line[no++] = part;
                   break;
               }
               line[no++] = part;
              }
              
        
              String val = "\n" + transformforand(line, no);
              pw1.print(val);
              continue;
          }
          else {
              pw1.println(newln);
          }
      }

      pw1.close();
      
      File fp1 = new File("output1.c");
      sc = new Scanner(fp1);
      int pr;
      while (true) {
          if (skip == 1)
              skip = 0;
          
          else {
              if (!sc.hasNextLine())
                  break ;
       
             str = sc.nextLine();
          }
          
          
          
          if (str.contains("}"))
              break ;
          
          if (str.contains("if")) {
              stmt[id] = str;
              pr = id;
              
              adj[id][id + 1] = 1;
              
              id++;
              fnc_if(pr);
          }
          
          else if (str.contains("while") || str.contains("for")) {
              stmt[id] = str;
              adj[id][id + 1] = 1;
              pr = id;
              
              id++;
              
              fnc_while(id - 1);
              
              adj[pr][id] = 1;
          }
          
          else if (str.contains("switch")) {
              stmt[id] = str;
              pr = id;
              
              id++;
              fnc_switch(pr);
          }
          
          else {
              if(!str.contains("{") && !str.matches("^\\s*$") && !str.contains("void")){
                stmt[id] = str;

                adj[id][id + 1] = 1;

                id++;
              }
          }
      }
      
      int i, j;
      stmt[id ] = "End";
      System.out.println("Nodes:-\n");
      for (i = 0; i < id + 1; i++)
          System.out.println(i + " => " + stmt[i].trim());
      System.out.println("\n Adjacency Matrix: - \n");
      
      for (i = 0; i < id + 1; i++) {
          System.out.print(i + "\t");
          for (j = 0; j < id + 1; j++) {
              System.out.print(adj[i][j] + " ");
          }
          System.out.println("");
      }
      
      int c = 0;
      for(i = 0; i < id + 1; i++)
          for(j = 0; j < id + 1; j++)
              if(adj[i][j] == 1) {
                  c++;
                  break;
              }
      
      //System.out.println("\n\nNumber of Independent Path = " + c);
      System.out.println("\nPaths:-");
      int root_index = 0;
      for(i = 0; i < id + 1; i++)
          if(stmt[i].contains("if") || stmt[i].contains("for") || stmt[i].contains("while") || stmt[i].contains("switch")){
              root_index = i;
              break;
          }
      //System.out.println(id+1);
      indPath(root_index, id + 1, 0);
      
    }
    
    
}
