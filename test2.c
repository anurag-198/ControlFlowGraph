int main() 
{
    char o;
    float num1,num2;
    printf("Select an operator either + or - or * or / \n");
    scanf("%c",&o);
    printf("Enter two operands: ");
    scanf("%f%f",&num1,&num2);
    
    int a = (i < 0) ? 4 : 3;
    if (a && b) 
	{
	a = 1;
	b = 2;
   }

    if(num1>num2)
    {
    switch(o) 
    {
        case '+':
            printf("%.1f + %.1f = %.1f",num1, num2, num1+num2); //while()
            break;
        case '-':
            printf("%.1f - %.1f = %.1f",num1, num2, num1-num2);
            break;
        case '*':
            printf("%.1f * %.1f = %.1f",num1, num2, num1*num2);
            break;
        case '/':
            printf("%.1f / %.1f = %.1f",num1, num2, num1/num2);
            break;
        default:
            /* If operator is other than +, -, * or /, error message is shown */
            printf("Error! operator is not correct"); /* sdfksdfsdfj 
		sdfgfgdfgfd */
            break;
    }
    }
    else
    {
    }
 // while()
//{
//	
//}
    return 0;
}
