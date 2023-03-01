package com.hactually.ui;

public interface View {
    public void printMenu();
    public void printStartBanner(String message);
    public void printSuccessBanner(String message);
    public void printFailureBanner(String message);
}
