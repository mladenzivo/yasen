package ru.stqa.lenium;

class ChildElementContext extends AbstractElementContext {

  ChildElementContext(ElementContext parent) {
    super(parent::getElementBy, parent::activate);
  }
}
