package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class GradeCalculatorTest {

    // ---------- letterGrade: ердийн болон хязгаарын утгууд ----------

    @Test
    @DisplayName("Ердийн оноонууд зөв үсгэн дүн өгнө: 95→A, 85→B, 75→C, 65→D, 30→F")
    void typicalScores() {
        GradeCalculator calc = new GradeCalculator();       // Arrange

        assertAll(                                          // Act + Assert
            () -> assertEquals("A", calc.letterGrade(95)),
            () -> assertEquals("B", calc.letterGrade(85)),
            () -> assertEquals("C", calc.letterGrade(75)),
            () -> assertEquals("D", calc.letterGrade(65)),
            () -> assertEquals("F", calc.letterGrade(30))
        );
    }

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        String grade = calc.letterGrade(90.0);              // Act
        assertEquals("A", grade);                           // Assert
    }

    @Test
    @DisplayName("89.99 оноо A биш, B дүн байх ёстой")
    void eightyNineNinetyNineIsB() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        String grade = calc.letterGrade(89.99);             // Act
        assertEquals("B", grade);                           // Assert
    }

    @Test
    @DisplayName("60 оноо яг D дүн байх ёстой (хязгаарын тохиолдол)")
    void sixtyIsExactlyD() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        String grade = calc.letterGrade(60.0);              // Act
        assertEquals("D", grade);                           // Assert
    }

    @Test
    @DisplayName("59.99 оноо D биш, F дүн байх ёстой")
    void fiftyNineNinetyNineIsF() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        String grade = calc.letterGrade(59.99);             // Act
        assertEquals("F", grade);                           // Assert
    }

    @Test
    @DisplayName("0 оноо зөвшөөрөгдөнө, F дүн өгнө (доод хязгаар)")
    void zeroIsF() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        String grade = calc.letterGrade(0);                 // Act
        assertEquals("F", grade);                           // Assert
    }

    @Test
    @DisplayName("100 оноо зөвшөөрөгдөнө, A дүн өгнө (дээд хязгаар)")
    void hundredIsA() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        String grade = calc.letterGrade(100);               // Act
        assertEquals("A", grade);                           // Assert
    }

    // ---------- letterGrade: буруу оролт ----------

    @Test
    @DisplayName("-1 оноо IllegalArgumentException шиднэ")
    void letterGradeRejectsNegative() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        assertThrows(IllegalArgumentException.class,        // Act + Assert
            () -> calc.letterGrade(-1));
    }

    @Test
    @DisplayName("101 оноо IllegalArgumentException шиднэ")
    void letterGradeRejectsAboveHundred() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        assertThrows(IllegalArgumentException.class,        // Act + Assert
            () -> calc.letterGrade(101));
    }

    // ---------- totalScore ----------

    @Test
    @DisplayName("Бүх оноо дээд хязгаарт байхад нийлбэр яг 100 болно")
    void totalScoreMaxIsHundred() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        double total = calc.totalScore(10, 40, 10, 10, 30); // Act
        assertEquals(100.0, total, 0.0001);                 // Assert
    }

    @Test
    @DisplayName("Ердийн оноонуудын нийлбэр зөв гарна")
    void totalScoreTypical() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        double total = calc.totalScore(8, 35, 7, 9, 25);    // Act
        assertEquals(84.0, total, 0.0001);                  // Assert
    }

    @Test
    @DisplayName("Ирц сөрөг (-5) байвал IllegalArgumentException шиднэ")
    void totalScoreRejectsNegativeAttendance() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        assertThrows(IllegalArgumentException.class,        // Act + Assert
            () -> calc.totalScore(-5, 30, 8, 8, 20));
    }

    @Test
    @DisplayName("Лаб 41 (дээд хязгаар 40-өөс хэтэрсэн) бол IllegalArgumentException шиднэ")
    void totalScoreRejectsLabAboveLimit() {
        GradeCalculator calc = new GradeCalculator();       // Arrange
        assertThrows(IllegalArgumentException.class,        // Act + Assert
            () -> calc.totalScore(10, 41, 10, 10, 30));
    }

    // ---------- Parameterized тестүүд ----------

    @ParameterizedTest(name = "оноо {0} → дүн {1}")
    @DisplayName("letterGrade хязгаарын утгууд (parameterized)")
    @CsvSource({"95,A", "90,A", "89.99,B", "80,B", "79.99,C", "70,C",
                "69.99,D", "60,D", "59.99,F", "0,F", "100,A"})
    void letterGradeBoundaries(double score, String expected) {
        assertEquals(expected, new GradeCalculator().letterGrade(score));
    }

    @ParameterizedTest(name = "letterGrade({0}) алдаа шиднэ")
    @DisplayName("letterGrade хүчингүй оноонд алдаа шиднэ (parameterized)")
    @ValueSource(doubles = {-1, -0.01, 100.01, 101, 1000})
    void letterGradeInvalidScores(double score) {
        assertThrows(IllegalArgumentException.class,
            () -> new GradeCalculator().letterGrade(score));
    }

    @ParameterizedTest(name = "({0},{1},{2},{3},{4}) → {5}")
    @DisplayName("totalScore нийлбэр (parameterized)")
    @CsvSource({
        "10,40,10,10,30,100",
        "0,0,0,0,0,0",
        "8,35,7,9,25,84",
        "5,20,5,5,15,50",
        "9.5,38.5,8.5,9,27,92.5"
    })
    void totalScoreSums(double att, double lab, double q1, double q2, double exam, double expected) {
        double total = new GradeCalculator().totalScore(att, lab, q1, q2, exam);
        assertEquals(expected, total, 0.0001);
    }

    @ParameterizedTest(name = "({0},{1},{2},{3},{4}) алдаа шиднэ")
    @DisplayName("totalScore хүчингүй оролтод алдаа шиднэ (parameterized)")
    @CsvSource({
        "-5,30,8,8,20",
        "11,30,8,8,20",
        "5,-1,8,8,20",
        "5,41,8,8,20",
        "5,30,-1,8,20",
        "5,30,11,8,20",
        "5,30,8,-1,20",
        "5,30,8,11,20",
        "5,30,8,8,-1",
        "5,30,8,8,31"
    })
    void totalScoreInvalidInputs(double att, double lab, double q1, double q2, double exam) {
        assertThrows(IllegalArgumentException.class,
            () -> new GradeCalculator().totalScore(att, lab, q1, q2, exam));
    }
}
