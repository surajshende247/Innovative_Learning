package app.iislearning.assignment;

public class MyAssign {
    String id;
    String student_id;
    String assignment_id;
    String stdimg_url;
    String feedback;
    String marks_obtained;
    String upload_status;
    String assignment_date;
    String assignment_title;
    String total_marks;
    String teacherimg_url;
    String instructions;
    String subject;
    String grade;

    public MyAssign(String id, String student_id, String assignment_id, String stdimg_url, String feedback, String marks_obtained, String upload_status, String assignment_date, String assignment_title, String total_marks, String teacherimg_url, String instructions, String subject, String grade) {
        this.id = id;
        this.student_id = student_id;
        this.assignment_id = assignment_id;
        this.stdimg_url = stdimg_url;
        this.feedback = feedback;
        this.marks_obtained = marks_obtained;
        this.upload_status = upload_status;
        this.assignment_date = assignment_date;
        this.assignment_title = assignment_title;
        this.total_marks = total_marks;
        this.teacherimg_url = teacherimg_url;
        this.instructions = instructions;
        this.subject = subject;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(String assignment_id) {
        this.assignment_id = assignment_id;
    }

    public String getStdimg_url() {
        return stdimg_url;
    }

    public void setStdimg_url(String stdimg_url) {
        this.stdimg_url = stdimg_url;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMarks_obtained() {
        return marks_obtained;
    }

    public void setMarks_obtained(String marks_obtained) {
        this.marks_obtained = marks_obtained;
    }

    public String getUpload_status() {
        return upload_status;
    }

    public void setUpload_status(String upload_status) {
        this.upload_status = upload_status;
    }

    public String getAssignment_date() {
        return assignment_date;
    }

    public void setAssignment_date(String assignment_date) {
        this.assignment_date = assignment_date;
    }

    public String getAssignment_title() {
        return assignment_title;
    }

    public void setAssignment_title(String assignment_title) {
        this.assignment_title = assignment_title;
    }

    public String getTotal_marks() {
        return total_marks;
    }

    public void setTotal_marks(String total_marks) {
        this.total_marks = total_marks;
    }

    public String getTeacherimg_url() {
        return teacherimg_url;
    }

    public void setTeacherimg_url(String teacherimg_url) {
        this.teacherimg_url = teacherimg_url;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

