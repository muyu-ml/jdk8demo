package com.example.jdk8demo;

import lombok.extern.slf4j.Slf4j;

import java.io.PipedReader;
import java.io.PipedWriter;

@Slf4j
public class Piped {

    public static void main(String[] arg) throws Exception{
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);
        Thread printThread = new Thread(new Print(in),"printThread");
        printThread.start();
        int receive = 0;
        try{
            while ((receive=System.in.read())!=-1){
                out.write(receive);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }

    }

    static class Print implements Runnable{

        private PipedReader in;

        public Print(PipedReader in){
            this.in = in;
        }

        @Override
        public void run(){
            int receive = 0;
            try{
                while ((receive=in.read())!=-1){
                    System.out.print((char)receive);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
