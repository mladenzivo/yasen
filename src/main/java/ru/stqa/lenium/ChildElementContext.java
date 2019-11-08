package ru.stqa.lenium;

class ChildElementContext extends AbstractElementContext {

  ChildElementContext(WebElementSupplier supplier) {
    super(by -> supplier.getWebElement().findElement(by), () -> supplier.getContext().activate());
  }
}
