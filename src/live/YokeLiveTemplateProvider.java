package live;

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider;
import org.jetbrains.annotations.Nullable;

public class YokeLiveTemplateProvider implements DefaultLiveTemplatesProvider {

    private static final String[] TEMPLATES = {
            "/liveTemplates/plain_extra",
    };

    @Override
    public String[] getDefaultLiveTemplateFiles() {
        return TEMPLATES;
    }

    @Nullable
    @Override
    public String[] getHiddenLiveTemplateFiles() {
        return null;
    }
}