package org.plugin.sandbox;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class HelloAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    Messages.showMessageDialog(anActionEvent.getProject(), "Hello world!", "MyIdeaDemo", Messages.getInformationIcon());
  }
}
