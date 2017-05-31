h0 = 0.92;
H = [];

x0 = 3;

MetProgres = [];
MetCentr = [];

BladProgres = [];
BladCentr = [];

%pochodne
fp1 = 2/x0
fp2 = -2 / x0^2
fp3 = 4/x0^3

for i = 0:40;
  h = h0 / (2 .^ i);
  H = [H h];
  
  metProgres = (log((x0 + h) ^2) - log(x0 ^ 2)) ./ h;
  MetProgres = [MetProgres metProgres];
  
  metCentr = (log((x0 + h) ^2) - log((x0 - h) ^ 2)) ./ (2*h);
  MetCentr = [MetCentr metCentr];
  
  bladProgres = abs(h / 2 * fp2);
  BladProgres = [BladProgres bladProgres];
 
  bladCentr = abs(h ^ 2 / 6 * fp3);
  BladCentr = [BladCentr bladCentr];
endfor;

epsProgres = abs((MetProgres - fp1) ./ fp1);
epsCentr = abs((MetCentr - fp1) ./ fp1);

min(epsProgres)
min(epsCentr)

[mePp mePpIndex] = min(epsProgres);
[mePc mePcIndex] = min(epsCentr);
 
h_opt_progres = H(mePpIndex);
h_opt_centr = H(mePcIndex);



% Richardson
% progresywna
% najdluzsze kroki
Ep11d = MetProgres(2) + (MetProgres(2) - MetProgres(1));
Ep21d = MetProgres(3) + (MetProgres(3) - MetProgres(2));
Ep22d = Ep21d + (Ep21d - Ep11d) / 3;
EpFp22d = abs((Ep22d - fp1) / fp1)
% najkrotsze kroki
Ep11k = MetProgres(end-1) + (MetProgres(end-1) - MetProgres(end));
Ep21k = MetProgres(end-2) + (MetProgres(end-2) - MetProgres(end-1));
Ep22k = Ep21k + (Ep21k - Ep11k) / 3;
EpFp22k = abs((Ep22k - fp1) / fp1)

% centralna
% najdluzsze kroki
Ec11d = MetCentr(2) + (MetCentr(2) - MetCentr(1)) / 3;
Ec21d = MetCentr(3) + (MetCentr(3) - MetCentr(2)) / 3;
Ec22d = Ec21d + (Ec21d - Ec11d) / 15;
EcFc22d = abs((Ec22d - fp1) / fp1)
% najkrotsze kroki
Ec11k = MetCentr(end-1) + (MetCentr(end-1) - MetCentr(end)) / 3;
Ec21k = MetCentr(end-2) + (MetCentr(end-2) - MetCentr(end-1)) / 3;
Ec22k = Ec21k + (Ec21k - Ec11k) / 15;
EcFc22k = abs((Ec22k - fp1) / fp1)

% wykres 
loglog(H, epsProgres, 'r.', H, epsCentr, 'go', H, BladProgres, 'b', H, BladCentr, 'k');
title('Zaleznosci bledow od dlugosci kroku i metody');
xlabel('Dlugosc kroku (h)');
ylabel('Blad');

hold on;
loglog([H(1) H(end)],[EpFp22d EpFp22d], 'b--');
loglog([H(1) H(end)],[EpFp22k EpFp22k], 'r--');
loglog([H(1) H(end)],[EcFc22d EcFc22d], 'k--');
loglog([H(1) H(end)],[EcFc22k EcFc22k], 'g--');
legend('Blad wzgledny roznicy Progresywnej','Blad wzgledny roznicy Centralnej',
'Blad odciecia roznicy Progresywnej','Blad odciecia roznicy Centralnej',
'Blad wzgledny roznicy Progresywnej przy ekstrapolacji Richardsona dla trzech pierwszych wartosci',
'Blad wzgledny roznicy Progresywnej przy ekstrapolacji Richardsona dla trzech ostatnich wartosci',
'Blad wzgledny roznicy Centralnej przy ekstrapolacji Richardsona dla trzech pierwszych wartosci',
'Blad wzgledny roznicy Centralnej przy ekstrapolacji Richardsona dla trzech ostatnich wartosci', 
"location", "southeast");  


