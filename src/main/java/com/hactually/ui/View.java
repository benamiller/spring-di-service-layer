package com.hactually.ui;

import java.util.List;

public interface View {
    public void printMenu(List<String> menuItems);
    public void printStartBanner(String message);
    public void printSuccessBanner(String message);
    public void printFailureBanner(String message);
}
