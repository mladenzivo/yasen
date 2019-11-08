package ru.stqa.lenium;

class ChildElementContext extends AbstractElementContext {

  ChildElementContext(Element element) {
    super(by -> element.getWebElement().findElement(by), element::activate, element::invalidate);
  }
}
