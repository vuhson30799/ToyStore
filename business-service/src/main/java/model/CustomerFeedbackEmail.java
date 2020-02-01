package model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CustomerFeedbackEmail {

    public static final String FROM_EMAIL = "toeic.t35@gmail.com";

    public static final String TARGET_EMAIL = "vuhoangsonltt@gmail.com";

    public final String SUBJECT_EMAIL = "Feedback from customer";

    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotEmpty
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}