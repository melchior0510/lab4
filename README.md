# Лаборатори №4: Нэгжийн тестийн эхлэл — JUnit 5

**Оюутны нэр:** Мөнх-Оргил
**Оюутны код:** B242270130
**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест (2026)

## Ажлын орчин

<<<<<<< HEAD
Ubuntu дээр VS Code ашиглаж хийсэн.

`java -version`:
```
openjdk version "17.0.20.1" 2026-08-18
OpenJDK Runtime Environment (build 17.0.20.1+1-1-26.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.20.1+1-1-26.04-Ubuntu, mixed mode, sharing)
=======
`java -version`:
```
[ӨӨРИЙН ГАРАЛТЫГ ЭНД БУУЛГА]
>>>>>>> b15e2bf79a3aec40db57ec558d480bd523f9bf07
```

`mvn -version`:
```
<<<<<<< HEAD
Apache Maven 3.9.12
Maven home: /usr/share/maven
Java version: 17.0.20.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "7.0.0-31-generic", arch: "amd64", family: "unix"
```

Анх компьютер дээр зөвхөн Java 25-ийн JRE байсан (`javac` байгаагүй) тул `mvn test` нь `release version 17 not supported` гэсэн алдаа өгсөн. `openjdk-17-jdk` суулгаад `update-alternatives`-аар `java`, `javac` хоёрыг 17 болгосны дараа ажилласан.

## Төслийн бүтэц

```
lab04-junit/
├── pom.xml
├── src/main/java/mn/edu/must/sqat/GradeCalculator.java
├── src/test/java/mn/edu/must/sqat/GradeCalculatorTest.java
└── results/
    ├── mvn-test.txt
    └── mvn-test-mutant.txt
=======
[ӨӨРИЙН ГАРАЛТЫГ ЭНД БУУЛГА]
>>>>>>> b15e2bf79a3aec40db57ec558d480bd523f9bf07
```

## Юу хийсэн бэ

<<<<<<< HEAD
Maven төсөл үүсгээд JUnit 5 (5.10.2), surefire 3.2.5 холбож, `maven.compiler.release=17` гэж тохируулсан.

`GradeCalculator` классад 2 метод бичсэн:

- `letterGrade(double score)` — 90 ба түүнээс дээш A, 80–89 B, 70–79 C, 60–69 D, 60-аас доош F. Оноо 0–100-ийн гадуур эсвэл NaN байвал `IllegalArgumentException` шиднэ.
- `totalScore(att, lab, quiz1, quiz2, exam)` — ирц (10), лаб+бие даалт (40), сорил 1 (10), сорил 2 (10), шалгалт (30)-ын оноог нийлүүлж буцаана. Аль нэг нь сөрөг эсвэл өөрийнхөө дээд оноогоос их байвал `IllegalArgumentException` шиднэ.

Тестүүдийг `GradeCalculatorTest` класст бичсэн. Ердийн оноо, хязгаарын утга (0, 59.99, 60, 89.99, 90, 100), буруу оролт гэсэн 3 төрлөөр шалгасан. Тест бүрийг Arrange / Act / Assert гэж тайлбар бичиж хуваасан.

## Тестийн үр дүн

Тестийн метод нийт 17 байгаа: 13 нь энгийн `@Test`, 4 нь `@ParameterizedTest`. Parameterized тестийн мөр бүр тусдаа тест болж тоологддог учраас нийт ажилласан тест 44 болсон (13 + 11 + 5 + 5 + 10).

`mvn test` ажиллуулахад (`results/mvn-test.txt`):
```
[INFO] Running mn.edu.must.sqat.GradeCalculatorTest
[INFO] Tests run: 44, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.252 s -- in mn.edu.must.sqat.GradeCalculatorTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 44, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] BUILD SUCCESS
```

## Мутацийн шалгалт

`letterGrade` доторх `score >= 90` гэснийг зориуд `score > 90` болгож өөрчлөөд `mvn test` ажиллуулсан (`results/mvn-test-mutant.txt`):
```
[ERROR] Failures:
[ERROR]   GradeCalculatorTest.letterGradeBoundaries:138 expected: <A> but was: <B>
[ERROR]   GradeCalculatorTest.ninetyIsExactlyA:36 expected: <A> but was: <B>
[ERROR] Tests run: 44, Failures: 2, Errors: 0, Skipped: 0
[INFO] BUILD FAILURE
```

2 тест уначихсан: `ninetyIsExactlyA` болон `letterGradeBoundaries` (parameterized) доторх `90,A` гэсэн мөр. Хоёулаа яг 90 оноог шалгадаг тест.

Дараа нь `>= 90` болгож буцаагаад дахин `mvn test` ажиллуулахад `BUILD SUCCESS` болсон.

Нэг удаа өөрчлөлтөө хадгалахаа мартаад (Ctrl+S) мутант ажиллуулсан чинь тест бүгд давчихсан. Лог дээр `Nothing to compile - all classes are up to date` гэж байсан нь код өөрчлөгдөөгүйг харуулсан. Хадгалаад дахин ажиллуулсны дараа дээрх алдаа гарсан.

## Дүгнэлт

Мутацийг `ninetyIsExactlyA` болон `letterGradeBoundaries`-ийн 90 гэсэн мөр барьсан. Бусад тестүүд (95, 85, 89.99, 100 гэх мэт) 90-ээс зайтай утга авдаг тул `>=`-ийг `>` болгосон ч давсаар байсан. Хэрвээ би зөвхөн 95, 85, 75 шиг ердийн оноогоор шалгасан бол энэ алдаа баригдахгүй, бүх тест ногоон байх байсан. Тиймээс хязгаарын утгыг тусад нь шалгах нь чухал гэдгийг ойлгосон. `>=` ба `>`-ийн ялгаа зөвхөн яг хязгаар дээр л мэдэгддэг. `@ParameterizedTest` ашиглавал олон хязгаарын утгыг цөөн мөр кодоор шалгаж болох нь тохиромжтой байсан. Мөн ажиллахгүй байхад `java`, `javac`, `mvn` гурав нь яг ижил хувилбар дээр байх ёстойг нэг мэдсэн, JDK-гүй JRE-ээр compile хийж болдоггүй юм байна.
=======
Maven төсөл үүсгэж JUnit 5 (5.10.2), surefire 3.2.5, `maven.compiler.release=17` тохируулсан.
`GradeCalculator` классад `letterGrade` (үсгэн дүн) болон `totalScore` (нийлбэр оноо) метод бичсэн.
Тестүүд нь `GradeCalculatorTest` класст байгаа.

## Тестийн үр дүн

- Тестийн метод: **17** (13 нь `@Test`, 4 нь `@ParameterizedTest`)
- `results/mvn-test.txt`-ийн сүүлийн мөр: **Tests run: [ГАРАЛТААС ХАРЖ БИЧ], Failures: 0, Errors: 0, Skipped: 0**, `BUILD SUCCESS`
- `@CsvSource`, `@ValueSource`-ийн мөр бүр тусдаа тест болж тоологддог тул `Tests run` нь методын 17 гэсэн тооноос их байна.

## Мутацийн шалгалт

`letterGrade` доторх `score >= 90`-ийг зориуд `score > 90` болгосон.
Үр дүн `results/mvn-test-mutant.txt`-д байгаа: `Tests run: [N], Failures: [F]`, `BUILD FAILURE`.
Унасан тест: **[ГАРАЛТААС ХАРЖ ТЕСТИЙН НЭРИЙГ БИЧ]**.
Унасан мэдээлэл: `[expected/but was мөрийг хуулж бич]`.
Дараа нь `>= 90` болгож буцааж засаад `mvn test` дахин ажиллуулахад `BUILD SUCCESS` болсон.

## Дүгнэлт

[ЭНД 5–8 ӨГҮҮЛБЭРИЙГ ӨӨРИЙН ҮГЭЭР БИЧ — доорх асуултад хариул:
- Мутаци аль тестээр баригдсан бэ?
- Хамгийн сонирхолтой алдаа/сюрприз юу байсан бэ?
- Хязгаарын утгыг тусад нь шалгаагүй бол мутаци баригдах байсан уу?
- Энэ лабораас юу сурсан бэ?]
>>>>>>> b15e2bf79a3aec40db57ec558d480bd523f9bf07
