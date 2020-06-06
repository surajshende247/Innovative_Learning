package app.iislearning.askdoubts;

public class MyDoubt
{
    String  id;
    String  student_id;
    String  grade;
    String  subject;
    String  teacher;
    String  query;
    String  answer;
    String  satisfaction;
    String  dateofquery;
    String  status;

    public MyDoubt(String id, String student_id, String grade, String subject, String teacher, String query, String answer, String satisfaction, String dateofquery, String status) {
        this.id = id;
        this.student_id = student_id;
        this.grade = grade;
        this.subject = subject;
        this.teacher = teacher;
        this.query = query;
        this.answer = answer;
        this.satisfaction = satisfaction;
        this.dateofquery = dateofquery;
        this.status = status;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getDateofquery() {
        return dateofquery;
    }

    public void setDateofquery(String dateofquery) {
        this.dateofquery = dateofquery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
