Głównym celem tego zadania jest podział przedmiotów znajdujących się w koszyku klienta na grupy dostaw.

Danymi wejściowymi dla programu są:

    Definicje wszystkich dostępnych sposobów dostawy dla oferowanych w sklepie produktów.
    Koszyk z kombinacją wybranych produktów zdefiniowanych w konfiguracji.

Spodziewany wynik:

Algorytm w wyniku ma zwrócić podział produktów na grupy dostawy w postaci mapy. Kluczem w mapie będzie sposób dostawy, a wartością lista produktów. Oczekujemy, że:

    Algorytm dzieli produkty na możliwie minimalną liczbę grup dostaw.
    Największa grupa zawiera możliwie najwięcej produktów.

W zadaniu zdecydowałem się na użycie algorytmu zachłannego. Po przeprowadzonych testach wydaje się, że daje on poprawne wyniki i nie blokuje się w minimach lokalnych.

Do budowy projektu używam Gradle. Aby zbudować fat-jar, wystarczy wykonać task Gradle o nazwie jar.