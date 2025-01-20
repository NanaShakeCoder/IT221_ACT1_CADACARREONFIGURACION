public class Manga {
    private String title;
    private String status;
    private String demographics;
    private Double score;
    private int members;
    private int votes;
    private int popularity;
    private int favorites;

    //CONSTRUCTORS
    public Manga (){
        this.title = "";
        this.score = 0.0;
        this.votes = 0;
        this.popularity = 0;
        this.members = 0;
        this.favorites = 0;
        this.status = "";
        this.demographics = "";
    }
    public Manga(String name, Double scr, int vot, int pop, int mem, int fav, String sts, String dem) {
        this.title = name;
        this.score = scr;
        this.votes = vot;
        this.popularity = pop;
        this.members = mem;
        this.favorites = fav;
        this.status = sts;
        this.demographics = dem;
    }
    //SETTERS
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setDemographics(String demographics) {
        this.demographics = demographics;
    }
    public void setScore(Double score) {
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
    //GETTERS
    public String getTitle() {
        return title;
    }
    public String getStatus() {
        return status;
    }
    public String getDemographics() {
        return demographics;
    }
    public Double getScore() {
        return score;
    }
    public int getMembers() {
        return members;
    }
    public int getVotes() {
        return votes;
    }
    public int getPopularity() {
        return popularity;
    }
    public int getFavorites() {
        return favorites;
    }
    //METHODS

    @Override
    public String toString() {
        if (this.title.length() < 20){
            return String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s", this.title, this.score, this.votes, this.popularity, this.members, this.favorites, this.status, this.demographics);
        } else {
            return String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s", this.title.substring(0,19), this.score, this.votes, this.popularity, this.members, this.favorites, this.status, this.demographics);
        }
    }
}
