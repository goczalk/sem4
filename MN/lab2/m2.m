function [wyn] = gauss_bez_wyboru(A, b)           
  n = size(A,1);		           
  A = [A b]; %zlacz wyrazy wolne z macierza wspolczynnikow		            
   
  for i = 1 : n-1 
     for j = i+1 : n
        A(j,:) = A(j,:)-(A(j,i)/A(i,i)*A(i,:);               
      end
  end 
   
  b = A(:,n+1);
  A = A(:,1:n);
  wyn = zeros(n,1);
  
  for i = n : -1 : 1
      wyn(i) = (b(i)-A(i,:)*wyn)/A(i,i);   		     
  end
end




%function[L U] = gauss1(A);
%n = size(A, 1);
%L = eye(n);
%U = zeros(n);
%for i = 1:n
%  for j = (i+1):n
%    k = A(j, i) / A(i, i);
%    L(j, i) = k;
%    A(j, :) = A(j, :) - k.* A(i, :);
%  end
%end
%U = A;