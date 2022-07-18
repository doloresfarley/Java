package com.affirm.app;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogLineData {

    private String Category ;
    private String Title ;
    private Integer movieLength;
    private Integer movieWatched;
    private Double ratio;
    private Integer views;

    LogLineData(final String str) {

        Category ="";
        Title = new String(str);
        movieLength =0 ;
        movieWatched = 0;
        ratio = 0.0;
        views = 0;


        String string = "Video:";
        Pattern pattern = Pattern.compile(string);
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            Category= str.substring(0, matcher.start());
            Title = str.replaceAll(Category+string, "");

           // System.out.println("Found at: " + Category);
           // System.out.println("Title: " + Category +"\n");

        }
        string = "(\\d+)ms";
        pattern = Pattern.compile(string);
        matcher = pattern.matcher(str);
        boolean firstTime = true;

        while (matcher.find()) {
            String m = str.substring(matcher.start(), matcher.end());
            Title = Title.replaceAll(m, "");
            int length = Integer.valueOf(m.replaceAll("ms", ""));
            if (firstTime) {
                movieLength = length;
                firstTime = false;

            } else {
                movieWatched = length;
            }
           // System.out.println("Found at: " + m);
           // System.out.println("Title: " + Title +"\n");

        }
        string = "(\\d+ )views";
         pattern = Pattern.compile(string);
         matcher = pattern.matcher(str);

        if (matcher.find()) {
            String m = str.substring(matcher.start(), matcher.end());
            Title = Title.replaceAll(m, "").trim();
          //  System.out.println("Views at: " + m);
          //  System.out.println("Title: " + Title +"\n");

            int length = Integer.valueOf(m.replaceAll(" views", ""));
            views = length;
        }

//        String s;
//        StringTokenizer tokenizer = new StringTokenizer(str, " ");
//        while (tokenizer.hasMoreElements()) {
//            s = tokenizer.nextToken();
//            if (s.contains(("ms"))) {
//                int length = Integer.valueOf(s.replaceAll("ms", ""));
//                if (firstTime) {
//                    movieLength = length;
//                    firstTime = false;
//                } else {
//                    movieWatched = length;
//                }
//
//
//            } else {
//                Title += s + " ";
//            }
//        }

        if (  movieLength != 0 )
             ratio = Double.valueOf(movieWatched)/ Double.valueOf(movieLength);

    }

    String getCategory() { return Category;}
    String getTitle() { return Title;}
    Integer getMovieLength() {return movieLength;}
    Integer getmovieWatched() {return movieWatched;}
    Double getRatio() { return ratio;}
    Integer getViews() {return views;}


    static class MovieSortingCategory implements Comparator<LogLineData> {

        @Override
        public int compare(LogLineData movie1, LogLineData movie2) {

            // for comparison
            return movie1.getCategory().compareTo(movie2.getCategory());

        }
    }

    static class MovieSortingLength implements Comparator<LogLineData> {

        @Override
        public int compare(LogLineData movie1, LogLineData movie2) {

            // for comparison
            return movie1.getMovieLength().compareTo(movie2.getMovieLength());

        }
    }

    static class MovieSortingName implements Comparator<LogLineData> {

        @Override
        public int compare(LogLineData movie1, LogLineData movie2) {

            // for comparison
            return movie1.getTitle().compareToIgnoreCase(movie2.getTitle());

        }
    }

    static class MovieSortingRatio implements Comparator<LogLineData> {

        @Override
        public int compare(LogLineData movie1, LogLineData movie2) {

            // for comparison
            return movie1.getRatio().compareTo(movie2.getRatio());

        }
    }

    static class MovieSortingWatched implements Comparator<LogLineData> {

        @Override
        public int compare(LogLineData movie1, LogLineData movie2) {

            // for comparison
            return movie1.getmovieWatched().compareTo(movie2.getmovieWatched());

        }
    }

    static class MovieSortingViews implements Comparator<LogLineData> {

        @Override
        public int compare(LogLineData movie1, LogLineData movie2) {

            // for comparison
            return movie1.getViews().compareTo(movie2.getViews());

        }
    }

    @Override
    public String toString(){
        String ms  = String.valueOf(movieLength);
        return   "Category: " +Category  +  "Title: " +Title +", Movie Length: " + ms + ", Movie Watched: " + String.valueOf(movieWatched) + ", "
                + "Ratio: " +String.valueOf(ratio) +
                ", # of Views " + String.valueOf(views);

    }
}
