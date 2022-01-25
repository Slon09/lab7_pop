package data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Calendar.*;

public class NewsData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String news;
    private Date date;
    private int id;
    private static final AtomicInteger count = new AtomicInteger(0);

    public NewsData(String news){
        id = count.incrementAndGet();
        this.news = news;
        this.date = new Date();
    }

    public String getNews() {
        return news;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }


}