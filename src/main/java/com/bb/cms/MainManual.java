package com.bb.cms;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MainManual {

    public static void main(String[] args) throws IOException {
        Loader.load("example\\gre_500_raw.txt", false);
    }


}
