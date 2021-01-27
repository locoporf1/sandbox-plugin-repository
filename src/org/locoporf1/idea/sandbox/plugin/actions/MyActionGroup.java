package org.locoporf1.idea.sandbox.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.locoporf1.idea.sandbox.plugin.model.Sandbox;
import org.locoporf1.idea.sandbox.plugin.services.MyApplicationService;

public class MyActionGroup extends DefaultActionGroup {

  @Override
  public void update(@NotNull AnActionEvent e) {
    e.getPresentation().setEnabled(true);
  }

  @NotNull
  @Override
  public AnAction[] getChildren(@Nullable AnActionEvent e) {
    AnAction[] result = new AnAction[0];
    if (e != null) {
      try {
        Optional<List<Sandbox>> sandboxes = MyApplicationService.getInstance().getList();
        if (sandboxes.isPresent()) {
          result = new AnAction[sandboxes.get().size()];
          for (int i = 1; i <= sandboxes.get().size(); i++) {
            result[i - 1] = createSandboxAction(e, i, sandboxes.get().get(i - 1));
          }
        } else {
          e.getPresentation().setEnabled(false);
        }
      } catch (IOException ioException) {
        e.getPresentation().setEnabled(false);
      }
    }
    return result;
  }

  private AnAction createSandboxAction(AnActionEvent e, Integer i, Sandbox sandbox) {
    String id = "SandboxAction" + i;
    String text = MyApplicationService.getInstance().getSandboxLabel(sandbox);
    AnAction result = e.getActionManager().getAction(id);
    if (result == null) {
      result = new SandboxAction(text);
      e.getActionManager().registerAction(id, result);
    } else if (result != null && !text.equals(result.getTemplateText())) {
      result = new SandboxAction(text);
      e.getActionManager().replaceAction(id, result);
    }
    return result;
  }
}
