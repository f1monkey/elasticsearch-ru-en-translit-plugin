package com.github.f1monkey.translit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TranslitProcessorTest {

    @ParameterizedTest
    @CsvSource({
        "cycle, seikl",
        "omega, omege",
        "омега, omege",

        "extension, ekstenshn",
        "экстеншн, ekstenshn",
        "экстеншен, ekstenshn",
        "экстеншон, ekstenshn",
        "экстеншан, ekstenshn",

        "mission, mishn",
        "мишн, mishn",

        "action, ekshn",
        "экшен, ekshn",

        "active, ektiv",
        "эктив, ektiv",

        "special, speshl",
        "спешл, speshl",
        "спешиал, speshl",
        "спешиол, speshl",

        "serious, serius",
        "сириос, sirios",
        "сириус, sirius",

        "complex, kompleks",
        "комплекс, kompleks",

        "calcium, kelsium",
        "кальциум, kelsium",

        "player, pleer",
        "плеер, pleer",
        "плейер, pleer",

        "chlorophyll, hlorofil",
        "хлорофилл, hlorofil",

        "inositol, inositol",
        "инозитол, inositol",

        "choline, holin",
        "холин, holin",

        "maxler, meksler",
        "макслер, meksler",

        "daily, deili",
        "дейли, deili",

        "day, dei",
        "дей, dei",

        "know, nou",
        "ноу, nou",

        "now, neu",
        "нау, neu",

        "glycine, glisin",
        "глицин, glisin",

        "gainer, geiner",
        "гейнер, geiner"
    })
    void testTransliteration(String input, String expected) {
        String result = TranslitProcessor.normalize(input);
        assertEquals(expected, result, "Failed for input: " + input);
    }

    @Test
    void testNullAndEmpty() {
        assertEquals(null, TranslitProcessor.normalize(null));
        assertEquals("", TranslitProcessor.normalize(""));
    }
}