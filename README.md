Api pod adresem localhost:8080/ wyświetli miejsce do wpisania ciągu znaków z imienia lub nazwiska szukanej osoby.
Kliknięcie przycisku "Szukaj" wyświetli listę osób spełniających kryterium.
Kliknięcie "wygeneruj vCard" spowoduje pobranie pliku vcf o nazwie danego pracownika.

Wywołanie:

- localhost:8080/
- localhost:8080/search?{name}
- localhost:8080/download/{fullName}

żądania:

- /    			-   wyświetla stronę z formularzem wyszukiwania pracowników
- /search?{name}  	-  Wyświetla stronę z listą pracowników
- /download/{fullName}    -  Generuje oraz rozpoczyna pobieranie pliku vcf z danymi wybrajnej osoby 
