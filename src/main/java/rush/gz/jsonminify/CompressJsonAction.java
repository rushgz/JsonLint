package rush.gz.jsonminify;

import com.google.gson.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class CompressJsonAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Runnable runnable = () -> {
            Editor editor = event.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);
            if (editor != null) {
                Document document = editor.getDocument();
                String jsonText = document.getText();
                try {
                    Gson gson = new GsonBuilder()
                            .disableHtmlEscaping().create();

                    JsonElement jsonElement = JsonParser.parseString(jsonText);
                    String compressedJson = gson.toJson(jsonElement);
                    document.setText(compressedJson);
                    Messages.showMessageDialog("JSON compressed successfully!", "Success", Messages.getInformationIcon());
                } catch (Exception e) {
                    Messages.showMessageDialog("Failed to compress JSON: " + e.getMessage(), "Error", Messages.getErrorIcon());
                }
            }
        };
        WriteCommandAction.runWriteCommandAction(ProjectManager.getInstance().getDefaultProject(), runnable);
    }
}
