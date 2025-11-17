package com.github.f1monkey.translit;

import java.util.*;
import java.util.regex.Pattern;

public class TranslitProcessor {

    private static final Map<Character, String> RU_TRANSLIT = Map.ofEntries(
        Map.entry('а', "a"), Map.entry('б', "b"), Map.entry('в', "v"),
        Map.entry('г', "g"), Map.entry('д', "d"), Map.entry('е', "e"),
        Map.entry('ё', "yo"), Map.entry('ж', "zh"), Map.entry('з', "z"),
        Map.entry('и', "i"), Map.entry('й', "y"), Map.entry('к', "k"),
        Map.entry('л', "l"), Map.entry('м', "m"), Map.entry('н', "n"),
        Map.entry('о', "o"), Map.entry('п', "p"), Map.entry('р', "r"),
        Map.entry('с', "s"), Map.entry('т', "t"), Map.entry('у', "u"),
        Map.entry('ф', "f"), Map.entry('х', "h"), Map.entry('ц', "c"),
        Map.entry('ч', "ch"), Map.entry('ш', "sh"), Map.entry('щ', "shch"),
        Map.entry('ъ', ""), Map.entry('ы', "y"), Map.entry('ь', ""),
        Map.entry('э', "e"), Map.entry('ю', "yu"), Map.entry('я', "ya")
    );

    private static final List<Rule> EN_RULES = List.of(
        rule("^cho", "ho"), // choline
        rule("cial$", "shl"), // special

        rule("gi", "dzhi"),

        rule("([bcdfghjklmnpqrstvwxyz]*[aeiouy])ge$", "$1dzh"), // g + silent "e"
        rule("ge", "dzhe"),

        rule("ci", "si"),
        rule("ce", "se"),
        rule("cy", "sai"),
        rule("ca", "ka"),
        rule("co", "ko"),
        rule("cu", "ku"),

        // ai => ei (daily, main, train, paint)
        rule("ai", "ei"),
        rule("ay$", "ei"),

        rule("(sion|tion)", "shn"), // "action" "extension"
        rule("xion", "kshn"), // -xion (complexion)

        rule("tio([a-z])", "sh$1"), // -tio- (ratio, patient)

        rule("e([bcdfghjklmnpqrstvwxyz]e)", "i$1"), // scene, these, complete
        rule("e(r[aeiuoy])", "i$1"), // hero, media, secret

        rule("igh", "ai"),
        rule("ie", "ai"),
        rule("ee", "i"),
        rule("ea", "i"),
        rule("oo", "u"),
        rule("iou", "iu"), // seriuos
        rule("ou", "u"),

        // 'ow' => 'оu' (know, snow, show, blow, throw)
        rule("((kn|sn|bl|sh|th|br|gr|fl|cr|b))ow", "$1ou"),

        // now, cow, how => 'au'
        rule("ow", "au"),

        rule("x", "ks"),
        rule("ck", "k"),
        rule("qu", "kv"),

        rule("^chlo", "hlo"),  // chlorine, chloroform
        rule("kn", "n"), // know
        rule("wr", "r"), // write
        rule("wh", "w"), // who, what
        rule("ph", "f"),
        rule("gh(?![aieo])", "g"),
        rule("gh", "h"),
        rule("cle$", "kl"), // uncle => ankl, circle => sirkl, miracle => mirakl

        rule("([bcdfghjklmnpqrstvwxyz]*[aeiouy][bcdfghjklmnpqrstvwxyz])e$", "$1"), // silent "e"
        rule("e$", "i"), // if not silent, then sounds like "i"

        rule("aye", "ee"), // player
        rule("c", "k"), // sounds like 'k'
        rule("a", "e"), // ambigious: any => eny, max => maks
        rule("y", "i") // sounds like 'i'
    );

    private static final List<Rule> RU_RULES = List.of(
        rule("sh[aeo]n$", "shn"),
        rule("shi[aeo]l$", "shl"),
        rule("eye(.*)", "ee$1"),
        rule("ey(.*)", "ei$1"),
        rule("ay(.*)", "ai$1"),
        rule("c([ei])", "s$1"),
        rule("a", "e"),
        rule("io", "iu") // сириус, сириос
    );

    private static final List<Rule> POST_RULES = List.of(
        rule("([bcdfghjklmnpqrstvwxyz])\\1", "$1"), // double consonants
        rule("z", "s")
    );

    private static final class Rule {
        final Pattern pattern;
        final String replacement;

        Rule(String regex, String replacement) {
            this.pattern = Pattern.compile(regex);
            this.replacement = replacement;
        }
    }

    private static Rule rule(String regex, String replacement) {
        return new Rule(regex, replacement);
    }

    private static String apply(String text, List<Rule> rules) {
        for (Rule r : rules) {
            text = r.pattern.matcher(text).replaceAll(r.replacement);
        }
        return text;
    }

    public static String normalize(String input) {
        if (input == null || input.isEmpty()) return input;

        String text = input.toLowerCase();

        text = apply(text, EN_RULES);

        StringBuilder sb = new StringBuilder();
        boolean hasCyrillic = false;
        for (char c : text.toCharArray()) {
            String repl = RU_TRANSLIT.get(c);
            if (repl != null) {
                sb.append(repl);
                hasCyrillic = true;
            } else {
                sb.append(c);
            }
        }

        String transliterated = sb.toString();

        if (hasCyrillic) {
            transliterated = apply(transliterated, RU_RULES);
        }

        return apply(transliterated, POST_RULES);
    }
}