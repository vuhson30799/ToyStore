package JSON;

public class Comment {

    private Long ratingId;

    private Long parentId;

    private Long rootId;

    private Long ratingStar;

    private String comment;

    private String timeAgo;

    private String nameUser;

    private String parentName;

    private Long toyId;

    private String username;

    private Long replyQty;

    public Comment() {
    }

    public Comment(Long ratingId, Long parentId) {
        this.ratingId = ratingId;
        this.parentId = parentId;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

    public Long getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(Long ratingStar) {
        this.ratingStar = ratingStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getToyId() {
        return toyId;
    }

    public void setToyId(Long toyId) {
        this.toyId = toyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getReplyQty() {
        return replyQty;
    }

    public void setReplyQty(Long replyQty) {
        this.replyQty = replyQty;
    }
}