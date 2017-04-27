package ViewModels;

import Annotations.Label;

public class AnimalEntity {
    @Label("编号")
    private String idNo;

    @Label("名称")
    private String name;

    @Label("种类")
    private String type;

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
