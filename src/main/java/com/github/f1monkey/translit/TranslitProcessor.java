package com.github.f1monkey.translit;

import java.util.*;
import java.util.regex.Pattern;

public class TranslitProcessor {

    private static final Map<Character, String> RU_TRANSLIT = Map.ofEntries(
        Map.entry('а', "a"), Map.entry('б', "b"), Map.entry('в', "v"),
        Map.entry('г', "G"), Map.entry('д', "d"), Map.entry('е', "e"),
        Map.entry('ё', "yo"), Map.entry('ж', "zh"), Map.entry('з', "z"),
        Map.entry('и', "i"), Map.entry('й', "y"), Map.entry('к', "k"),
        Map.entry('л', "l"), Map.entry('м', "m"), Map.entry('н', "n"),
        Map.entry('о', "o"), Map.entry('п', "p"), Map.entry('р', "r"),
        Map.entry('с', "s"), Map.entry('т', "t"), Map.entry('у', "u"),
        Map.entry('ф', "f"), Map.entry('х', "h"), Map.entry('ц', "c"),
        Map.entry('ч', "ch"), Map.entry('ш', "S"), Map.entry('щ', "S"),
        Map.entry('ъ', ""), Map.entry('ы', "y"), Map.entry('ь', ""),
        Map.entry('э', "e"), Map.entry('ю', "u"), Map.entry('я', "ya")
    );

    private static final List<Rule> EN_RULES = List.of(
        rule("^love", "lAv"),
        rule("^glove", "glov"),
        rule("^shove", "shov"),
        rule("^have$", "hev"),
        rule("some", "sAm"),
        rule("done$", "dAn"),
        rule("come", "kAm"),
        rule("coming", "kAming"),
        rule("hydro", "GIdro"),
        rule("gly(.+)", "glI$1"), // glycine
        rule("(.+)ysat(.+)", "$1IsAt$2"), // hydrolysate
        rule("(.+)inat(.+)", "$1InAt$2"), // picolinate
        rule("(.+)amid(.+)", "$1AmId$2"), // niacinamide
        rule("(.+)acin(.+)", "$1AcIn$2"), // niacinamide
        rule("^chlo", "hlo"),  // chlorine, chloroform
        rule("^chol", "hol"), // choline
        rule("pico", "pIco"), // picolinate
        rule("cle$", "kl"), // uncle => ankl, circle => sirkl, miracle => mirakl

        rule("(c|w|sh)ould", "$1Ud"),

        // all => oll
        rule("ally$", "Ally"), // actually, totally
        rule("al(ways|most|right)", "ol$1"), // always, almost
        rule("shall", "shell"),
        rule("^all", "All"), // alligator, allergy
        rule("all", "oll"),

        // u => oo words
        rule("(b|p|f|c|n|sk)ull", "$1Ull"),
        rule("(p|b)ush", "$1Ush"),
        rule("cushion", "cUshion"),
        rule("bushel", "bUshel"),
        rule("butcher", "bUtcher"),
        rule("mustang", "mUstang"),
        rule("pustule", "pUstule"),
        rule("put", "pUt"),
        rule("pudding", "pUdding"),

        // one
        rule("^one", "WAn"),
        rule("one$", "WAn"),

        // sh
        rule("(sion|tion)", "Sn"), // "action" "extension"
        rule("xion", "kSn"), // -xion (complexion)
        rule("sh", "S"),
        rule("tio([a-z])", "S$1"), // -tio- (ratio, patient)
        rule("sS", "S"), // mission
        rule("cial$", "Sl"), // special

        // g,j
        rule("gi", "Ji"),
        rule("([bcdfghjklmnpqrstvwxyz]*[aeiouy])ge$", "$1J"), // g + silent "e" (college)
        rule("ge", "Je"),
        rule("j", "J"),
        rule("g", "G"),

        // c => k, c => s
        rule("c([ieyIEY])", "s$1"),
        rule("c([aouAoUbcdfghjklmnpqrstvwxyzW])", "k$1"),

        rule("ew(s?)", "u$1"),

        // ai => ei (daily, main, train, paint)
        rule("ai", "ei"),
        rule("ay$", "ei"),

        // ie
        rule("^friend$", "frend"),
        rule("ie", "I"), // cookie, field, piece

        rule("igh", "I"),

        rule("e([bcdfghjklmnpqrstvwxyz][aeiuoy])", "I$1"), // scene, these, complete

        // a
        rule("a([bcdfghjklmnpqrstvwxz]{2,})", "A$1"), // grapple
        rule("a([bcdfghjklmnpqrstvwxz][aeiouy])", "ei$1"), // make

        // y
        rule("([SW]|^[bcdfghjklmnpqrstvwxz]+)y$", "$1I"), // why, shy, by
        rule("pply$", "plI"), // apply, supply
        rule("y([bcdfghjklmnpqrstvwxz][aeiouy])", "I$1"), // cycle, typo

        // u
        rule("u([bcdfghjklmnpqrstvwxz][^aeiouy])", "A$1"), // supply, currency

        rule("(ye|uy)$", "I"), // bye, buy
        rule("ee", "I"),
        rule("oo", "u"),
        rule("iou", "io"), // seriuos
        rule("aye", "ee"), // player

        // ea
        rule("^break", "breik"),
        rule("ea", "I"),

        // e
        rule("([bcdfghjklmnpqrstvwxyz]*[aeiouyAIU][bcdfghjklmnpqrstvwxyz]|ppl)e$", "$1"), // silent "e"
        rule("e$", "I"), // if not silent, then sounds like "i"

        rule("((kn|sn|bl|sh|th|br|gr|fl|cr|b))ow", "$1ou"), // 'ow' => 'оu' (know, snow, show, blow, throw)
        rule("ow", "au"), // now, cow, how => 'au'

        rule("x", "ks"),
        rule("ck", "k"),

        rule("kn", "n"), // know
        rule("wr", "r"), // write
        rule("wh", "W"), // who, what
        rule("ph", "f"),
        rule("gh(?![aieo])", "g"),
        rule("gh", "h"),

        rule("c", "k"), // sounds like 'k'
        rule("[aA]", "e"), // ambigious: any => eny, max => maks
        rule("[iy]", "I"), // sounds like 'i'

        rule("w", "W"),
        rule("v", "W"),
        rule("qu", "kW"), // quake, question

        rule("ou?", "o"),


        rule("A", "a"),
        rule("I", "i"),
        rule("U", "u")
    );

    private static final List<Rule> RU_RULES = List.of(
        rule("(u)([aeiou])", "W$2"),
        rule("v", "W"),

        rule("S[aeo]n$", "Sn"),
        rule("Si[aeo]l$", "Sl"),
        rule("eye(.*)", "ee$1"),
        rule("ey(.*)", "ei$1"),
        rule("ay(.*)", "ai$1"),
        rule("c([ei])", "s$1"),
        rule("dzh", "J"),
        rule("ai", "i"),
        rule("a", "e"),
        rule("y", "i"),
        rule("ou", "o")
    );

    private static final List<Rule> POST_RULES = List.of(
        // double consonants
        rule("([bcdfghjklmnpqrstvwxyz])\\1", "$1"),

        rule("io", "iu"), // сириус, сириос, bio
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