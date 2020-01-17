Api pod adresem localhost:8080/ wyświetli miejsce do wpisania ciągu znaków z imienia lub nazwiska szukanej osoby.
Kliknięcie przycisku "Szukaj" wyświetli listę osób spełniających kryterium.
Kliknięcie "wygeneruj vCard" spowoduje pobranie pliku vcf o nazwie danego pracownika.

Wywołanie:
localhost:8080/
localhost:8080/search?{name}
localhost:8080/download/{fullName}


METODA		ŻADANIE						      ODPOWIEDŹ
GET				/							          Wyświetla stronę z formularzem wyszukiwania pracowników
GET				/search?{name}		      Wyświetla stronę z listą pracowników
GET       /download/{fullName}    Generuje oraz rozpoczyna pobieranie pliku vcf z danymi wybrajnej osoby 
