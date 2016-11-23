package postfix;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.ui.CollectionListModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllSetTemplate extends PostfixTemplateWithExpressionSelector {

    public AllSetTemplate() {
        super(
                "allset",
                "set...",
                JavaPostfixTemplatesUtils.selectorTopmost()
        );
    }


    @Override
    protected void expandForChooseExpression(@NotNull PsiElement expression, @NotNull Editor editor) {
        removeExpressionFromEditor(expression, editor);

        final Project project = expression.getProject();
        final TemplateManager manager = TemplateManager.getInstance(project);
        try {
            String className = ((PsiExpression) expression).getType().getCanonicalText();
            PsiClass psiClass = JavaPsiFacade.getInstance(expression.getProject()).findClass(className, expression.getResolveScope());
            List<PsiField> paramentFields = new CollectionListModel<>(psiClass.getAllFields()).getItems();
            final String stringTemplate = getSetsText(expression.getText(), paramentFields) + "$END$";
            final Template template = manager.createTemplate("", "", stringTemplate);
            template.setToReformat(true);
            manager.startTemplate(editor, template);
        } catch (Exception e) {
        }
    }

    private String getSetsText(String objName, List<PsiField> paramentFields) {
        StringBuilder builder = new StringBuilder("");
        for (PsiField field : paramentFields) {
            PsiModifierList modifierList = field.getModifierList();
            if (modifierList == null || modifierList.hasModifierProperty(PsiModifier.STATIC) || modifierList.hasModifierProperty(PsiModifier.FINAL) || modifierList.hasModifierProperty(PsiModifier.SYNCHRONIZED)) {
                continue;
            }
            builder.append(objName + ".set" + getFirstUpperCase(field.getName()) + "();\n");
        }
        return builder.toString();
    }

    private String getFirstUpperCase(String oldStr) {
        return oldStr.substring(0, 1).toUpperCase() + oldStr.substring(1);
    }

    private void removeExpressionFromEditor(@NotNull PsiElement expression, @NotNull Editor editor) {
        Document document = editor.getDocument();
        document.deleteString(expression.getTextRange().getStartOffset(), expression.getTextRange().getEndOffset());
    }
}