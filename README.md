# NotBindingOfIsaac

NotBindingOfIsaac

Gruppen Teilnehmer:
Marcel Otto
Max Wenzel
Ilyas
Fabian Fila (Silva Fila)


Beschreibung.

Die Spielidee lehnt sich an das Konzept von Binding of Isaac an. Der Spieler
steuert den Avatar über die WASD-Tasten und kann magische Geschosse mit einem
linken Mausklick abfeuern, die in die Richtung der Maus fliegen.

Das Ziel des Spiels besteht darin, den versteckten Bossraum zu finden und den Boss zu besiegen. Dieser wird
durch das vollständige Besiegen aller Gegner in einem Raum freigeschaltet, wodurch
Teleporter zu weiteren Bereichen aktiviert werden. Da die Karte bei jedem Spielstart
neu generiert wird, befindet sich der Bossraum stets an einem anderen Ort.

Der Spieler muss darauf achten, nicht zu viele Lebenspunkte zu verlieren, da das Spiel ansonsten endet.
In den Räumen werden jedoch einsammelbare Lebenspunkte-Items generiert, die das Überleben erleichtern können.

Das Spiel umfasst derzeit drei Raumtypen:
Startraum: Keine Gegner, dient als sicherer Ausgangspunkt.
Normale Räume: Erkennbar an ihrem markanten Hintergrunddesign, enthalten Gegner.
Bossraum: Enthält stärkere Gegner, Bossmusik und größere Herausforderungen.

Da sich das Spiel noch im Prototyp-Stadium befindet, fehlen derzeit zusätzliche Raumtypen wie
Item-Shops oder Verbesserungsräume. Diese können jedoch durch ein weiterentwickeltes Kartengenerierungssystem
zukünftig ergänzt werden.



Beschreibung der Rolle/Implementierungen
Marcel Otto:

Ich habe ein einfaches Soundsystem erstellt, das aktuell Hintergrundmusik basierend auf dem
jeweiligen Raumtyp abspielen kann. Da ohne spezielle Bibliotheken oder JavaFX keine einfache
Lautstärkeregelung möglich ist, wird die Musik standardmäßig mit maximaler Lautstärke abgespielt.
Die Lautstärke kann daher nur über die Windows-Systemsteuerung angepasst werden.
Zusätzlich habe ich mich um die Map-Generierung der Räume gekümmert. Die Räume besitzen jeweils
einen Typ: Start, Normal oder Boss. Diese Typen habe ich so gestaltet, dass sie später leicht um
zusätzliche Räume wie Item-Shops oder Verbesserungsräume erweitert werden können. Dabei wird auch
festgelegt, welche Gegner und wie viele davon in einem Raum erscheinen.

Die Karte wird zufällig generiert, wobei jedem Raum ein Typ zugewiesen wird. Das Kartensystem
habe ich als dreidimensionales System entworfen, bei dem jeder Raum bis zu vier Verbindungen in verschiedene
Richtungen (Hoch, Rechts, Unten und Links) haben kann.

Desweiteren hab ich mich um die Stnadard einlesung von BufferedImages für den Avatar und die Hintergrund
Texturen gekümmert.

Max Wenzel:

Ilyas:


Fabian Fila (Silva Fila):

Im Rahmen meiner Arbeit habe ich eine Vielzahl an Refactoring-Maßnahmen durchgeführt,
um den Code besser wartbar und performanter zu gestalten.
Dabei konnte ich die allgemeine Codequalität deutlich verbessern und zahlreiche Bugs beheben,
was zu einer stabileren und angenehmeren Benutzererfahrung führt.

Zusätzlich habe ich zusammen mit Marcel in einer intensiven Peer-Programming-Session an mehreren zentralen Spielmechaniken gearbeitet.
Wir haben gemeinsam das Konzept für das Score-System entworfen und implementiert, Levels entwickelt,
interaktive Objekte sowie animierte Objekte integriert und eine API für die Gegner-Logik entworfen.
Dieser kollaborative Entwicklungsprozess ermöglichte es uns, diese Features effizient umzusetzen und aufeinander abzustimmen.

Neben der Zusammenarbeit habe ich eigenverantwortlich das Gesundheitssystem im Spiel entwickelt.
Dies umfasst nicht nur das Herz-Item, welches dem Spieler ermöglicht sein Leben aufzufüllen,
sondern auch die Healthbar der Gegner.
