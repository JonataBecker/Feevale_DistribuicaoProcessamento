package com.github.jonatabecker;

import com.github.jonatabecker.share.Job;
import java.rmi.Naming;

/**
 *
 * @author JonataBecker
 */
public class Processor {

    private Job job;

    private void getJobTracker() {
        String url = "rmi://localhost:" + 8877 + "/tracker";
        try {
            job = (Job) Naming.lookup(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listJobs() {
        Integer theJob;
        try {
            while ((theJob = job.getJob()) != null) {
                System.out.println("Job: " + theJob);
                Thread.sleep(400);
                if(isPrime(theJob)){
                    job.sendJobResult(theJob);
                }else{
                    job.sendJobResult(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    private boolean isPrime(int n){
        int v = n;
        while(--v > 1){
            if(n % v == 0){
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        Processor p = new Processor();
        p.getJobTracker();
        p.listJobs();
        System.out.println("No more Jobs!");
    }

}
