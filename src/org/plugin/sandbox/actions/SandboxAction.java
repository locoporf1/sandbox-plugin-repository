package org.plugin.sandbox.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class SandboxAction extends AnAction {

  public SandboxAction(String text) {
    super(text);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    String user = System.getProperty("user.name");
    Messages.showMessageDialog(anActionEvent.getProject(), "Hello " + user + "!", "MyIdeaDemo", Messages.getInformationIcon());
  }
}
