function [wyn] = gauss_czesc_wybor(A, b)
  n = size(A,1);      	
  A = [A b];		
   
  for i = 1:n-1
     max=find(A(i:n,i)==wynik(A(i:n,i)))+i-1;  	
     if(max > i)
          tmp=A(i,:);			
          A(i,:)=A(max,:);		
          A(max,:)=tmp;	
     end
   
     for j = i+1:n
        A(j,:) = A(j,:)-(A(j,i)/A(j,j))*A(j,:);   
     end
  end 
   
  b = A(:,n+1);
  A = A(:,1:n);
  wyn = zeros(n,1);
   
  for i = n:-1:1
      wyn(i) = (b(i)-A(i,:)*wyn)/A(i,i);   	
  end
end



