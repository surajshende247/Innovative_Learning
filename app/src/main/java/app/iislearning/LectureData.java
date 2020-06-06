package app.iislearning;

public class LectureData {
    String class_id;
    String class_subject;
    String class_grade;
    String class_title;
    String video_id;
    String class_description;
    String class_instructions;
    String subject_teacher;
    String uploaded_date;
    String due_date;

    public LectureData(String class_id, String class_subject, String class_grade, String class_title, String video_id, String class_description, String class_instructions, String subject_teacher, String uploaded_date, String due_date) {
        this.class_id = class_id;
        this.class_subject = class_subject;
        this.class_grade = class_grade;
        this.class_title = class_title;
        this.video_id = video_id;
        this.class_description = class_description;
        this.class_instructions = class_instructions;
        this.subject_teacher = subject_teacher;
        this.uploaded_date = uploaded_date;
        this.due_date = due_date;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_subject() {
        return class_subject;
    }

    public void setClass_subject(String class_subject) {
        this.class_subject = class_subject;
    }

    public String getClass_grade() {
        return class_grade;
    }

    public void setClass_grade(String class_grade) {
        this.class_grade = class_grade;
    }

    public String getClass_title() {
        return class_title;
    }

    public void setClass_title(String class_title) {
        this.class_title = class_title;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getClass_description() {
        return class_description;
    }

    public void setClass_description(String class_description) {
        this.class_description = class_description;
    }

    public String getClass_instructions() {
        return class_instructions;
    }

    public void setClass_instructions(String class_instructions) {
        this.class_instructions = class_instructions;
    }

    public String getSubject_teacher() {
        return subject_teacher;
    }

    public void setSubject_teacher(String subject_teacher) {
        this.subject_teacher = subject_teacher;
    }

    public String getUploaded_date() {
        return uploaded_date;
    }

    public void setUploaded_date(String uploaded_date) {
        this.uploaded_date = uploaded_date;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
}
