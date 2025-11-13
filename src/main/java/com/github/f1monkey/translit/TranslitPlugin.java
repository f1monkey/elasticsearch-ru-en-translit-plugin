package com.github.f1monkey.translit;

import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule.AnalysisProvider;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.common.settings.Settings;

import java.util.Map;

public class TranslitPlugin extends Plugin implements AnalysisPlugin {

    @Override
    public Map<String, AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        return Map.of(
            "ru_en_translit",
            new AnalysisProvider<TokenFilterFactory>() {
                @Override
                public TokenFilterFactory get(
                        IndexSettings indexSettings,
                        Environment environment,
                        String name,
                        Settings settings) {
                    return new TranslitTokenFilterFactory(name, settings);
                }
            }
        );
    }
}