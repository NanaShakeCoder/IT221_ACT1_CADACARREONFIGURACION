public class Manga {
    private String title;
    private String status;
    private String demographics;
    private int score;
    private int members;
    private int votes;
    private int popularity;
    private int favorites;

    public Manga (){
        this.title = "";
        this.status = "";
        this.demographics = "";
        this.score = 0;
        this.members = 0;
        this.votes = 0;
        this.popularity = 0;
        this.favorites = 0;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setDemographics(String demographics) {
        this.demographics = demographics;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setMembers(int members) {
        this.members = members;
    }
    public void setVotes(int votes) {
        this.votes = votes;
    }
    public void setPopularity(int popularity) {
            this.popularity = popularity;
    }
    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }
}
