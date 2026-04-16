package com.example.sportsnewsfeed.data;

import com.example.sportsnewsfeed.R;
import com.example.sportsnewsfeed.model.NewsItem;
import java.util.ArrayList;
import java.util.List;

public class DummyData {
    public static List<NewsItem> getNewsItems() {
        List<NewsItem> items = new ArrayList<>();
        items.add(new NewsItem(1, "Champions League Final", "The biggest match of the year is here. Real Madrid faces Dortmund.", "Football", R.drawable.ic_placeholder, true));
        items.add(new NewsItem(2, "NBA Playoffs Update", "The Celtics are looking dominant in the Eastern Conference Finals.", "Basketball", R.drawable.ic_placeholder, true));
        items.add(new NewsItem(3, "T20 World Cup Kicks Off", "Cricket fans around the world are excited for the start of the tournament.", "Cricket", R.drawable.ic_placeholder, true));
        items.add(new NewsItem(4, "Transfer Rumors: Mbappe to Madrid?", "The French superstar is heavily linked with a move to the Spanish capital.", "Football", R.drawable.ic_placeholder, false));
        items.add(new NewsItem(5, "Lakers Searching for New Coach", "After a disappointing season, the Lakers are looking for fresh leadership.", "Basketball", R.drawable.ic_placeholder, false));
        items.add(new NewsItem(6, "IPL Final Highlights", "Relive the best moments from the thrilling IPL final.", "Cricket", R.drawable.ic_placeholder, false));
        items.add(new NewsItem(7, "French Open: Nadal's Last Stand?", "Tennis legend Rafael Nadal prepares for what could be his final Roland Garros.", "Tennis", R.drawable.ic_placeholder, false));
        items.add(new NewsItem(8, "F1: Verstappen Wins in Monaco", "Another masterclass from the reigning world champion on the streets of Monte Carlo.", "F1", R.drawable.ic_placeholder, false));
        return items;
    }
}
