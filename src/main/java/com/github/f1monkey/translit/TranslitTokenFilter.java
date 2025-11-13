package com.github.f1monkey.translit;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class TranslitTokenFilter extends TokenFilter {

    private final CharTermAttribute termAttr = addAttribute(CharTermAttribute.class);

    protected TranslitTokenFilter(TokenStream input) {
        super(input);
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }

        String term = termAttr.toString();
        String normalized = TranslitProcessor.normalize(term);

        termAttr.setEmpty().append(normalized);
        return true;
    }
}