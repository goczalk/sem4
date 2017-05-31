function[wynik] = m4(A);
n= size(A, 1);
U = zeros(n);
L = eye(n);
t1=tic;
for k=1:n;
  for l = (k+1 ):n;
    m=A(l, k) / A( k, k );
    L(l, k) = m;
    A(l, :) = A(l, : ) - m .*A(k, :);
  end
end
inv(A);
det(A);
trace(A);
eig(A);
A*A;
t=toc(t1)
end


