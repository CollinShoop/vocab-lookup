package com.bb.cms;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class MainInteractive {

    public static void main(String[] args) throws IOException {
        String path;
        if (args.length > 0) {
            path = args[0];
        } else {
            System.out.println("Path > ");
            path = new Scanner(System.in).nextLine();
        }

        Loader.load(path, false);

    }


}
