# N-királynő megoldása Sosic és Gu 1990-es cikke alapján </br>

A királynőket úgy helyezzük el a táblán hogy minden sorba és minden oszlopba csak egy szerepelhet. A királynők helyének eltárolásához egy egydimenziós tömböt használunk, ahol a tömb elemeinek sorszáma a saktábla sorait és a tömb elemei pedig a saktábla oszlopait tárolják.

Algoritmus:
A királynők kezdő pozicióját egy keveréssel hozzuk létre, majd megnézzük, hogy melyik királynő van támadás alatt. Ezt orvosolva megpróbáljuk úgy kicserélni egy másik királynővel, hogy az ütközések száma csökkenjen. Ez a módszer nem nyújt mindig megoldást, ezért ha egy iteráción keresztül a cserék száma 0 marad és az ütközések száma nem 0, a királynőket tartalmazó tömböt újrakeverjük és nekifutunk mégegyszer. Akkor áll le az algoritmus ha nincsen ütközés a királynők között egyik átlón sem.

A megoldáshoz létrehoztunk egy nxn-es mátrixot, hogy tudjuk kiszámolni a főátlón sé a mellékátlón levő ütközéseket. A Queen klassz célja az erőforrás fölösleges használatának csökkentése. A királynő oszlop száma mellett eltároljuk hogy melyik főátlón és mellékátlón helyezkedik el, ezáltal csökkentve az ütközések keresését.
A rengeteg próbálkozásunk ellenére nem tudtuk elérni a program lefutását több mint 200 királynőre 1 szekundum alatt.

500 kiralynő meghatározáshoz 25 másodpercre van szüksége a jelenlegi tesztelő számítógépnek, ahhoz hogy lefusson rajta.
Teljesítmény 2,3 Ghz (8 mag) 8 generation i5-8300H 
