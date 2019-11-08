package ru.stqa.lenium;

class ChildElementContext extends AbstractElementContext {

  ChildElementContext(Element element) {
    super(element.getWebElement(), element::activate, element::invalidate);
  }
}
