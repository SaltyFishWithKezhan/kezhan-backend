package cn.clate.kezhan.pojos;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("kz_circle_type")
public class CircleType {
    @Id
    private int id;

    @Column("type_name")
    private String typeName;

    @Column("type_name_en")
    private  String typeNameEn;

    @Column("status")
    private int status;

    public int getId() {
        return id;
    }

    public CircleType setId(int id) {
        this.id = id;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public CircleType setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getTypeNameEn() {
        return typeNameEn;
    }

    public CircleType setTypeNameEn(String typeNameEn) {
        this.typeNameEn = typeNameEn;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public CircleType setStatus(int status) {
        this.status = status;
        return this;
    }
}
