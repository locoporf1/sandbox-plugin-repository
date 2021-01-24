package org.plugin.sandbox.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyActionGroup extends DefaultActionGroup {

  @Override
  public void update(@NotNull AnActionEvent e) {
    boolean writable = Files.isWritable(Paths.get("C:\\Users\\m.rodriguez.tena\\Desktop\\sandboxes.txt"));
    e.getPresentation().setEnabled(writable);
  }

  @NotNull
  @Override
  public AnAction[] getChildren(@Nullable AnActionEvent e) {
    AnAction[] result = null;
    try {
      List<String> lines = Files
          .lines(Paths.get("C:\\Users\\m.rodriguez.tena\\Desktop\\sandboxes.txt"))
          .collect(Collectors.toList());
      result = new AnAction[lines.size()];
      for (int i = 0; i < lines.size(); i ++) {
        result[i] = createSandboxAction(e, i, lines.get(i));
      }
    } catch (IOException ioException) {
      e.getPresentation().setEnabled(false);
    }
    return result;
  }

  private AnAction createSandboxAction(AnActionEvent e, Integer i, String owner) {
    String text = "Sandbox " + (i + 1);
    String id = "Sandbox" + i;
    AnAction result = e.getActionManager().getAction(id);
    if (result == null) {
      result = new SandboxAction(owner.isEmpty() ? text : text + " - " + owner);
      e.getActionManager().registerAction(id, result);
    }
    return result;
  }
}
