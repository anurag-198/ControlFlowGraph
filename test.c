void fun(int a, int b, int y)
{
	if (a>5)
        {
		a = 3;
	}
	else
	{
		b = 5;
	}
	switch(a) 
    	{
        case '+':
            printf("%.1f + %.1f = %.1f",num1, num2, num1+num2);
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
            printf("Error! operator is not correct");
            break;
    	}	
	while(y < 20)
	{
		if(y == 3)
		{
			y++;
		}
		else if(y == 5)
		{
			y+=3;
		}
	}
}

