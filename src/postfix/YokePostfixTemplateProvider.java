package postfix;

import com.intellij.codeInsight.template.postfix.templates.JavaPostfixTemplateProvider;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplate;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class YokePostfixTemplateProvider extends JavaPostfixTemplateProvider {

    private final Set<PostfixTemplate> templates;

    public YokePostfixTemplateProvider() {
        templates = ContainerUtil.newHashSet(
                new AllSetTemplate()
        );
    }

    @NotNull
    @Override
    public Set<PostfixTemplate> getTemplates() {
        return templates;
    }
}