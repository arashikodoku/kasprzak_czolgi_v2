
## Czołg

Czołg, jak prawie każdy wie, składa się z opancerzonego pojazdu, wieżyczki z działem, gąsiennic i wielu innych elementów.
Na potrzeby naszej gry uprościmy czołg do dwóch podstawowych elementów:
 * opancerzonego kadłuba
 * wieżyczki z lufą

Nasz czołg będzie reprezentowany przez taką teksturę
![czolg](android/assets/tank.png "Czołg")
zatem klasa `Czolg` dziedziczy po klasie `Sprite`.
```
public class Czolg extends Sprite {

}
```

Wewnątrz tej klasy deklarujemy również drugiego sprite'a, który będzie reprezentował wieżyczkę.
```
private final Sprite wiezyczka;
```

Na koniec dodajemy metodę, która będzie obracać wieżyczkę:
```
public void obrocWiezyczke(float stopnie) {
    wiezyczka.rotate(stopnie);
}
```