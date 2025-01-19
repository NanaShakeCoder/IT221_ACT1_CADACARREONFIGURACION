public class Manga {
    private String title;
    private String status;
    private String demographics;
    private int score;
    private int members;
    private int votes;
    private int popularity;
    private int favorites;

    //CONSTRUCTORS
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
    public Manga(String name, String sts, String dem, int scr, int mem, int vot, int pop, int fav) {
        this.title = name;
        this.status = sts;
        this.demographics = dem;
        this.score = scr;
        this.members = mem;
        this.votes = vot;
        this.popularity = pop;
        this.favorites = fav;
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
    public int getScore() {
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

}
