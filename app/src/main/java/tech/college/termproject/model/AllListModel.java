package tech.college.termproject.model;


public class AllListModel {
    private String status;
    private int imgId;
    private String name;

    public AllListModel(String status, int imgId, String name) {

        this.status = status;
        this.imgId = imgId;
        this.name = name;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
