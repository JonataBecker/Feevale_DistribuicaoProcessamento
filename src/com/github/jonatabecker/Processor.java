package com.github.jonatabecker;

import java.rmi.Naming;
import com.github.jonatabecker.share.Fifo;
import com.github.jonatabecker.share.Job;

/**
 *
 * @author JonataBecker
 */
public class Processor {

    private Fifo job;

    private void getJobTracker() {
        String url = "rmi://localhost:" + 8877 + "/tracker";
        try {
            job = (Fifo) Naming.lookup(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listJobs() {
        Job theJob;
        try {
            while ((theJob = job.getJob()) != null) {
                System.out.println("Job: " + theJob);
                Thread.sleep(400);
                job.sendJobResult(theJob.exec());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Processor processor = new Processor();
        processor.getJobTracker();
        processor.listJobs();
        System.out.println("No more Jobs!");
    }

}
