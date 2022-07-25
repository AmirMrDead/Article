package list;

import entity.Article;

import java.util.Arrays;

public class ArticleList {

    private Article[] articles = new Article[1000];
    private int index = 0;

    public Article[] getArticle() {
        return articles;
    }

    public void setArticle(Article[] article) {
        this.articles = article;
    }

    public void add(Article article) {
        if (index > articles.length - 1)
            articles = Arrays.copyOf(articles, articles.length * 2);

        articles[index] = article;
        index++;
    }

    public void remove(int index) {
        articles[index - 1] = null;
        for (int i = index - 1; i < this.index; i++) {
            articles[i] = articles[i + 1];
        }
        this.index--;
    }
}
