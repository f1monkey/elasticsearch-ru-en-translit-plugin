package com.github.f1monkey.translit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslitProcessorTest {

    @ParameterizedTest
    @CsvSource({
            "сайкл, cycle",
            "омега, omega",

            "экстеншн, extension",
            "экстеншен, extension",
            "экстеншон, extension",
            "экстеншан, extension",
            "мишн, mission",
            "экшен, action",
            "эктив, active",
            "спешл, special",
            "спешиал, special",
            "спешиол, special",
            "сириос, serious",
            "сириус, sirius",
            "комплекс, complex",
            "кальциум, calcium",
            "плеер, player",
            "плейер, player",
            "хлорофилл, chlorophyll",
            "инозитол, inositol",
            "холин, choline",
            "макслер, maxler",
            "дейли, daily",
            "дей, day",
            "ноу, know",
            "нау, now",
            "глицин, glycine",
            "гейнер, gainer",
            "фудс, foods",
            "фудз, foods",
            "пиколинат, picolinate",
            "ноус, knows",
            "амбиджиос, ambigious",
            "хи, he",
            "колледж, college",
            "бай, bye",
            "бай, buy",
            "веган, vegan",
            "ниацинамид, niacinamide",
            "гидролизат, hydrolysate",

            "вей, way",
            "вей, whey",

            "лав, love",
            "сам, some",
            "каминг, coming",
            "кам, come",
            "глов, glove",
            "шов, shov",
            "дан, done",


            "дайз, dies",
            "дай, die",

            "кукис, cookies",
            "пис, piece",
            "френд, friend"
    })
    void testTransliteration(String input1, String input2) {
        String result1 = TranslitProcessor.normalize(input1);
        String result2 = TranslitProcessor.normalize(input2);
        assertEquals(result1, result2,
                () -> String.format("Failed for input: \"%s\", \"%s\"\n\"Actual:   \"%s\"\nExpected: \"%s\"\n",
                        input1, input2, result1, result2));
    }

    @Test
    void testNullAndEmpty() {
        assertEquals(null, TranslitProcessor.normalize(null));
        assertEquals("", TranslitProcessor.normalize(""));
    }
}