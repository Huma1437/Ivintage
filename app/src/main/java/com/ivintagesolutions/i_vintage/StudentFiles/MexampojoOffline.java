package com.ivintagesolutions.i_vintage.StudentFiles;

public class MexampojoOffline {

    String exam_name;
    String exam_status;
    String enrollment_form_status;

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getExam_status() {
        return exam_status;
    }

    public void setExam_status(String exam_status) {
        this.exam_status = exam_status;
    }

    public String getEnrollment_form_status() {
        return enrollment_form_status;
    }

    public void setEnrollment_form_status(String enrollment_form_status) {
        this.enrollment_form_status = enrollment_form_status;
    }



    public MexampojoOffline(String exam_name, String exam_status, String enrollment_form_status) {
        this.exam_name = exam_name;
        this.exam_status = exam_status;
        this.enrollment_form_status = enrollment_form_status;
    }


}
