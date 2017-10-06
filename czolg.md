
## Czołg

Czołg, jak prawie każdy wie, składa się z opancerzonego pojazdu, wieżyczki z działem, gąsiennic i wielu innych elementów.
Na potrzeby naszej gry uprościmy czołg do dwóch podstawowych elementów:
 * opancerzonego kadłuba
 * wieżyczki z lufą

Nasz czołg będzie reprezentowany przez taką teksturę
![czolg](android/assets/tank.png "Czołg")
zatem klasa `Czolg` dziedziczy po klasie `Sprite`.
```java
public class Czolg extends Sprite {

}
```

Wewnątrz tej klasy deklarujemy również drugiego sprite'a, który będzie reprezentował wieżyczkę.
```java
private final Sprite wiezyczka;
```

Nestępnie dodajemy stałą `SCALE`, która nieco powiększy nasz czołg, aby lepiej był widoczny na scenie.
```java
private static final int SCALE = 5;
```

Nadpisujemy też metodę ```setPosition()```, aby wraz z czołgiem wypozycjonowac również wieżyczkę we właściwym miejscu.
```java
@Override
public void setPosition(float x, float y) {
    super.setPosition(x, y);
    wiezyczka.setPosition(x, y);
}
```

Na koniec dodajemy metodę, która będzie obracać wieżyczkę:
```java
public void obrocWiezyczke(float stopnie) {
    wiezyczka.rotate(stopnie);
}
```