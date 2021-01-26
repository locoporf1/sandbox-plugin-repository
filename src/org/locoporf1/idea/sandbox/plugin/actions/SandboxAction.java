package org.locoporf1.idea.sandbox.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import java.io.IOException;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.locoporf1.idea.sandbox.plugin.model.Sandbox;
import org.locoporf1.idea.sandbox.plugin.services.MyApplicationService;

public class SandboxAction extends AnAction {

  public SandboxAction(String text) {
    super(text);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
    Sandbox sandbox2Request = new Sandbox();
    sandbox2Request.setName(this.getTemplateText());
    try {
      Optional<Sandbox> optionalSandbox = MyApplicationService.getInstance().request(sandbox2Request);
      if (optionalSandbox.isPresent()) {
        Sandbox requestedSandbox = optionalSandbox.get();
        Messages.showMessageDialog(
            "".equals(requestedSandbox.getOwner()) ?
                "Sandbox is now free!" :
                "Sandbox assigned to you!",
            "Operation successful",
            Messages.getInformationIcon()
        );
      }
    } catch (IOException e) {
      Messages.showMessageDialog(e.getMessage(), "Error requesting sandbox", Messages.getErrorIcon());
    }

  }
}
