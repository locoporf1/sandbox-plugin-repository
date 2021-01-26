package org.locoporf1.idea.sandbox.plugin.model;

public class Sandbox {

  private String name;
  private String owner;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public boolean isFree() {
    return owner == null || owner.isEmpty();
  }
}
