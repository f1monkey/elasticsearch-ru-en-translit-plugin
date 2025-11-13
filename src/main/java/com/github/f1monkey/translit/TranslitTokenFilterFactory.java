package com.github.f1monkey.translit;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.common.settings.Settings;

public class TranslitTokenFilterFactory implements TokenFilterFactory {

    private final String name;
    private final Settings settings;

    public TranslitTokenFilterFactory(String name, Settings settings) {
        this.name = name;
        this.settings = settings;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new TranslitTokenFilter(tokenStream);
    }
}
