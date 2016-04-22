# A simple Chat in Java

**Developer**:  Daniel Melichar

**Klasse**: 5AHITM

## Aufgabenstellung: A11 - SERVER CLIENT CHAT
Erstellen Sie einen Server-Client-Chat!

* Im ersten Schritt (Grundanforderungen) sollen simple Strings zwischen Server und Client übertragen werden können
* Der Client
    * Übernimmt IP-Adresse und Port des Ziel-Hosts als Kommandozeilenparameter
    * Verbindet sich mithilfe der angegebenen Parameter mit dem Server
    * Liest über die Konsole zeilenweise Benutzereingaben als Strings ein
    * Diese Strings werden an den Server übertragen, welcher sie an alle anderen verbundenen Clients weiterleitet
* Der Server
    * Ist ebenfalls eine Konsolenanwendung und übernimmt den Port als Kommandozeilenparameter
    * Horcht auf eingehende Verbindungen von Clients
    * Startet für neue Clients einen neuen Thread
    * Verwaltet die verbundenen Threads in einer threadsicheren Collection
    * Verteilt empfangene Strings an alle anderen Clients
* Überlegen Sie sich eine geeignete Architektur, sodass Sie die kommenden Erweiterungen problemlos einbauen können
    * Nachrichten können in weiterer Folge nicht nur simple Strings sein, sondern auch serialisierte Objekte
    * Bauen Sie Design Patterns ein: insbesondere eignet sich der Decorator für das Dekorieren von Nachrichten über Streams
* Denken Sie an ein sauberes Exception Handling!
* Dokumentieren Sie Ihren Code!

Viel Spaß!





