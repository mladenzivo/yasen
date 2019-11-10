package ru.stqa.yasen;

class ChildElementContext extends AbstractElementContext {

  private final Element parent;

  ChildElementContext(Element parent) {
    super(parent::getWebElement, parent::activate, parent::invalidate);
    this.parent = parent;
  }
}
