package org.locoporf1.idea.sandbox.plugin.services;

import com.intellij.openapi.components.ServiceManager;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.locoporf1.idea.sandbox.plugin.model.Sandbox;

public interface MyApplicationService {

  static MyApplicationService getInstance() {
    return ServiceManager.getService(MyApplicationService.class);
  }

  Optional<List<Sandbox>> getList() throws IOException;
  Optional<Sandbox> request(Sandbox sandbox) throws IOException;
  String getUsername();
  String getSandboxLabel(Sandbox sandbox);

}
